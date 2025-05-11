package beyond.momentours.randomquestion.query.repository;

import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.UserRandomQuestion;
import beyond.momentours.randomquestion.query.dto.RandomQuestionDTO;
import beyond.momentours.randomquestion.query.dto.UserRandomQuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RandomQuestionMapper {

    RandomQuestionDTO getRandomQuestionByQuesId(@Param("memberId") Long memberId, @Param("quesId") Long quesId);

    List<RandomQuestion> findAllQuestions();

    List<Long> findUsedQuestionsByCoupleId(@Param("coupleId") Long coupleId);

    UserRandomQuestion findByQuestionId(@Param("quesId") Long quesId);

    UserRandomQuestionDTO findByUserQuesId(@Param("userQuesId") Long userQuesId);

    String findAnsStatusByUserQuesId(@Param("userQuesId") Long userQuesId);

    UserRandomQuestionDTO findByAnsStatus(Long coupleId);

    Long findQuestionsByMemberId(Long coupleId);

    List<UserRandomQuestionDTO> getRandomQuestionByCursor(@Param("coupleId") Long coupleId,
                                                      @Param("lastId") Long lastId,
                                                      @Param("keyword") String keyword,
                                                      @Param("size") int size);

    boolean existsByCoupleIdAndCoupleQuesNoAndAnsStatus(@Param("coupleId") Long coupleId,
                                                        @Param("coupleQuesNo") Long coupleQuesNo,
                                                        @Param("ansStatus") String ansStatus);

    UserRandomQuestionDTO getRandomQuestionByUserQuesId(@Param("coupleId") Long coupleId,
                                                @Param("userQuesId") Long userQuesId);

}
