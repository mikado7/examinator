package com.examinator.exam.answer;

import com.examinator.exam.question.ConstraintValidator;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer findAnswerById(Long id) {
        return answerRepository.findById(id).orElseThrow();
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        validateAnswer(answer);
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    private void validateAnswer(Answer answer) {
        System.out.println("validating answer");
        Jsoup.parse(Jsoup.clean(answer.getContent(), Safelist.basic()), Parser.htmlParser());
        ConstraintValidator.validate(answer);
    }

}
