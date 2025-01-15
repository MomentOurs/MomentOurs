package beyond.momentours.member.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.query.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryMemberService")
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Long findByMemberId(Long memberId) {

        Long memId = memberMapper.findByMemberId(memberId);

        if (memId == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        return memId;
    }
}
