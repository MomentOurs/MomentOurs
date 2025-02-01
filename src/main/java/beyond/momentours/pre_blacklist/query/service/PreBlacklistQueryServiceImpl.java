package beyond.momentours.pre_blacklist.query.service;

import beyond.momentours.pre_blacklist.query.repository.PreBlacklistMapper;
import beyond.momentours.pre_blacklist.query.vo.response.ResponsePreBlacklistAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("queryPreBlacklistServiceImpl")
public class PreBlacklistQueryServiceImpl implements PreBlacklistQueryService{

    private final PreBlacklistMapper preBlacklistMapper;

    @Autowired
    public PreBlacklistQueryServiceImpl(PreBlacklistMapper preBlacklistMapper) {
        this.preBlacklistMapper = preBlacklistMapper;
    }

    /* 회원별로 중복 없이 목록 조회 */
    @Override
    public List<ResponsePreBlacklistAll> getPreBlacklistAll() {
        return preBlacklistMapper.findByPreBlacklistAll();
    }
}
