package com.example.blog.services;

import com.example.blog.model.BlogUser;
import com.example.blog.model.PostLikers;
import com.example.blog.model.Posts;

import javax.servlet.http.HttpSession;

public interface PostService {
    Posts createPost(Posts posts, HttpSession httpSession);
    String deletePost(Long postId,HttpSession httpSession);
    Posts editPost(Long postId,Posts posts, HttpSession httpSession);
    PostLikers likePost(Long postId,BlogUser blogUser,HttpSession httpSession);

}
