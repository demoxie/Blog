package com.example.blog.services;

import com.example.blog.model.BlogUser;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface BlogUserService {
    BlogUser login(BlogUser blogUser, HttpSession httpSession);
    BlogUser register(BlogUser blogUser);
    String logout(HttpSession httpSession);
    void deactivateAccount(BlogUser blogUser);
    ResponseEntity<BlogUser> getBlogUser(Long blogUserID);
    List<BlogUser> getAllBlogUsers();
    BlogUser updateBlogUser(BlogUser blogUser);
    void deleteBlogUser(Long blogUserID);

}
