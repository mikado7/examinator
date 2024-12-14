package com.examinator.exam.question;

public interface QuestionService {

    Question save(Question question);

    Question findQuestionById(Long id);

    void deleteQuestion(Long id);

}
