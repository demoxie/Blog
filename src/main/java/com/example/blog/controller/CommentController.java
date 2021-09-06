package com.example.blog.controller;

import com.example.blog.model.Comments;
import com.example.blog.services.CommentServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentServices commentServices;

    public CommentController(CommentServices commentServices) {
        this.commentServices = commentServices;
    }
    @PostMapping(value="/comment")
    private ResponseEntity<Comments> comment(@RequestBody Comments comments, HttpSession httpSession){
        Comments comments1 = commentServices.doComment(comments,httpSession);
        //System.out.println(comments.getPosts().getPostID());

        var headers = new HttpHeaders();
        headers.add("Responded", "MyController");

        return ResponseEntity.accepted().headers(headers).body(comments1);
    }
}
