package com.examinator.exam.question;

import com.examinator.exam.Exam;
import com.examinator.exam.ExamService;
import com.examinator.security.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ExamService examService;

    public QuestionController(QuestionService questionService, UserDetailsService userDetailsService, UserService userService, ExamService examService) {
        this.questionService = questionService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.examService = examService;
    }

    @PostMapping(value = "/question")
    @ResponseBody
    public ResponseEntity<Question> post(@RequestBody QuestionDTO dto) {
        Exam e = examService.findById(dto.getExamId());
        return new ResponseEntity<>(questionService.save(dto, e), HttpStatus.CREATED);
    }

    @GetMapping(value = "/question/{id}")
    @ResponseBody
    public ResponseEntity<QuestionDTO> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(questionService.findQuestionById(id).getDTO(), HttpStatus.OK);
    }

    @DeleteMapping(value = "question/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return userDetailsService.loadUserByUsername(authentication.getName());
        } else {
            throw new IllegalStateException("User is not authenticated");
        }
    }
}