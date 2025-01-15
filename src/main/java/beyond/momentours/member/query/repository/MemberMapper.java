package beyond.momentours.member.query.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Long findByMemberId(Long memberId);
}
