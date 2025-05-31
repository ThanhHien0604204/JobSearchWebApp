/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ntth.services;

import com.ntth.pojo.User;
import com.ntth.repositories.UserRepository;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LOQ
 */
public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    Map<String, Object> authenticateUser(String username, String password);
    boolean isEmployerApproved(Integer userId);
    User addUser(User user);
    void save(User user);
    UserRepository getUserRepository();
    UserDetails loadUserByUsername(String username);
     Map<String, Object> registerUser(
            String firstName,
            String lastName,
            String email,
            String phone,
            String username,
            String password,
            String role,
            MultipartFile avatar,
            String companyName,
            String taxCode,
            String description,
            String address,
            String website,
            MultipartFile image1,
            MultipartFile image2,
            MultipartFile image3);
}
