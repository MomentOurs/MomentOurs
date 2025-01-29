package beyond.momentours.inquiry.command.application.service;


import beyond.momentours.announcement.command.application.service.AnnouncementServiceImpl;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.inquiry.command.application.dto.InquiryDTO;
import beyond.momentours.inquiry.command.application.mapper.InquiryConverter;
import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import beyond.momentours.inquiry.command.domain.aggregate.vo.request.InquiryCreateOrUpdateRequestVO;
import beyond.momentours.inquiry.command.domain.repository.InquiryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class InquiryCommandServiceImpl implements InquiryCommandService{

    private final InquiryRepository inquiryRepository;
    private final InquiryConverter inquiryConverter;
    private static final Logger log = LoggerFactory.getLogger(AnnouncementServiceImpl.class);



    public InquiryCommandServiceImpl(InquiryRepository inquiryRepository, InquiryConverter inquiryConverter) {
        this.inquiryRepository = inquiryRepository;
        this.inquiryConverter = inquiryConverter;
    }

    // 1. 문의 등록
    @Override
    @Transactional
    public InquiryDTO createInquiry(InquiryDTO inquiryDTO){
        try{
            // 내용 제목 필수 값 유효성 검사
            if (StringUtils.isEmpty(inquiryDTO.getInquiryTitle()) || StringUtils.isEmpty(inquiryDTO.getInquiryContent())) {
                throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
            }
            Inquiry inquiry = inquiryConverter.dtoToEntity(inquiryDTO);
            Inquiry saved = inquiryRepository.save(inquiry);
            return inquiryConverter.entityToDto(saved);
        } catch (Exception e){
            //등록 실패 예외
            log.error("등록 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    // 2. 문의 수정
    @Override
    @Transactional
    public InquiryDTO updateInquiry(InquiryDTO inquiryDTO){
        // 문의id을 찾을 수 없는 경우 예외
        Inquiry originalInquiry = inquiryRepository.findById(inquiryDTO.getInquiryId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_INQUIRY));
        // 권한이 없는 경우 수정 불가
        if(!originalInquiry.getInquiryMemberId().equals(inquiryDTO.getInquiryMemberId())){
            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
        }
        try{
            // 내용 제목 필수 값 유효성 검사
            if (StringUtils.isEmpty(inquiryDTO.getInquiryTitle()) || StringUtils.isEmpty(inquiryDTO.getInquiryContent())) {
                throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
            }

            Inquiry inquiry = inquiryConverter.dtoToEntity(inquiryDTO);
            Inquiry updated = inquiryRepository.save(inquiry);
            return inquiryConverter.entityToDto(updated);
        } catch (Exception e){
            // 수정 실패
            log.error("문의 수정 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    // 3. 문의 삭제
    // 자신이 등록한 문의가 아니면 삭제 권한 없음
    @Override
    @Transactional
    public void deleteInquiry(Long inquiryId, Long memberId){

        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_INQUIRY));

        // 작성자와 로그인 한 사용자 동일한지 확인
        if(!inquiry.getInquiryMemberId().equals(memberId)){
            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
        }
        try{
            inquiryRepository.deleteById(inquiryId);
        }
        catch (Exception e){
            log.error("문의 삭제 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
