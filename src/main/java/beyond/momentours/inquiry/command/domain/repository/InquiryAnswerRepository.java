package beyond.momentours.inquiry.command.domain.repository;

import beyond.momentours.inquiry.command.domain.aggregate.entity.InquiryAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {

    // 문의 답변 등록: inquiryAnswerStatus true 로 변경
    @Modifying
    @Query("UPDATE Inquiry i SET i.inquiryAnswerStatus = true WHERE i.inquiryId = :inquiryId")
    int createInquiryAnswerByInquiryId(@Param("inquiryId") Long inquiryId);

    // inquiryId로 inquiryAnswer 조회
    Optional<InquiryAnswer> findByInquiryId(Long inquiryId);

}
