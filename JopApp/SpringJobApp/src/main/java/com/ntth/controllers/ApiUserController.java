/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.controllers;

import com.ntth.pojo.Feedback;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ntth.pojo.User;
import com.ntth.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author User
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbacks(@PathVariable(value = "userId") int id) {
        return new ResponseEntity<>(this.userService.getFeedbacks(id), HttpStatus.OK);
    }
     @PostMapping("/users")
    public ResponseEntity<?> registerUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("avatar") MultipartFile avatar,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "taxCode", required = false) String taxCode,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "website", required = false) String website,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2,
            @RequestParam(value = "image3", required = false) MultipartFile image3) {

        Map<String, Object> response = userService.registerUser(
                firstName, lastName, email, phone, username, password, role,
                avatar, companyName, taxCode, description, address, website,
                image1, image2, image3);

        if ((boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        Map<String, Object> response = userService.authenticateUser(username, password);

        if ((boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    public ResponseEntity<?> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = userService.getUserByUsername(userDetails.getUsername());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
    }
}
