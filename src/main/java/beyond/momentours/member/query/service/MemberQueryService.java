package beyond.momentours.member.query.service;


import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.query.dto.MemberDTO;

public interface MemberQueryService {
    Long findByMemberId(Long memberId);

    String findByMemberEmail(MemberDTO memberDTO);

    MemberDTO findMemberEmailByMypage(CustomUserDetails user);
}
