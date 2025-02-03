package beyond.momentours.pre_blacklist.command.application.service;

import beyond.momentours.blacklist.command.application.service.BlackListSerivce;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.pre_blacklist.command.application.dto.PreBlackListDTO;
import beyond.momentours.pre_blacklist.command.domain.aggregate.entity.PreBlackList;
import beyond.momentours.pre_blacklist.command.domain.repository.PreBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("commandPreBlacklistServiceImpl")
public class PreBlackListServiceImpl implements PreBlackListService {

    private final PreBlackListRepository preBlacklistRepository;
    private final BlackListSerivce blackListSerivce;

    @Autowired
    public PreBlackListServiceImpl(PreBlackListRepository preBlacklistRepository, BlackListSerivce blackListSerivce) {
        this.preBlacklistRepository = preBlacklistRepository;
        this.blackListSerivce = blackListSerivce;
    }

    @Override
    @Transactional
    public void updateStatus(PreBlackListDTO preBlacklistDTO) {
        try {
            PreBlackList preBlacklist = preBlacklistRepository.findById(preBlacklistDTO.getPreBlackId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PREBLACKLIST));

            String status = preBlacklistDTO.getStatus();
            Integer blacklistDays = null;

            // 정지 기간 설정
            switch (status) {
                case "7일":
                    blacklistDays = 7;
                    break;
                case "15일":
                    blacklistDays = 15;
                    break;
                case "30일":
                    blacklistDays = 30;
                    break;
                case "영구정지":
                    blacklistDays = -1; // 영구정지는 -1로 설정
                    break;
                case "반려":
                    // 반려 상태일 경우 블랙리스트로 넘기지 않음
                    PreBlackList preBlackListStatus = PreBlackList.builder()
                            .status(preBlacklistDTO.getStatus())
                            .build();
                    preBlacklistRepository.save(preBlackListStatus);
                    return;
            }

            // 예비 블랙리스트 상태 업데이트
            PreBlackList preBlackListStatus = PreBlackList.builder()
                    .status(preBlacklistDTO.getStatus())
                    .blackListCount(preBlacklist.getBlackListCount() + 1)
                    .statusCreatedAt(LocalDateTime.now())
                    .statusUpdatedAt(LocalDateTime.now())
                    .build();
            preBlacklistRepository.save(preBlackListStatus);

            // 블랙리스트로 이동
            blackListSerivce.createBlackList(preBlacklist.getMemberId(), blacklistDays);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.PREBLACKLIST_FAILURE);
        }
    }

    @Transactional
    @Override
    public void createPreBlacklist(Long reportId, Long memberId) {

        try {
            PreBlackList pre = preBlacklistRepository.findByMemberId(memberId);

            PreBlackList preBlacklist = PreBlackList.builder()
                    .status("대기")
                    .memberId(memberId)
                    .reportId(reportId)
                    .blackListCount(pre.getBlackListCount())
                    .build();

            preBlacklistRepository.save(preBlacklist);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.PREBLACKLIST_FAILURE);
        }
    }
}
