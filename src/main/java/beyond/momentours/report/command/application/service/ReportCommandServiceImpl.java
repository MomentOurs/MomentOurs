package beyond.momentours.report.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.pre_blacklist.command.application.service.PreBlackListService;
import beyond.momentours.report.command.application.dto.ReportDTO;
import beyond.momentours.report.command.application.mapper.ReportConverter;
import beyond.momentours.report.command.domain.aggregate.entity.Report;
import beyond.momentours.report.command.domain.repository.ReportRepository;
import beyond.momentours.report.query.repository.ReportMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportRepository reportRepository;
    private final ReportConverter reportConverter;
    private final ReportMapper reportMapper;
    private final PreBlackListService preBlackListService;

    @Transactional
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        try {
            Long reportedUserId = null;
            if (reportDTO.getReportType().name().equals("MOMENT")) reportedUserId = reportMapper.findMomentOwner(reportDTO.getTargetId());
            else if (reportDTO.getReportType().name().equals("DATE_COURSE")) reportedUserId = reportMapper.findDateCourseOwner(reportDTO.getTargetId());

            if (reportedUserId == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

            Report report = reportConverter.fromDTOToEntity(reportDTO);
            report.setReportedUserId(reportedUserId);
            log.info("저장할 신고 정보: {}", report);

            Report savedReport = reportRepository.save(report);
            log.info("신고 등록 성공: {}", savedReport);

            int reportCount = reportMapper.countReportsByReportedUserId(reportedUserId);

            if (reportCount == 5) {
                Long preBlackId = preBlackListService.getOrCreatePreBlacklist(reportedUserId);
                log.info("5번째 신고와 이전 신고에 preBlackId 추가: {}", preBlackId);
            }

            return reportConverter.fromEntityToDTO(savedReport);
        } catch (Exception e) {
            log.error("신고 등록 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

