package beyond.momentours.pre_blacklist.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.pre_blacklist.query.repository.PreBlackListMapper;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;
import beyond.momentours.report.command.application.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("queryPreBlacklistServiceImpl")
public class PreBlackListQueryServiceImpl implements PreBlackListQueryService {

    private final PreBlackListMapper preBlackListMapper;

    @Autowired
    public PreBlackListQueryServiceImpl(PreBlackListMapper preBlackListMapper) {
        this.preBlackListMapper = preBlackListMapper;
    }

    /* 회원별로 중복 없이 목록 조회 */
    @Override
    public List<ResponsePreBlackListAll> getPreBlacklistAll() {

        List<ResponsePreBlackListAll> response = preBlackListMapper.findByPreBlacklistAll();

        if (response == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_PREBLACKLIST);
        }

        return response;
    }

    @Override
    public List<ReportDTO> getReportsByPreBlackList(Long preBlackListId) {
        List<ReportDTO> reports = preBlackListMapper.findReportsByPreBlackListId(preBlackListId);

        if (reports == null || reports.isEmpty()) throw new CommonException(ErrorCode.NOT_FOUND_REPORT);

        return reports;
    }
}
