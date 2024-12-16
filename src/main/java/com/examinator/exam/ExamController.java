package com.examinator.exam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping(value = "/exam")
    @ResponseBody
    public ResponseEntity<Exam> post(@RequestBody ExamDTO exam) {
        if (exam.getId() != null) {
            examService.findById(exam.getId());
            return new ResponseEntity<>(examService.save(exam), HttpStatus.OK);
        }
        return new ResponseEntity<>(examService.save(exam), HttpStatus.CREATED);
    }

    @GetMapping(value = "/exam/{id}")
    @ResponseBody
    public ResponseEntity<Exam> get(@PathVariable Long id) {
        return new ResponseEntity<>(examService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/exam{id}")
    @ResponseBody
    public ResponseEntity<Exam> delete(@PathVariable Long id) {
        Exam exam = examService.findById(id);
        examService.delete(exam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/exam", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExamDTO>> loadAllExams() {
        return new ResponseEntity<>(examService.loadAll(), HttpStatus.OK);
    }

}
