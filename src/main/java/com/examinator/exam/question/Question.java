package com.examinator.exam.question;

import com.examinator.exam.Exam;
import com.examinator.exam.answer.Answer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String content;


    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("id asc")
    @Size(max = 8)
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Exam exam;

    private Long sequence;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createDate;

    @JsonIgnore
    public QuestionDTO getDTO() {
        var dto = new QuestionDTO();
        dto.setId(this.getId());
        dto.setSequence(this.getSequence());
        dto.setContent(this.getContent());
        dto.setExamId(this.getExam()==null ? null : this.getExam().getId());
        dto.setAnswers(this.getAnswers());
        return dto;
    }
}
