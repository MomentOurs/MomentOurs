package beyond.momentours.member.command.application.controller;

import beyond.momentours.common.ResponseDTO;
import beyond.momentours.member.command.application.dto.JwtTokenDTO;
import beyond.momentours.member.command.application.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/kakao/callback")
    public ResponseDTO<?> kakaoCallback(@RequestParam String code) {
        JwtTokenDTO tokens = authService.handleKakaoLogin(code);
        return ResponseDTO.ok(tokens);
    }
}
