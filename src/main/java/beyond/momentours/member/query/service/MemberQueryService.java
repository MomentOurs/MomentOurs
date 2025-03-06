package beyond.momentours.member.query.service;


import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.vo.response.ResponseMemberSearchVO;

import java.util.List;

public interface MemberQueryService {
    Long findByMemberId(Long memberId);

    String findByMemberEmail(MemberDTO memberDTO);

    MemberDTO findMemberEmailByMypage(CustomUserDetails user);

    List<ResponseMemberSearchVO> getMemberSearch(String memberNickname, String memberEmail);

    boolean emailCheck(String memberEmail);

    boolean nicknameCheck(String memberNickname);
}
