package beyond.momentours.member.command.application.service;


import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.repository.MemberRepository;
import beyond.momentours.member.query.service.MemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("commandAuthServiceImpl")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberQueryService memberQueryService;
    private final MailService mailService;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberConverter memberConverter, BCryptPasswordEncoder bCryptPasswordEncoder, MemberQueryService memberQueryService, MailService mailService) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberQueryService = memberQueryService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public MemberDTO signup(MemberDTO memberDTO) {

        memberDTO.encodedPwd(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        Member member = memberConverter.fromDTOToEntity(memberDTO);
        memberRepository.save(member);
        MemberDTO reponseMemberDTO = memberConverter.fromEntityToDTO(member);

        return reponseMemberDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String memberEmail) {

        //DB에서 조회
        Member member = memberRepository.findByMemberEmail(memberEmail);

        // 사용자 데이터가 없으면 예외 발생
        if (member == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        // 사용자 데이터를 기반으로 CustomUserDetails 생성
        return new CustomUserDetails(member);
    }

    @Override
    @Transactional
    public MemberDTO updateProfile(MemberDTO requestMemberDTO) {

        Long memberId = memberQueryService.findByMemberId(requestMemberDTO.getMemberEmail());

        Member member = memberConverter.fromProfileDTOToEntity(requestMemberDTO);

        memberRepository.save(member);

        MemberDTO reponseMemberDTO = memberConverter.fromEntityTOProfileUpdateDTO(member);

        return reponseMemberDTO;
    }

    @Override
    public String checkEmail(MemberDTO requestMemberDTO) {
        Member member = memberRepository.findByMemberEmail(requestMemberDTO.getMemberEmail());

        if (member== null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        String authCode = mailService.sendMail(requestMemberDTO.getMemberEmail());

        if (authCode==null) {
            throw new CommonException(ErrorCode.MAIL_SEND_FAIL);
        }

        return authCode;
    }

}
