package beyond.momentours.announcement.query.repository;

import beyond.momentours.announcement.query.dto.AnnouncementDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnnouncementMapper {

    // 1. 모두 조회
    List<AnnouncementDTO> selectAll();

    // 2. id로 조회
    AnnouncementDTO selectById(@Param("announcementId") Long announcementId);

    // 3. 키워드로 조회
    List<AnnouncementDTO> selectByKeyword(@Param("keyword") String keyword);

    // 4. 작성자 id로 조회 (관리자)
    List<AnnouncementDTO> selectByMemberId(@Param("memberId") Long memberId);


}
