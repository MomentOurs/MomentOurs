package beyond.momentours.member.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.query.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryMemberService")
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public Long findByMemberId(String memberId) {

        Long memId = memberMapper.findByMemberId(memberId);

        if (memId == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        return memId;
    }

    @Override
    public String findByMemberEmail(MemberDTO memberDTO) {
        String memberEmail = memberMapper.findByMemberEmail(memberDTO.getMemberEmail());
        if (memberEmail == null)
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        return memberEmail;
    }
}
