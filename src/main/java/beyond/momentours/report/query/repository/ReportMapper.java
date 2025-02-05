package beyond.momentours.report.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {
    Long findMomentOwner(@Param("targetId") Long targetId);
    Long findDateCourseOwner(@Param("targetId") Long targetId);

    int countReportsByReportedUserId(@Param("reportedUserId") Long reportedUserId);
}

