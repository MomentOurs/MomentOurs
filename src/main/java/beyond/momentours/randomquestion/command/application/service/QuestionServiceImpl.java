package beyond.momentours.randomquestion.command.application.service;

import beyond.momentours.randomquestion.command.domain.aggregate.entity.RandomQuestion;
import beyond.momentours.randomquestion.command.domain.aggregate.entity.UserRandomQuestion;
import beyond.momentours.randomquestion.command.domain.repository.RandomQuestionRepository;
import beyond.momentours.randomquestion.command.domain.repository.UserRandomQuestionRepository;
import beyond.momentours.randomquestion.query.service.RandomQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("commandQuestionServiceImpl")
public class QuestionServiceImpl implements QuestionService {

    private final RandomQuestionRepository randomQuestionRepository;
    private final UserRandomQuestionRepository userRandomQuestionRepository;
    private final RandomQuestionService randomQuestionService;
    private final ChatGptService chatGptService;

    @Autowired
    public QuestionServiceImpl(RandomQuestionRepository randomQuestionRepository, UserRandomQuestionRepository userRandomQuestionRepository, RandomQuestionService randomQuestionService, ChatGptService chatGptService) {
        this.randomQuestionRepository = randomQuestionRepository;
        this.userRandomQuestionRepository = userRandomQuestionRepository;
        this.randomQuestionService = randomQuestionService;
        this.chatGptService = chatGptService;
    }

    @Value("${openai.min-threshold}")
    private int minThreshold;

    // 사용하지 않은 질문이 3개 이하면 새로운 질문 생성 후 저장
    @Transactional
    @Override
    public void createNewQuestion(Long coupleId) {
        // 저장된 랜덤질문 모두 조회
        List<RandomQuestion> allQuestions = randomQuestionService.findAllQuestions();

        // 질문이 아예 없으면 (완전 초기) 지피티에게 새로운 질문 요청
        if (allQuestions.isEmpty()) {
            List<String> newQuestions = chatGptService.fetchQuestionsFromChatGPT();
            for (String question : newQuestions) {
                RandomQuestion randomQuestion = RandomQuestion.builder()
                        .quesContent(question)
                        .build();
                randomQuestionRepository.save(randomQuestion);
            }
            allQuestions = randomQuestionService.findAllQuestions();
        }

        // 특정 커플이 사용한 질문 목록 조회
        List<Long> usedQuestionIds = randomQuestionService.findUsedQuestionsByCoupleId(coupleId);

        // 사용 가능한 질문 필터링
        List<RandomQuestion> availableQuestions = allQuestions.stream()
                .filter(question -> !usedQuestionIds.contains(question.getQuesId()))
                .toList();

        // 사용 가능한 질문이 minThreshold보다 적으면 추가 질문 생성
        if (availableQuestions.size() < minThreshold) {
            List<String> newQuestions = chatGptService.fetchQuestionsFromChatGPT();
            for (String question : newQuestions) {
                RandomQuestion randomQuestion = RandomQuestion.builder()
                        .quesContent(question)
                        .build();
                randomQuestionRepository.save(randomQuestion);
            }
            // 방금 추가된 질문 조회
            availableQuestions.addAll(randomQuestionRepository.findTop(minThreshold));
        }

        // 랜덤한 질문 하나를 커플에게 배정
        RandomQuestion assignedQuestion = assignRandomQuestionToCouple(availableQuestions);

        UserRandomQuestion userRandomQuestion = UserRandomQuestion.builder()
                .quesId(assignedQuestion.getQuesId())
                .coupleId(coupleId)
                .ansStatus("NONE")
                .build();
        userRandomQuestionRepository.save(userRandomQuestion);
    }

    private RandomQuestion assignRandomQuestionToCouple(List<RandomQuestion> availableQuestions) {
        Collections.shuffle(availableQuestions);
        return availableQuestions.get(0);
    }

}
