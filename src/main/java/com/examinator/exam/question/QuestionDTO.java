package com.examinator.exam.question;

import com.examinator.exam.answer.Answer;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionDTO {

    private Long id;
    private Long examId;
    private String content;
    private Set<Answer> answers;
    private Long sequence;
}
