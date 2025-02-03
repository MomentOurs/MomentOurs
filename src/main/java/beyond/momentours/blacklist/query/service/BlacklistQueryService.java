package beyond.momentours.blacklist.query.service;

import beyond.momentours.blacklist.query.vo.response.ResponseBlacklistAll;

import java.util.List;

public interface BlacklistQueryService {
    List<ResponseBlacklistAll> getBlacklistAll();
}
