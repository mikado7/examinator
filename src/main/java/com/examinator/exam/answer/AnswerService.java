package com.examinator.exam.answer;

public interface AnswerService {

    Answer findAnswerById(Long id);

    Answer saveAnswer(Answer answer);

    void deleteAnswer(Long id);


}
