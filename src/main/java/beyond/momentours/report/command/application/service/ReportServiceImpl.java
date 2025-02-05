package beyond.momentours.report.command.application.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
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
@Service("commandReportService")
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportConverter reportConverter;
    private final ReportMapper reportMapper;

    @Transactional
    @Override
    public ReportDTO createReport(ReportDTO reportDTO) {
        try {
            Long reportedUserId = null;
            if (reportDTO.getReportType().name().equals("MOMENT")) reportedUserId = reportMapper.findMomentOwner(reportDTO.getMomentId());
            else if (reportDTO.getReportType().name().equals("DATE_COURSE")) reportedUserId = reportMapper.findDateCourseOwner(reportDTO.getCourseId());

            if (reportedUserId == null) throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);

            Report report = reportConverter.fromDTOToEntity(reportDTO);
            log.info("저장할 신고 정보: {}", report);
            Report savedReport = reportRepository.save(report);
            log.info("신고 등록 성공: {}", savedReport);
            return reportConverter.fromEntityToDTO(savedReport);
        } catch (Exception e) {
            log.error("신고 등록 중 오류 발생", e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

