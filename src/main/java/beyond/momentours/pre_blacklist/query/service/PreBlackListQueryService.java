package beyond.momentours.pre_blacklist.query.service;

import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlackListAll;

import java.util.List;

public interface PreBlackListQueryService {
    List<ResponsePreBlackListAll> getPreBlacklistAll();
}
