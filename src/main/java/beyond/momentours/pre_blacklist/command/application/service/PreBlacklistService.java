package beyond.momentours.pre_blacklist.command.application.service;

import beyond.momentours.pre_blacklist.command.application.dto.PreBlacklistDTO;

public interface PreBlacklistService {
    void updateStatus(PreBlacklistDTO preBlacklistDTO);
}
