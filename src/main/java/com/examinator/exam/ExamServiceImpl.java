package com.examinator.exam;

import com.examinator.exam.question.ConstraintValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    @Transactional
    public Exam save(ExamDTO dto) {
        Exam e = new Exam();
        e.setId(dto.getId());
        e.setName(dto.getName());
        ConstraintValidator.validate(e);
        if (dto.getQuestions() != null && !dto.getQuestions().isEmpty()) {
            e.setQuestions(dto.getQuestions()
                    .stream()
                    .peek(q -> q.setExam(e))
                    .collect(Collectors.toSet()));
        } else if (dto.getId() != null) {
            Exam existingExam = examRepository.findByIdWithQuestions(dto.getId())
                    .orElseThrow(() -> new NoSuchElementException("Exam with id " + dto.getId() + " not found"));
            e.setQuestions(existingExam.getQuestions());
        }
        return examRepository.save(e);
    }

    @Override
    @Transactional
    public Exam findById(Long id) {
        return examRepository.findByIdWithQuestions(id)
                .orElseThrow(() -> new NoSuchElementException("Exam with id " + id + " not found"));
    }

    @Override
    public void delete(Exam exam) {
        examRepository.delete(exam);
    }

    @Override
    public List<ExamDTO> loadAll() {
        return examRepository.findAll()
                .stream()
                .map(e -> {
                            var dto = new ExamDTO();
                            dto.setId(e.getId());
                            dto.setName(e.getName());
                            return dto;
                        }
                )
                .collect(Collectors.toList());
    }
}
