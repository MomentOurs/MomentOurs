package beyond.momentours.member.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.query.repository.AdminMapper;
import beyond.momentours.member.query.vo.response.ResponseLoginHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminQueryServiceImpl implements AdminQueryService {

    private final AdminMapper adminMapper;

    @Autowired
    public AdminQueryServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public List<ResponseLoginHistoryVO> getLoginHistory() {
        List<ResponseLoginHistoryVO> response = adminMapper.findLoginHistoryAll();
        if (response == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_LOGINHISTORY);
        }
        return response;
    }

}
