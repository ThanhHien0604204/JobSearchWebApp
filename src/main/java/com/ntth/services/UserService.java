/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ntth.services;

import com.ntth.pojo.User;
import com.ntth.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author LOQ
 */
public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);
    User authenticate(String username, String password);
    boolean isEmployerApproved(Integer userId);
    User addUser(User user);
    void save(User user);
    UserRepository getUserRepository();
    UserDetails loadUserByUsername(String username);
}
