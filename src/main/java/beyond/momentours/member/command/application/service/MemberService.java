package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.EmailDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface MemberService extends UserDetailsService {
    MemberDTO signup(MemberDTO requestMemberDTO);

    UserDetails loadUserByUsername(String memberId);

    MemberDTO updateProfile(MemberDTO requestMemberDTO);

    String checkEmail(MemberDTO requestMemberDTO);

    boolean verifyEmail(EmailDTO emailDTO);
}
