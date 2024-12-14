package com.examinator.exam;

import com.examinator.exam.question.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exam")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Exam(Long id) {
        this.id = id;
    }

    @Length(min = 8, max = 100)
    private String name;

    @OneToMany(mappedBy = "exam", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequence ASC")
    @JsonManagedReference
    private Set<Question> questions = new HashSet<>();

}
