package beyond.momentours.security;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    public JwtAuthenticationProvider(MemberRepository memberRepository, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public Authentication getAuthentication(String token) {
        String memberEmail = jwtUtil.getMemberId(token);
        Member member = memberRepository.findByMemberEmail(memberEmail);

        if (member == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }
        CustomUserDetails userDetails = new CustomUserDetails(member);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }
}
