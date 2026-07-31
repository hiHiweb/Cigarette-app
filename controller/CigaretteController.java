package com.example.cigarette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.cigarette.model.AssessmentForm;
import com.example.cigarette.service.CigaretteService;

@Controller
@SessionAttributes("assessmentForm")
public class CigaretteController {

    @Autowired
    private CigaretteService cigaretteService;

 
    @ModelAttribute("assessmentForm")
    public AssessmentForm setUpForm() {
        return new AssessmentForm();
    }

    @GetMapping("/q{num}")
    public String showQuestion(@PathVariable int num, Model model) {
        int totalSteps = 10;
        int progress = ((num-1) * 100) / totalSteps;
        model.addAttribute("progress", progress);
        model.addAttribute("num", num);
        return "q" + num;
    }

    @PostMapping("/q{num}")
    public String processQuestion(@PathVariable int num, 
                                  @RequestParam String answer,
                                  @ModelAttribute("assessmentForm") AssessmentForm form) {
        switch(num) {
            case 1 -> form.setQ1(answer);
            case 2 -> form.setQ2(answer);
            case 3 -> form.setQ3(answer);
            case 4 -> form.setQ4(answer);
            case 5 -> form.setQ5(answer);
            case 6 -> form.setQ6(answer);
            case 7 -> form.setQ7(answer);
            case 8 -> form.setQ8(answer);
            case 9 -> form.setQ9(answer);
            case 10 -> form.setQ10(answer);
        }

        if (num < 10) {
            return "redirect:/q" + (num + 1);
        }
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String showResult(@ModelAttribute("assessmentForm") AssessmentForm form, 
                             Model model, 
                             SessionStatus sessionStatus) {
        String result = cigaretteService.calculateResult(form);
        model.addAttribute("ResultRecommendApp", result);
        
        return "ResultRecommendApp";
    }
}
