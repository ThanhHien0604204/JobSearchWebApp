/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.repositories.impl;

import com.ntth.pojo.User;
import com.ntth.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository//đánh dấu UserRepositoryImpl là một Spring bean
//nên Spring sẽ tự động tạo bean cho nó.
@Transactional//đảm bảo các phương thức chạy trong một giao dịch
public class UserRepositoryImpl implements UserRepository {

//    @Autowired
//    private LocalSessionFactoryBean factory;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
    private final SessionFactory sessionFactory;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryImpl(LocalSessionFactoryBean factory, BCryptPasswordEncoder passwordEncoder) {
        this.sessionFactory = factory.getObject();
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        Session s = sessionFactory.getCurrentSession();
        try {
            // Mã hóa mật khẩu
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Đặt active dựa trên role
//            if (user.getRole() == User.Role.EMPLOYER) {
//                user.setActive(false); // Chờ phê duyệt
//            } else {
//                user.setActive(true); // CANDIDATE hoặc ADMIN
//            }
            // Lưu user
            s.persist(user);//để thêm bản ghi mới.
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error adding user: " + e.getMessage());
        }
    }

    //Sử dụng Hibernate
    @Override
    public User getUserByUsername(String username) {
        Session s = sessionFactory.getCurrentSession();
        Query q = s.createQuery("FROM User WHERE username = :username", User.class);
        q.setParameter("username", username);

        try {
            Query query = s.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("[ERROR] Error finding user: " + e.getMessage());
            throw new RuntimeException("Lỗi khi tìm user theo username: " + e.getMessage(), e);
        }
    }

//    @Override
//    public boolean existsByUsername(String username) {
//        Session session = factory.getObject().getCurrentSession();
//        Query query = session.createQuery("SELECT COUNT(*) FROM User WHERE username = :username", Long.class);
//        query.setParameter("username", username);
//
//        try {
//            return query.getSingleResult() > 0;
//        } catch (NoResultException e) {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean existsByEmail(String email) {
//        Session session = factory.getObject().getCurrentSession();
//        Query query = session.createQuery("SELECT COUNT(*) FROM User WHERE email = :email", Long.class);
//        query.setParameter("email", email);
//
//        try {
//            return query.getSingleResult() > 0;
//        } catch (NoResultException e) {
//            return false;
//        }
//    }
    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    //Admin duyệt active cho EMPLOYER
    public void activateUser(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setActive(true);
            save(user);
        }
    }
}
