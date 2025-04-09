package beyond.momentours.blacklist.query.service;

import beyond.momentours.blacklist.query.repository.BlacklistMapper;
import beyond.momentours.blacklist.query.vo.response.ResponseBlacklistAll;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("queryBlacklistServiceImpl")
public class BlacklistQueryServiceImpl implements BlacklistQueryService {

    private final BlacklistMapper blacklistMapper;

    @Autowired
    public BlacklistQueryServiceImpl(BlacklistMapper blacklistMapper) {
        this.blacklistMapper = blacklistMapper;
    }

    @Override
    public List<ResponseBlacklistAll> getBlacklistAll() {

        List<ResponseBlacklistAll> responseAll = blacklistMapper.findByBlacklistAll();

        if (responseAll == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_BLACKLIST);
        }

        return responseAll;
    }
}
