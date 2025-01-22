package beyond.momentours.member.query.service;


import beyond.momentours.member.query.dto.MemberDTO;

public interface MemberQueryService {
    Long findByMemberId(String memberId);

    String findByMemberEmail(MemberDTO memberDTO);

    MemberDTO findMemberEmailByMypage(String email);
}
