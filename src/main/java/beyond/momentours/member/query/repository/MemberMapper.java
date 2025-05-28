package beyond.momentours.member.query.repository;

import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.vo.response.ResponseMemberSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    Long findByMemberId(Long memberId);

    String findByMemberEmail(String memberEmail);

    MemberDTO findMemberEmailByMypage(Long memberId);

    List<ResponseMemberSearchVO> findMemberSearch(@Param("memberNickname") String memberNickname,
                                                  @Param("memberEmail") String memberEmail);

    String findByMemberNickname(String memberNickname);

    String findMemberEmailByInfo(MemberDTO memberDTO);
}
