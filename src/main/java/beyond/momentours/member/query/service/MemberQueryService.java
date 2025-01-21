package beyond.momentours.member.query.service;

import beyond.momentours.member.command.application.dto.MemberDTO;

public interface MemberQueryService {
    Long findByMemberId(String memberId);

    String findByMemberEmail(MemberDTO memberDTO);
}
