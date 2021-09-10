package com.example.blog.controller;

import com.example.blog.model.Posts;
import com.example.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping(value = "/create-post")
    private ResponseEntity<Posts> createPost(@RequestBody Posts posts, HttpSession httpSession){
        Posts post = postService.createPost(posts, httpSession);

        var headers = new HttpHeaders();
        headers.add("Responded", "MyController");

        return ResponseEntity.accepted().headers(headers).body(post);
    }
    @GetMapping (value = "/delete-post/{postId}")
    private String delete(@PathVariable Long postId,HttpSession httpSession){
        return postService.deletePost(postId, httpSession);
    }
    @PutMapping("/edit-post/{postId}")
    public Posts editPost(@PathVariable Long postId,@RequestBody Posts posts,HttpSession httpSession) {
        return postService.editPost(postId,posts,httpSession);
    }
    @GetMapping (value = "/like-post/{postId}")
    private String likePost(@PathVariable Long postId,HttpSession httpSession){
        return postService.deletePost(postId, httpSession);
    }


}
