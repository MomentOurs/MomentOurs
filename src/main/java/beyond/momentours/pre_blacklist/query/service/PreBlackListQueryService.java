package beyond.momentours.pre_blacklist.query.service;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import beyond.momentours.report.command.application.dto.ReportDTO;

import java.util.List;

public interface PreBlackListQueryService {
    List<ResponsePreBlackListAll> getPreBlacklistAll();

    List<ReportDTO> getReportsByPreBlackList(Long preBlackListId);
}
