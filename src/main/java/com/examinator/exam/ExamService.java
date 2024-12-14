package com.examinator.exam;

import java.util.List;

public interface ExamService {

    Exam findById(Long id);

    Exam save(Exam exam);

    void delete(Exam exam);

    List<Exam> loadAll();
}
