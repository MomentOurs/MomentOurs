package beyond.momentours.report.query.repository;

import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.command.domain.aggregate.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    Long findMomentOwner(@Param("targetId") Long targetId);
    Long findDateCourseOwner(@Param("targetId") Long targetId);
    int countReportsByReportedUserId(@Param("reportedUserId") Long reportedUserId);
    List<Report> findRecentReportsByReportedUserId(@Param("reportedUserId") Long reportedUserId);

    ReportDTO findReportById(@Param("reportId") Long reportId);
}
