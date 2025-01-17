package beyond.momentours.randomq.query.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.randomq.query.service.RandomQService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/randomq/")
public class RandomQController {

    private final RandomQService randomQService;

    @GetMapping("")
    public ResponseDTO<?> findAllRandomQ(@RequestParam Long coupleId){
        return null;
    }

}
