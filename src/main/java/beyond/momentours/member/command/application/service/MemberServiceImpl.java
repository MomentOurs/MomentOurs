package beyond.momentours.member.command.application.service;


import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.member.command.application.dto.EmailDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.application.mapper.MemberConverter;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.repository.MemberRepository;
import beyond.momentours.security.JWTUtil;
import beyond.momentours.util.RedisEmailAuthentication;
import beyond.momentours.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("commandMemberServiceImpl")
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final RedisEmailAuthentication redisEmailAuthentication;
    private final RedisTemplate<String, String> redisTemplate;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberConverter memberConverter, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, RedisEmailAuthentication redisEmailAuthentication, RedisTemplate<String, String> redisTemplate, JWTUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.redisEmailAuthentication = redisEmailAuthentication;
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
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

    /* 회원탈퇴 */
    @Override
    @Transactional
    public void withdraw(CustomUserDetails user) {
        try {
            Member member = memberRepository.findById(user.getMemberId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER));

            Member updatedMember = member.toBuilder()
                    .memberStatus(false) // 회원 상태만 변경
                    .build();
            memberRepository.save(updatedMember);
        } catch (CommonException e) {
            throw new CommonException(ErrorCode.WITHDRAW_FAILURE);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String memberEmail) {

        //DB에서 조회
        Member member = memberRepository.findByMemberEmail(memberEmail);

        // 사용자 데이터가 없으면 예외 발생
        if (member == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_MEMBER);
        }

        // 비활성화된 회원 로그인 차단
        if (!member.getMemberStatus()) {
            throw new CommonException(ErrorCode.INACTIVE_ACCOUNT);
        }

        // 사용자 데이터를 기반으로 CustomUserDetails 생성
        return new CustomUserDetails(member);
    }

    @Override
    @Transactional
    public MemberDTO updateProfile(MemberDTO requestMemberDTO, CustomUserDetails user) {

        requestMemberDTO = MemberDTO.builder()
                .memberId(user.getMemberId())
                .build();

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

    @Override
    public boolean verifyEmail(EmailDTO emailDTO) {
        // 인증 번호 만료 여부 확인
        if (redisEmailAuthentication.isAuthenticationExpired(emailDTO.getMemberEmail())) {
            redisEmailAuthentication.deleteEmailAuthenticationHistory(emailDTO.getMemberEmail()); // 만료된 인증 번호 삭제
            throw new CommonException(ErrorCode.EMAIL_AUTH_CODE_EXPIRED);
        }

        boolean isValid = redisEmailAuthentication.verifyEmailAuthentication(emailDTO.getMemberEmail(), emailDTO.getCode());

        if (!isValid) {
            throw new CommonException(ErrorCode.EMAIL_AUTH_CODE_INVALID);
        }
        return isValid;
    }

    @Override
    @Transactional
    public void updatePassword(MemberDTO memberDTO) {
        memberDTO.encodedPwd(bCryptPasswordEncoder.encode(memberDTO.getMemberPassword()));
        Member member = memberConverter.fromPasswordDTOToMember(memberDTO);
        memberRepository.updatePasswordByEmail(member.getMemberEmail(), member.getMemberPassword());
    }

    // 로그아웃 처리
    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
        // SecurityUtil에서 토큰 추출
        String token = SecurityUtil.extractToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
        }

        // 만료 시간 가져오기
        Date expiration = jwtUtil.getExpirationDateFromToken(token);

        // 현재 시간 기준 남은 시간 계산
        long expirationMillis = expiration.getTime() - System.currentTimeMillis();

        // Redis에 로그아웃된 토큰 저장
        redisTemplate.opsForValue().set("로그아웃_" + token, "logout", expirationMillis, TimeUnit.MILLISECONDS);
    }

//    @Override
//    @Transactional
//    public void changeMemberRole(Long memberId, String)

}
