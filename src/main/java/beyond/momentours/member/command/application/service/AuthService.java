package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.JwtTokenDTO;

public interface AuthService {
    JwtTokenDTO handleKakaoLogin(String code);
}
