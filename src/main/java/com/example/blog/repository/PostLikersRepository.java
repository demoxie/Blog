package com.example.blog.repository;

import com.example.blog.model.BlogUser;
import com.example.blog.model.PostLikers;
import com.example.blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpSession;

public interface PostLikersRepository extends JpaRepository<Posts, BlogUser> {
    PostLikers likeAPost(Posts post,BlogUser blogUser);
    PostLikers findByPostIDAndBloguser(Long postId, BlogUser blogUser);
}
