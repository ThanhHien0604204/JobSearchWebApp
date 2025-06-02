/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.controllers;

import com.ntth.services.JobPostingsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ntth.pojo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiJobPostingController {

    @Autowired
    private JobPostingsService jobService;

    @DeleteMapping("/jobpostings/{id}")
    public ResponseEntity<Void> destroy(@PathVariable("id") int id) {
        this.jobService.deleleJobPostings(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jobs")
    public ResponseEntity<Map<String, Object>> getJobPostings(
            @RequestParam Map<String, String> params) {
        Map<String, Object> response = jobService.getJob(params);
        return ResponseEntity.ok(response);
    }
}
