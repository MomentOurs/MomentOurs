package beyond.momentours.pre_blacklist.query.service;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlacklistAll;

import java.util.List;

public interface PreBlacklistQueryService {
    List<ResponsePreBlacklistAll> getPreBlacklistAll();
}
