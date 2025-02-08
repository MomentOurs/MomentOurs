package beyond.momentours.pre_blacklist.query.repository;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import beyond.momentours.report.command.application.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PreBlackListMapper {

    List<ResponsePreBlackListAll> findByPreBlacklistAll();

    List<ReportDTO> findReportsByPreBlackListId(@Param("preBlackListId") Long preBlackListId);
}
