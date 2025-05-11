package beyond.momentours.randomquestion.command.application.service;

import beyond.momentours.couple.query.service.QueryCoupleService;
import beyond.momentours.member.command.application.dto.CustomUserDetails;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.UserRandomQuestion;
import beyond.momentours.randomquestion.command.domain.repository.RandomQuestionRepository;
import beyond.momentours.randomquestion.command.domain.repository.UserRandomQuestionRepository;
import beyond.momentours.randomquestion.query.service.RandomQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service("commandQuestionServiceImpl")
public class QuestionServiceImpl implements QuestionService {

    private final RandomQuestionRepository randomQuestionRepository;
    private final UserRandomQuestionRepository userRandomQuestionRepository;
    private final RandomQuestionService randomQuestionService;
    private final QueryCoupleService queryCoupleService;
    private final ChatGptService chatGptService;

    @Value("${openai.min-threshold}")
    private int minThreshold;

    @Autowired
    public QuestionServiceImpl(RandomQuestionRepository randomQuestionRepository, UserRandomQuestionRepository userRandomQuestionRepository, RandomQuestionService randomQuestionService, QueryCoupleService queryCoupleService, ChatGptService chatGptService) {
        this.randomQuestionRepository = randomQuestionRepository;
        this.userRandomQuestionRepository = userRandomQuestionRepository;
        this.randomQuestionService = randomQuestionService;
        this.queryCoupleService = queryCoupleService;
        this.chatGptService = chatGptService;
    }

    // 사용하지 않은 질문이 3개 이하면 새로운 질문 생성 후 저장
    @Transactional
    @Override
    public void createNewQuestion(CustomUserDetails user) throws InterruptedException, ExecutionException, TimeoutException {

        Long coupleId = queryCoupleService.getCoupleIdByMemberId(user.getMemberId());
        Long memberId = user.getMemberId();

        assignNewQuestionToCouple(coupleId, List.of(memberId)); // 1명만 배정
    }

    @Transactional
    @Override
    public void checkAndAssignToAllCouples() throws ExecutionException, InterruptedException, TimeoutException {
        List<Long> coupleIds = queryCoupleService.getAllCoupleIds();

        for (Long coupleId : coupleIds) {
            Long latestQuesNo = randomQuestionService.findQuestionsByMemberId(coupleId);
            if (latestQuesNo <= 0L) continue;

            boolean bothAnswered = randomQuestionService.existsByCoupleIdAndCoupleQuesNoAndAnsStatus(coupleId, latestQuesNo, "ALL");

            if (bothAnswered) {
                assignNewQuestionToCouple(coupleId);
            }
        }
    }

    @Transactional
    @Override
    public void assignNewQuestionToCouple(Long coupleId) throws InterruptedException, ExecutionException, TimeoutException {
        List<Long> memberIds = queryCoupleService.getMemberIdsByCoupleId(coupleId); // 두 명
        assignNewQuestionToCouple(coupleId, memberIds);
    }

    private void assignNewQuestionToCouple(Long coupleId, List<Long> memberIds) throws InterruptedException, ExecutionException, TimeoutException {
        List<RandomQuestion> allQuestions = randomQuestionRepository.findAll();
        List<Long> usedQuestionIds = randomQuestionService.findUsedQuestionsByCoupleId(coupleId);

        List<RandomQuestion> availableQuestions = new ArrayList<>(
                allQuestions.stream()
                        .filter(q -> !usedQuestionIds.contains(q.getQuesId()))
                        .toList()
        );

        if (availableQuestions.size() < minThreshold) {
            List<String> newQuestions = chatGptService.fetchQuestionsFromChatGPT();

            if (newQuestions == null || newQuestions.isEmpty()) {
                throw new IllegalStateException("ChatGPT로부터 질문을 받아오지 못했습니다.");
            }

            for (String content : newQuestions) {
                randomQuestionRepository.save(RandomQuestion.builder().quesContent(content).build());
            }

            // 질문 다시 불러오기
            allQuestions = randomQuestionService.findAllQuestions();
            availableQuestions = new ArrayList<>(
                    allQuestions.stream()
                            .filter(q -> !usedQuestionIds.contains(q.getQuesId()))
                            .toList()
            );
        }

        if (availableQuestions.isEmpty()) {
            throw new IllegalStateException("사용 가능한 질문이 없습니다.");
        }

        RandomQuestion selected = assignRandomQuestionToCouple(availableQuestions);
        Long maxQuesNo = randomQuestionService.findQuestionsByMemberId(coupleId); // COALESCE → null 방지

        log.info("[질문 배정] coupleId={}, memberIds={}, assignedQuesId={}", coupleId, memberIds, selected.getQuesId());

        for (Long memberId : memberIds) {
            UserRandomQuestion urq = UserRandomQuestion.builder()
                    .quesId(selected.getQuesId())
                    .coupleId(coupleId)
                    .coupleQuesNo(maxQuesNo + 1)
                    .ansStatus("NONE") // 트리거가 이후 상태 관리
                    .used(false)
                    .build();

            userRandomQuestionRepository.save(urq);
        }
    }

    private RandomQuestion assignRandomQuestionToCouple(List<RandomQuestion> availableQuestions) {
        Collections.shuffle(availableQuestions);
        return availableQuestions.get(0);
    }

}
