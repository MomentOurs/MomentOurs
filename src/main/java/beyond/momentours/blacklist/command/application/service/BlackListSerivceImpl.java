package beyond.momentours.blacklist.command.application.service;

import beyond.momentours.blacklist.command.application.dto.BlackListDTO;
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
    public void createBlackList(BlackListDTO blackListDTO) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime accessibleDate = null;

            if (blackListDTO.getBlacklistDays() != null) {
                accessibleDate = currentDate.plusDays(blackListDTO.getBlacklistDays());
            }

            BlackList blackList = blackListConverter.fromBlackDTOToBlackList(currentDate, accessibleDate, blackListDTO.getMemberId());
            blackListRepository.save(blackList);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.BLACKLIST_FAILURE);
        }
    }
}
