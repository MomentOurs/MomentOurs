package beyond.momentours.randomquestionanswer.command.domain.repository;

import beyond.momentours.randomquestionanswer.command.domain.aggregate.entity.RQAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RQAnswerRepository extends JpaRepository<RQAnswer,Long> {
    RQAnswer findByQuesAnswerIdAndMemberId(Long quesAnswerId, Long memberId);
    
    RQAnswer findByUserQuesIdAndMemberId(Long userQuesId, Long memberId);
    @Modifying
    @Query("UPDATE RQAnswer r SET r.quesAnsContent = :quesAnsContent WHERE r.quesAnswerId = :quesAnswerId")
    void updateContent(@Param("quesAnswerId")Long quesAnswerId, @Param("quesAnsContent") String quesAnsContent);
}
