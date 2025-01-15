package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.domain.aggregate.entity.Member;
import beyond.momentours.member.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("commandAuthServiceImpl")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberConverter memberConverter, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public MemberDTO signup(MemberDTO memberDTO) {

        memberDTO.encodedPwd(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        Member member = memberConverter.fromDTOToEntity(memberDTO);
        memberRepository.save(member);
        MemberDTO reponseMemberDTO = memberConverter.fromEntityToDTO(member);

        return reponseMemberDTO;
    }
}
