package beyond.momentours.security;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.repository.MemberRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtAuthenticationProvider(MemberRepository memberRepository, JWTUtil jwtUtil, RedisTemplate<String, String> redisTemplate) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    public Authentication getAuthentication(String token) {

        // 블랙리스트 체크 (로그아웃된 토큰이면 예외 발생)
        if (Boolean.TRUE.equals(redisTemplate.hasKey("로그아웃_" + token))) {
            throw new CommonException(ErrorCode.LOGGED_OUT);
        }

        String memberEmail = jwtUtil.getMemberId(token);
        Member member = memberRepository.findByMemberEmail(memberEmail);

        if (member == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        // 비활성화된 회원 로그인 차단
        if (!member.getMemberStatus()) {
            throw new CommonException(ErrorCode.INACTIVE_ACCOUNT);
        }

        CustomUserDetails userDetails = new CustomUserDetails(member);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }
}
