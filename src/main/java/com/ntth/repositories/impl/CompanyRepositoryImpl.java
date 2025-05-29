/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.repositories.impl;

import com.ntth.pojo.Company;
import com.ntth.repositories.CompanyRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LOQ
 */
@Repository
@Transactional
public class CompanyRepositoryImpl implements CompanyRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CompanyRepositoryImpl(LocalSessionFactoryBean factory) {
        this.sessionFactory = factory.getObject();
    }

    @Override
    public void save(Company company) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(company);
        } catch (Exception e) {
            throw new RuntimeException("Error saving company: " + e.getMessage());
        }
    }

    @Override
    public Company findByUserId(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query<Company> query = session.createQuery("FROM Company WHERE userId.id = :userId", Company.class)
                    .setParameter("userId", userId);
            Company company = query.getSingleResultOrNull();
            System.out.println("[DEBUG] Company found: " + (company != null ? "userId=" + company.getUserId().getId() : "null"));
            return company;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tìm công ty theo userId:" + e.getMessage());
        }
    }
}
