package beyond.momentours.pre_blacklist.command.application.service;

import beyond.momentours.pre_blacklist.command.application.dto.PreBlacklistDTO;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlacklistAll;

import java.util.List;

public interface PreBlacklistService {
    void updateStatus(PreBlacklistDTO preBlacklistDTO);
}
