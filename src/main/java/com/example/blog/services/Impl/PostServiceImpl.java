package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Posts;
import com.example.blog.repository.PostRepository;
import com.example.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Posts createPost(Posts posts, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute("BlogUser");

        if(loginUser == null ){
            return null;
        }

        Posts post = new Posts();
        post.setContent(posts.getContent());
        post.setBloguser(loginUser);
        return postRepository.saveAndFlush(post);
    }

    @Override
    public void deletePost(Posts posts, BlogUser blogUser) {

    }
}
