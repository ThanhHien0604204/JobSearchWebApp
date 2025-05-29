/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ntth.repositories;

import com.ntth.pojo.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author LOQ
 */
public interface CompanyRepository {
    void save(Company company);
    Company findByUserId(Integer userId);
}
