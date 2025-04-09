package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.EmailDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface MemberService extends UserDetailsService {
    MemberDTO signup(MemberDTO requestMemberDTO);

    UserDetails loadUserByUsername(String memberId);

    MemberDTO updateProfile(MemberDTO requestMemberDTO, CustomUserDetails user);

    void checkEmail(MemberDTO requestMemberDTO);

    boolean verifyEmail(EmailDTO emailDTO);

    void updatePassword(MemberDTO memberDTO);

    void logout(HttpServletRequest request);

    void withdraw(CustomUserDetails user);
}
