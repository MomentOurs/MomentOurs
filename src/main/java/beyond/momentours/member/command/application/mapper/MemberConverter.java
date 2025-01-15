package beyond.momentours.member.command.application.mapper;

import beyond.momentours.member.command.application.dto.MemberDTO;
import beyond.momentours.member.domain.aggregate.entity.Member;
import beyond.momentours.member.domain.vo.reponse.ResponseSignupMemberVO;
import beyond.momentours.member.domain.vo.request.RequestSignupMemberVO;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public MemberDTO fromSignupVOToDTO(RequestSignupMemberVO requestSignupMemberVO) {
        return MemberDTO.builder()
                .memberId(requestSignupMemberVO.getMemberId())
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
                .memberId(memberDTO.getMemberId())
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
                .memberId(memberDTO.getMemberId())
                .memberEmail(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .memberName(memberDTO.getMemberName())
                .memberNickname(memberDTO.getMemberNickname())
                .memberPhone(memberDTO.getMemberPhone())
                .memberBirth(memberDTO.getMemberBirth())
                .memberGender(memberDTO.getMemberGender())
                .memberMbti(memberDTO.getMemberMbti())
                .memberRole(memberDTO.getMemberRole())
                .build();
    }

    public MemberDTO fromEntityToDTO(Member member) {
        return MemberDTO.builder()
                .memberId(member.getMemberId())
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
}
