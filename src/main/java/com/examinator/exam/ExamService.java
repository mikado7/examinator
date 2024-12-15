package com.examinator.exam;

import java.util.List;

public interface ExamService {

    Exam findById(Long id);

    Exam save(ExamDTO exam);

    void delete(Exam exam);

    List<ExamDTO> loadAll();
}
