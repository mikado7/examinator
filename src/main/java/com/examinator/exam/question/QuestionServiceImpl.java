package com.examinator.exam.question;

import com.examinator.exam.Exam;
import com.examinator.exam.ExamService;
import com.examinator.exam.answer.AnswerRepository;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamService examService;
    private final AnswerRepository answerRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ExamService examService, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.examService = examService;
        this.answerRepository = answerRepository;
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow();
    }

    public Question save(QuestionDTO dto, Exam exam) {
        Question q;
        if (dto.getId() == null) {
            q = new Question();
        } else {
            q = questionRepository.findById(dto.getId())
                    .orElseThrow();
        }
        q.setExam(exam);
        q.setContent(dto.getContent());
        q.setSequence(dto.getSequence());
        q.getAnswers()
                .clear();
        dto.getAnswers()
                .forEach(a -> {
                    a.setQuestion(q);
                    q.getAnswers()
                            .add(a);
                });

        validateQuestion(q);
        return questionRepository.save(q);
    }


    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private void validateQuestion(Question question) {
        Jsoup.parse(Jsoup.clean(question.getContent(), Safelist.basic()), Parser.htmlParser());
        question.getAnswers()
                .forEach(a -> Jsoup.parse(Jsoup.clean(a.getContent(), Safelist.basic()), Parser.htmlParser()));
        ConstraintValidator.validate(question);
    }
}
