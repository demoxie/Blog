package com.example.blog.services;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Posts;

import javax.servlet.http.HttpSession;

public interface PostService {
    Posts createPost(Posts posts, HttpSession httpSession);
    void deletePost(Posts posts, BlogUser blogUser);

}
