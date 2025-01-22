package beyond.momentours.member.query.service;

import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.repository.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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

    @Override
    public MemberDTO findMemberEmailByMypage(String email) {
        log.debug("findMemberEmailByMypage: {}", email);
        MemberDTO member = memberMapper.findMemberEmailByMypage(email);
        if (member == null)
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        log.debug("22222: {}", member.getMemberEmail());

        return member;
    }
}
