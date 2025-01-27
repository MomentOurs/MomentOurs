package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.JwtTokenDTO;
import beyond.momentours.member.command.application.dto.oauth.KakaoUserInfo;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.aggregate.entity.MemberRole;
import beyond.momentours.member.command.domain.repository.MemberRepository;
import beyond.momentours.security.JWTUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthServiceImpl implements AuthService {
    private final WebClient webClient;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Autowired
    public AuthServiceImpl(WebClient webClient, JWTUtil jwtUtil, MemberRepository memberRepository) {
        this.webClient = webClient;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public JwtTokenDTO handleKakaoLogin(String code) {
        // 1. 카카오 액세스 토큰 얻기
        String kakaoAccessToken = getKakaoAccessToken(code);

        // 2. 카카오 사용자 정보 얻기
        KakaoUserInfo userInfo = getKakaoUserInfo(kakaoAccessToken);

        // 3. 회원가입 또는 로그인 처리
        Member member = registerOrLogin(userInfo);

        // 4. JWT 토큰 생성
        String accessToken = jwtUtil.generateToken(member.getMemberEmail(), "ROLE_USER", null);
        String refreshToken = jwtUtil.generateRefreshToken(member.getMemberEmail());

        return new JwtTokenDTO(accessToken, refreshToken);
    }

    private String getKakaoAccessToken(String code) {
        String tokenUri = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", "your_redirect_uri");

        return webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(node -> node.get("access_token").asText())
                .block();
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        return webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

    private Member registerOrLogin(KakaoUserInfo userInfo) {
        String email = userInfo.getKakao_account().getEmail();
        Member member = memberRepository.findByMemberEmail(email);

        if (member == null) {
            // 회원가입
            member = Member.builder()
                    .memberEmail(email)
                    .memberName(userInfo.getKakao_account().getProfile().getNickname())
                    .memberRole(MemberRole.ROLE_MEMBER)
//                    .oauthProvider("KAKAO")
                    .build();
            memberRepository.save(member);
        }

        return member;
    }
}
