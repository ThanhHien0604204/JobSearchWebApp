/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ntth.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ntth.filters.ApiAuthenticationFilter;
import com.ntth.filters.JwtFilter;
import com.ntth.util.JwtUtils;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.ntth",
    "com.ntth.controllers",
    "com.ntth.repositories",
    "com.ntth.services"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Environment environment;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dlujkp27n",
                        "api_key", "249677818335624",
                        "api_secret", "88ZX0HPUwOMHSUVEDq7FJl1VGHk",
                        "secure", true));
        return cloudinary;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/home").authenticated()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/jobpostings/**").hasAuthority("EMPLOYER")
                .requestMatchers("/jobpostings", "/jobpostings/**", "/add").hasAuthority("EMPLOYER")
                .requestMatchers("/applications/**").permitAll()
                .requestMatchers("/follow").permitAll()
                .requestMatchers("/feedback", "/feedback/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/company").permitAll()
                .requestMatchers("/company/**").hasAuthority("CANDIDATE")
                .requestMatchers("/listapplications/**").permitAll()
                .requestMatchers("/addapplication").hasAuthority("CANDIDATE")
                .requestMatchers("/follow/**").hasAuthority("CANDIDATE")
                .requestMatchers("/jobapplications/**").hasAuthority("EMPLOYER")
                .requestMatchers("/user/**").hasAuthority("ADMIN")
                .requestMatchers("/user").permitAll()
                .requestMatchers("/stats", "/stats/**").hasAuthority("ADMIN")
                //.requestMatchers("/api/jobs/**").hasAuthority("EMPLOYER")
                .requestMatchers("/", "/index", "/login", "/register", "/js/**").permitAll()
                .requestMatchers("/api/secure/**").authenticated() // Bảo vệ /api/secure/**
                .requestMatchers("/api/**").permitAll())
                .addFilterBefore(new ApiAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)// Thêm filter cho /api/login
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                //.anyRequest().authenticated())
                .formLogin(form -> form
                .loginPage("/login")//đn thành công chuyển hướng đến /login
                .loginProcessingUrl("/perform_login")//form đăng nhập gửi dữ liệu đến
                .successHandler((request, response, authentication) -> {
                    System.out.println("[DEBUG] Login success for user: " + authentication.getName() + ", role: " + authentication.getAuthorities());
                    String role = authentication.getAuthorities().stream()
                            .findFirst()
                            .map(gr -> gr.getAuthority())
                            .orElse("");
                    if (role.equals("ADMIN")) {
                        response.sendRedirect("/JobSearchWebApp/admin/dashboard");
                    } else {
                        response.sendRedirect("/");
                    }
                })
                .defaultSuccessUrl("/", true)//Nếu đăng nhập thành công, người dùng sẽ được chuyển hướng đến URL 
                //.failureUrl("/login?error=true").permitAll())
                .failureHandler((request, response, exception) -> {
                    String errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng";
                    if (exception.getMessage().contains("chưa được kích hoạt")) {
                        errorMessage = "Tài khoản chưa được kích hoạt";
                    } else if (exception.getMessage().contains("chưa được phê duyệt")) {
                        errorMessage = "Tài khoản EMPLOYER chưa được phê duyệt";
                    }
                    request.getSession().setAttribute("error", errorMessage);
                    response.sendRedirect("/JobSearchWebApp/login?error=true");
                })
                .permitAll())
                .logout(logout -> logout
                .logoutSuccessUrl("/").permitAll())
                .exceptionHandling(e -> e
                .accessDeniedPage("/login"));//người dùng cố gắng truy cập một URL mà họ không có quyền

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");  // Thay bằng SMTP server của bạn
        mailSender.setPort(587);
        mailSender.setUsername("phuongnhu308@gmail.com");
        mailSender.setPassword("xnxs waud thnb qsbk");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}
