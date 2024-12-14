package com.examinator.exam.question;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public Question save(Question question) {
        if (question.getAnswers().size() > 8) {
            throw new IllegalArgumentException();
        }
        validateQuestion(question);
        if (question.getSequence() == null) {
            question.setSequence((long) (question.getExam()
                    .getQuestions()
                    .size() + 1));
        }
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private void validateQuestion(Question question) {
        Jsoup.parse(Jsoup.clean(question.getContent(), Safelist.basic()), Parser.htmlParser());
        question.getAnswers().forEach(a -> Jsoup.parse(Jsoup.clean(a.getContent(), Safelist.basic()), Parser.htmlParser()));
        ConstraintValidator.validate(question);
    }
}
