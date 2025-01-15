package beyond.momentours.member.command.application.service;

import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.LoginDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.domain.aggregate.entity.Member;
import beyond.momentours.member.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
    @Transactional
    public MemberDTO signup(MemberDTO memberDTO) {

        memberDTO.encodedPwd(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        Member member = memberConverter.fromDTOToEntity(memberDTO);
        memberRepository.save(member);
        MemberDTO reponseMemberDTO = memberConverter.fromEntityToDTO(member);

        return reponseMemberDTO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {

        //DB에서 조회
        Member member = memberRepository.findByMemberEmail(memberEmail);

        // 사용자 데이터가 없으면 예외 발생
        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + memberEmail);
        }

        // 사용자 데이터를 기반으로 CustomUserDetails 생성
        return new CustomUserDetails(member);
    }

    @Override
    @Transactional
    public LoginDTO login(MemberDTO memberDTO) {

        if (memberDTO.getMemberPassword() != null) {
            memberDTO.encodedPwd(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        }

        Member member = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

//        if (!bCryptPasswordEncoder.matches(memberDTO.getMemberPassword(), member.getMemberPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }

        LoginDTO result = memberConverter.fromEntityToLoginDTO(member);
        return result;
    }

}
