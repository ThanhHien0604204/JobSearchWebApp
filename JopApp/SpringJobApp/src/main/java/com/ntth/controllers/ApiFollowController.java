/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.controllers;

import com.ntth.pojo.User;
import com.ntth.services.FollowService;
import com.ntth.services.JobApplicationService;
import com.ntth.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiFollowController {

     @Autowired
    private JobApplicationService jaService;

     @Autowired
    private FollowService followService;
     
    @Autowired
    private UserService userService;
    @PostMapping("/follow/{companyId}")
    public ResponseEntity<?> followCompany(@PathVariable(value = "companyId") int companyId, Principal principal) {
        String username = principal.getName();
        User currentUser = userService.getUserByUsername(username);

        if (currentUser == null || currentUser.getRole() != User.Role.CANDIDATE) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn không có quyền thực hiện hành động này.");
        }
        System.out.println("1 Id nguoi dung hien tai la: "+ currentUser.getId());
        followService.followCompany(currentUser.getId().intValue(), companyId);
        System.out.println("2 Id nguoi dung hien tai la: "+ currentUser.getId());

        return ResponseEntity.ok("Đã theo dõi công ty thành công!");
    }

}
