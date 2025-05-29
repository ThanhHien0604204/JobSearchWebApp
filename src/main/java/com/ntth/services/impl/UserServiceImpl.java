/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ntth.pojo.Company;
import com.ntth.pojo.User;
import com.ntth.repositories.CompanyRepository;
import com.ntth.services.UserService;
import com.ntth.repositories.UserRepository;
import com.ntth.repositories.impl.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
//@Service báo cho Spring rằng đây là một bean cần được quản lý.
//@Service("userDetailsService")//dat ten hat dau
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
//    @Autowired
//    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Đang cố gắng tải người dùng: " + username);
        User u = userRepository.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Tên đăng nhập không tồn tại: " + username);
        }
        if (!u.isActive()) {
            throw new UsernameNotFoundException("Tài khoản chưa được kích hoạt: " + username);
        }
        // Kiểm tra approved cho EMPLOYER
        if (u.getRole() == User.Role.EMPLOYER) {
            Company company = companyRepository.findByUserId(u.getId());
            System.out.println("Company for user " + username + ": " + (company != null ? company.getApproved() : "null"));
            if (company == null || !company.getApproved()) {
                throw new UsernameNotFoundException("Tài khoản EMPLOYER chưa được phê duyệt: " + username);
            }
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole().name()));
        System.out.println("Authorities: " + authorities);
        // Trả về UserDetails
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                authorities
        );
    }

    //xác thực người dùng dựa trên username và password
    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword()) && user.isActive()) {
            return user;
        }
        return null;
    }

    @Override
    public boolean isEmployerApproved(Integer userId) {
        // Giả định kiểm tra trạng thái approved trong bảng company
        return true; 
    }

    @Override
    @Transactional
    public User addUser(User user) {
        // Kiểm tra trùng lặp trước khi thêm (tùy chọn, nếu không đã có trong RegisterController)
//        if (userRepository.existsByUsername(user.getUsername())
//                || userRepository.existsByEmail(user.getEmail())) {
//            throw new RuntimeException("Tên đăng nhập hoặc email đã tồn tại");
//        }
        return this.userRepository.addUser(user);
    }

    @Override
    @Transactional
    public void save(User user) {
        this.userRepository.save(user); // Lưu người dùng vào bảng user
    }

    // Getter để truy cập UserRepository từ Controller
    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

}
