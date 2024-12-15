package com.examinator.exam;

import com.examinator.exam.question.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@Data
public class ExamDTO {

    private Long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Question> questions;
}
