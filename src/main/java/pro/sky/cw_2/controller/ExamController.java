package pro.sky.cw_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.cw_2.model.Question;
import pro.sky.cw_2.service.ExaminerService;

import java.util.Collection;
import java.util.Collections;

@RestController
public class ExamController {

    private final ExaminerService examinerService;

    public ExamController(ExaminerService examinerService) {
        this.examinerService = examinerService;
    }

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuesstions(@PathVariable int amount) {
        return examinerService.getQuestions(amount);
    }
}
