package beyond.momentours.blacklist.command.application.controller;

import beyond.momentours.blacklist.command.application.dto.BlackListDTO;
import beyond.momentours.blacklist.command.application.mapper.BlackListConverter;
import beyond.momentours.blacklist.command.application.service.BlackListSerivce;
import beyond.momentours.blacklist.command.domain.vo.request.RequestBlackVO;
import beyond.momentours.common.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("commandBlackListController")
@RequestMapping("api/blacklist")
public class BlackListController {

    private final BlackListSerivce blackListSerivce;
    private final BlackListConverter blackListConverter;

    @Autowired
    public BlackListController(BlackListSerivce blackListSerivce, BlackListConverter blackListConverter) {
        this.blackListSerivce = blackListSerivce;
        this.blackListConverter = blackListConverter;
    }

    /* 블랙리스트 등록 */
    @PostMapping("")
    public ResponseDTO<?> createBlackList(@RequestBody RequestBlackVO requestBlackVO) {
        BlackListDTO blackListDTO = blackListConverter.fromBlackVOToBlackDTO(requestBlackVO);
        blackListSerivce.createBlackList(blackListDTO);
        return ResponseDTO.ok("블랙리스트가 성공적으로 등록되었습니다.");
    }


}
