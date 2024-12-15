package com.examinator.exam.question;

import com.examinator.exam.Exam;

public interface QuestionService {

    Question save(QuestionDTO question, Exam e);

    Question findQuestionById(Long id);

    void deleteQuestion(Long id);
}
