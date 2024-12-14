package com.examinator.exam;

import com.examinator.exam.question.ConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam save(Exam exam) {
        ConstraintValidator.validate(exam);
        return examRepository.save(exam);
    }

    @Override
    public Exam findById(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Exam with id " + id + " not found"));
    }

    @Override
    public void delete(Exam exam) {
         examRepository.delete(exam);
    }

    @Override
    public List<Exam> loadAll() {
        return examRepository.findAll();
    }
}
