package beyond.momentours.randomquestion.command.application.service;

public interface QuestionService {

    // 사용하지 않은 질문이 3개 이하면 새로운 질문 생성 후 저장
    void createNewQuestion(Long coupleId);
}
