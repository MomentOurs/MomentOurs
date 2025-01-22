package beyond.momentours.member.query.repository;

import beyond.momentours.member.query.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Long findByMemberId(String memberId);

    String findByMemberEmail(String memberEmail);

    MemberDTO findMemberEmailByMypage(String email);
}
