/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.repositories.impl;

import com.ntth.pojo.Job;
import com.ntth.repositories.JobPostingsRepository;
import jakarta.data.repository.Repository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author LOQ
 */
@Repository
@Transactional//bật giao tác
public class JobPostingsRepositoryImpl implements JobPostingsRepository {

    private static final int PAGE_SIZE = 6;

//    @Autowired//có autowired mới có đối tượng
//    private LocalSessionFactoryBean factory;
    private final SessionFactory sessionFactory;

    @Autowired
    public JobPostingsRepositoryImpl(LocalSessionFactoryBean factory) {
        this.sessionFactory = factory.getObject();
    }

    @Override
    public List<Job> getJob(Map<String, String> params) {
        Session s = sessionFactory.getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();//chưa tất cả truy vấn
        CriteriaQuery<Job> q = b.createQuery(Job.class);
        Root root = q.from(Job.class);
        q.select(root);//lấy hết
        //tao 1 vị từ
        List<Predicate> predicates = new ArrayList<>();
        // Chỉ lấy công việc từ công ty được phê duyệt
        predicates.add(b.equal(root.get("companyId").get("approved"), true));
        if (params != null) {
            // Từ khóa tìm kiếm theo name
            String kw = params.get("kw");
            //do nếu dùng where để truy vấn thì chỉ đc dùng where 1 lần
            //mà đề yêu cầu truy vấn nhiều dạng=> đưa vào ds vị từ trc
             if (kw != null && !kw.trim().isEmpty()) {
                predicates.add(b.like(b.lower(root.get("title")), "%" + kw.toLowerCase() + "%"));
            }
            // Mức lương từ
            String salaryFrom = params.get("salaryFrom");
           if (salaryFrom != null && !salaryFrom.trim().isEmpty()) {
                try {
                    predicates.add(b.greaterThanOrEqualTo(root.get("salaryFrom"), new BigDecimal(salaryFrom)));
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Invalid salaryFrom: " + salaryFrom);
                }
            }

            // Mức lương đến
            String salaryTo = params.get("salaryTo");
            if (salaryTo != null && !salaryTo.trim().isEmpty()) {
                try {
                    predicates.add(b.lessThanOrEqualTo(root.get("salaryTo"), new BigDecimal(salaryTo)));
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Invalid salaryTo: " + salaryTo);
                }
            }
            // Danh mục công việc
            String categoryId = params.get("categoryId");
            if (categoryId != null && !categoryId.trim().isEmpty()) {
                try {
                    predicates.add(b.equal(root.get("categoryId").get("id"), Integer.parseInt(categoryId)));
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Invalid categoryId: " + categoryId);
                }
            }
            // Địa điểm
            String location = params.get("location");
            if (location != null && !location.trim().isEmpty()) {
                predicates.add(b.like(b.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }

//            String cateId = params.get("categoryId");
//            if (cateId != null && !cateId.isEmpty()) {
//                predicates.add(b.equal(root.get("categoryId").as(Integer.class), cateId));
//            }                       //trường khóa ngoại chỉ kiểu số nguyên để chỉ lấy khóa chính so sanh thôi
            // Thời gian làm việc
            String workingTime = params.get("workingTime");
            if (workingTime != null && !workingTime.trim().isEmpty()) {
                predicates.add(b.equal(b.lower(root.get("workingTime")), workingTime.toLowerCase()));
            }
            //tìm kiếm theo bán kính 
//            String latitude = params.get("latitude");
//            String longitude = params.get("longitude");
//            String radius = params.get("radius");
//            if (latitude != null && longitude != null && radius != null &&
//                !latitude.trim().isEmpty() && !longitude.trim().isEmpty() && !radius.trim().isEmpty()) {
//                try {
//                    double lat = Double.parseDouble(latitude);
//                    double lon = Double.parseDouble(longitude);
//                    double rad = Double.parseDouble(radius);
//                    predicates.add(b.lessThan(
//                        b.function("acos", Double.class,
//                            b.sum(
//                                b.prod(
//                                    b.function("cos", Double.class, b.function("radians", Double.class, b.literal(lat))),
//                                    b.function("cos", Double.class, b.function("radians", Double.class, root.get("latitude"))),
//                                    b.function("cos", Double.class,
//                                        b.diff(
//                                            b.function("radians", Double.class, root.get("longitude")),
//                                            b.function("radians", Double.class, b.literal(lon))
//                                        )
//                                    )
//                                ),
//                                b.prod(
//                                    b.function("sin", Double.class, b.function("radians", Double.class, b.literal(lat))),
//                                    b.function("sin", Double.class, b.function("radians", Double.class, root.get("latitude")))
//                                )
//                            )
//                        ).multiply(6371),
//                        rad
//                    ));
//                } catch (NumberFormatException e) {
//                    System.out.println("[ERROR] Invalid latitude/longitude/radius: " + latitude + "/" + longitude + "/" + radius);
//                }
//            }
            //rồi mới dùng where 1 lần
            q.where(predicates.toArray(Predicate[]::new));
        }         //chuyển thành mảng   //cú pháp này tách ra từng phần tử truyền vào where
        Query query = s.createQuery(q);
        //phân trang
        if (params != null && params.containsKey("page")) {
            try {
                int page = Integer.parseInt(params.getOrDefault("page", "1"));
                //k gửi thì mặc định lấy 1
                int start = (page - 1) * PAGE_SIZE;
                query.setMaxResults(PAGE_SIZE);//số phần từ tối đa lấy trong 1 lầy
                query.setFirstResult(start);//vị trí đầu tiên bắt đầu lấy 
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid page: " + params.get("page"));
            }
        }

        return query.getResultList();

    }

    @Override//lấy sp theo mã
    public Job getJobById(int id) {
        Session s = sessionFactory.getCurrentSession();
        return s.get(Job.class, id);//lấy trong class này, và truyền id(lấy theo id)

    }

    @Override
    public Job createOrUpdate(Job p) {
        Session s = sessionFactory.getCurrentSession();
        if (p.getId() == null) {//đang thêm mới
            s.persist(p);
        } else {//khác null là đang cập nhật 
            s.merge(p);
        }

        s.refresh(p);//nạp lại dữ liệu để trả về 

        return p;
    }

    @Override
    public void deleleJob(int id) {
        Session s = sessionFactory.getCurrentSession();
        Job p = this.getJobById(id);//lấy đối tượng ra
        s.remove(p);
    }

}
