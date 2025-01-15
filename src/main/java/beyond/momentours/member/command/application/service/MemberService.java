package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.MemberDTO;

public interface MemberService {
    MemberDTO signup(MemberDTO requestMemberDTO);
}
