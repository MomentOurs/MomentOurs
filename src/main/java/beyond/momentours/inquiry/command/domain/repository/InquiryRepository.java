package beyond.momentours.inquiry.command.domain.repository;

import beyond.momentours.inquiry.command.domain.aggregate.entity.Inquiry;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // 소프트 딜리트: inquiryStatus 상태값 false로 변경
    @Modifying
    @Query("UPDATE Inquiry i SET i.inquiryStatus = false WHERE i.inquiryId = :inquiryId")
    int softDeleteByInquiryId(@Param("inquiryId") Long inquiryId);
}
