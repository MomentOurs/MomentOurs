package beyond.momentours.member.query.mapper;

import beyond.momentours.member.query.dto.MemberDTO;
import beyond.momentours.member.query.vo.response.ResponseMypageVO;
import org.springframework.stereotype.Component;

@Component
public class MemberQueryConverter {

    public ResponseMypageVO fromDtoToMypageVO(MemberDTO memberDTO) {
        return ResponseMypageVO.builder()
                .memberEmail(memberDTO.getMemberEmail())
                .memberName(memberDTO.getMemberName())
                .memberNickname(memberDTO.getMemberNickname())
                .memberPhone(memberDTO.getMemberPhone())
                .memberBirth(memberDTO.getMemberBirth())
                .memberGender(memberDTO.getMemberGender())
                .memberMbti(memberDTO.getMemberMbti())
                .build();
    }
}
