package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.LoginDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {
    MemberDTO signup(MemberDTO requestMemberDTO);

    UserDetails loadUserByUsername(String memberId);

    LoginDTO login(MemberDTO requestMemberDTO);
}
