package com.examinator.exam.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Set<Subject> findAllByOwner_Username(String username);
}
