package beyond.momentours.blacklist.command.application.service;

import beyond.momentours.blacklist.command.application.mapper.BlackListConverter;
import beyond.momentours.blacklist.command.domain.aggregate.entity.BlackList;
import beyond.momentours.blacklist.command.domain.repository.BlackListRepository;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("commandBlackListServiceImpl")
public class BlackListSerivceImpl implements BlackListSerivce{

    private final BlackListRepository blackListRepository;
    private final BlackListConverter blackListConverter;

    @Autowired
    public BlackListSerivceImpl(BlackListRepository blackListRepository, BlackListConverter blackListConverter) {
        this.blackListRepository = blackListRepository;
        this.blackListConverter = blackListConverter;
    }


    @Override
    public void createBlackList(Long memberId, Integer blacklistDays) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime accessibleDate = null;

            // 영구정지 처리 (-1일 경우 null 유지)
            if (blacklistDays != null && blacklistDays > 0) {
                accessibleDate = currentDate.plusDays(blacklistDays);
            }

            BlackList blackList = blackListConverter.fromBlackDTOToBlackList(currentDate, accessibleDate, memberId);
            blackListRepository.save(blackList);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BLACKLIST_FAILURE);
        }
    }
}
