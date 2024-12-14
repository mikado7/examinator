package com.examinator.exam.subject;

import java.util.Set;

public interface SubjectService {

    Subject findSubjectById(Long id);

    Subject saveSubject(Subject subject);

    void deleteSubject(Long id);

    Set<Subject> getAllSubjectsByOwnerUsername(String username);
}
