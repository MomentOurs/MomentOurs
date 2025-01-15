package beyond.momentours.member.command.application.mapper;

import beyond.momentours.member.command.application.dto.LoginDTO;
import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.command.domain.aggregate.entity.Member;
import beyond.momentours.member.command.domain.aggregate.entity.MemberRole;
import beyond.momentours.member.command.domain.vo.reponse.ResponseLoginMemberVO;
import beyond.momentours.member.command.domain.vo.reponse.ResponseSignupMemberVO;
import beyond.momentours.member.command.domain.vo.request.RequestLoginMemberVO;
import beyond.momentours.member.command.domain.vo.request.RequestSignupMemberVO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberDTO fromSignupVOToDTO(RequestSignupMemberVO requestSignupMemberVO) {
        return MemberDTO.builder()
                .memberEmail(requestSignupMemberVO.getMemberEmail())
                .memberPassword(requestSignupMemberVO.getMemberPassword())
                .memberName(requestSignupMemberVO.getMemberName())
                .memberNickname(requestSignupMemberVO.getMemberNickname())
                .memberPhone(requestSignupMemberVO.getMemberPhone())
                .memberBirth(requestSignupMemberVO.getMemberBirth())
                .memberGender(requestSignupMemberVO.getMemberGender())
                .memberMbti(requestSignupMemberVO.getMemberMbti())
                .build();
    }

    public ResponseSignupMemberVO fromDTOToSignupVO(MemberDTO memberDTO) {
        return ResponseSignupMemberVO.builder()
                .memberEmail(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .memberNickname(memberDTO.getMemberNickname())
                .memberPhone(memberDTO.getMemberPhone())
                .memberBirth(memberDTO.getMemberBirth())
                .memberGender(memberDTO.getMemberGender())
                .memberMbti(memberDTO.getMemberMbti())
                .createdAt(memberDTO.getCreatedAt())
                .updatedAt(memberDTO.getUpdatedAt())
                .memberRole(memberDTO.getMemberRole())
                .build();
    }

    public Member fromDTOToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .memberEmail(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .memberNickname(memberDTO.getMemberNickname())
                .memberPhone(memberDTO.getMemberPhone())
                .memberBirth(memberDTO.getMemberBirth())
                .memberGender(memberDTO.getMemberGender())
                .memberMbti(memberDTO.getMemberMbti())
                .memberRole(MemberRole.ROLE_MEMBER)
                .memberStatus(true)
                .build();
    }

    public MemberDTO fromEntityToDTO(Member member) {
        return MemberDTO.builder()
                .memberEmail(member.getMemberEmail())
                .memberPassword(member.getMemberPassword())
                .memberName(member.getMemberName())
                .memberNickname(member.getMemberNickname())
                .memberPhone(member.getMemberPhone())
                .memberBirth(member.getMemberBirth())
                .memberGender(member.getMemberGender())
                .memberMbti(member.getMemberMbti())
                .memberRole(member.getMemberRole())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }


    public LoginDTO fromEntityToLoginDTO(Member member) {
        return LoginDTO.builder()
                .memberEmail(member.getMemberEmail())
                .memberPassword(member.getMemberPassword())
//                .accessToken(member.getAccessToken())
//                .refreshToken(member.getRefreshToken())
                .build();
    }

    public MemberDTO fromLoginVOToDTO(RequestLoginMemberVO requestLoginMemberVO) {
        return MemberDTO.builder()
                .memberEmail(requestLoginMemberVO.getMemberEmail())
                .memberPassword(requestLoginMemberVO.getMemberPassword())
                .build();
    }

    public ResponseLoginMemberVO fromDTOToLoginVO(LoginDTO loginDTO) {
        return ResponseLoginMemberVO.builder()
                .memberEmail(loginDTO.getMemberEmail())
                .memberPassword(loginDTO.getMemberPassword())
                .build();
    }
}
