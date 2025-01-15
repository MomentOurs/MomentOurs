package beyond.momentours.couple.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.couple.command.application.service.CommandCoupleService;
import beyond.momentours.couple.command.application.service.CoupleMatchingService;
import beyond.momentours.couple.command.domain.aggregate.entity.MatchingCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couple")
public class CommandCoupleController {

    private final CommandCoupleService commandCoupleService;
    private final CoupleMatchingService coupleMatchingService;

    @Autowired
    public CommandCoupleController(CommandCoupleService commandCoupleService, CoupleMatchingService coupleMatchingService) {
        this.commandCoupleService = commandCoupleService;
        this.coupleMatchingService = coupleMatchingService;
    }

    @PostMapping("/{userId}")
    public ResponseDTO<?> createMatchingCode(@PathVariable Long userId) {


        return ResponseDTO.ok();
    }
}
