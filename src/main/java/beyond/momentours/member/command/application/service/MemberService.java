package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface MemberService extends UserDetailsService {
    MemberDTO signup(MemberDTO requestMemberDTO);

    UserDetails loadUserByUsername(String memberId);

}
