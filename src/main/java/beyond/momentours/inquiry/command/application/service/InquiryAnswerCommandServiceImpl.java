package beyond.momentours.inquiry.command.application.service;


import beyond.momentours.announcement.command.application.service.AnnouncementServiceImpl;
import beyond.momentours.common.exception.CommonException;
import beyond.momentours.common.exception.ErrorCode;
import beyond.momentours.inquiry.command.application.dto.InquiryAnswerDTO;
import beyond.momentours.inquiry.command.application.mapper.InquiryAnswerConverter;
import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import beyond.momentours.inquiry.command.domain.repository.InquiryAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class InquiryAnswerCommandServiceImpl implements InquiryAnswerCommandService {

    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final InquiryAnswerConverter inquiryAnswerConverter;
    private static final Logger log = LoggerFactory.getLogger(AnnouncementServiceImpl.class);

    public InquiryAnswerCommandServiceImpl(InquiryAnswerRepository inquiryAnswerRepository, InquiryAnswerConverter inquiryAnswerConverter) {
        this.inquiryAnswerRepository = inquiryAnswerRepository;
        this.inquiryAnswerConverter = inquiryAnswerConverter;
    }

    // 1. 문의 답변 등록
    @Override
    @Transactional
    public InquiryAnswerDTO createInquiryAnswer(InquiryAnswerDTO inquiryAnswerDTO){
        try{
            if(StringUtils.isEmpty(inquiryAnswerDTO.getInquiryAnswerContent())){
                throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
            }
            InquiryAnswer inquiryAnswer = inquiryAnswerConverter.dtoToEntity(inquiryAnswerDTO);
            InquiryAnswer saved = inquiryAnswerRepository.save(inquiryAnswer);
            inquiryAnswerRepository.createInquiryAnswerByInquiryId(saved.getInquiryId());
            return inquiryAnswerConverter.entityToDto(saved);
        } catch(Exception e){
            //등록 실패 예외
            log.error("등록 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    // 2. 문의 답변 수정
    @Override
    @Transactional
    public InquiryAnswerDTO updateInquiryAnswer(InquiryAnswerDTO inquiryAnswerDTO){
        InquiryAnswer originalInquiryAnswer = inquiryAnswerRepository.findByInquiryId(inquiryAnswerDTO.getInquiryId())
                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_QUES_ANSWER));

        if(!originalInquiryAnswer.getAnswerMemberId().equals(inquiryAnswerDTO.getAnswerMemberId())){
            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
        }
        try{
            // 내용 값 필수 입력
            if(StringUtils.isEmpty(inquiryAnswerDTO.getInquiryAnswerContent())){
                throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
            }

            InquiryAnswer inquiryAnswer = inquiryAnswerConverter.dtoToEntity(inquiryAnswerDTO);
            InquiryAnswer updated = inquiryAnswerRepository.save(inquiryAnswer);
            return inquiryAnswerConverter.entityToDto(updated);
        } catch (Exception e){
            log.error("문의 답변 수정 실패");
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    // 3. 문의 답변 삭제
//    @Override
//    @Transactional
//    public void deleteInquiryAnswer(Long inquiryAnswerId, Long answerMemberId){
//
//        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.findById(inquiryAnswerId)
//                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_QUES_ANSWER));
//
//        if(!inquiryAnswer.getAnswerMemberId().equals(answerMemberId)){
//            throw new CommonException(ErrorCode.FORBIDDEN_ROLE);
//        }
//        try{
//            inquiryAnswerRepository.deleteById(inquiryAnswerId);
//        }
//        catch (Exception e){
//            log.error("문의 답변 삭제 실패");
//            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }

}
