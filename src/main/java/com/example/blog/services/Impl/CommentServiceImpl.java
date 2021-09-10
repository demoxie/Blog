package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Comments;
import com.example.blog.model.Posts;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentServices {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comments doComment(Comments comments, HttpSession httpSession) {
        BlogUser user = (BlogUser) httpSession.getAttribute("BlogUser");
        if(user == null){
            System.out.println("its null");
            return null;
        }
        System.out.println(comments.getPosts().getPostID());
        Optional<Posts> postsOptional = postRepository.findById(comments.getPosts().getPostID());
        Posts posts = new Posts();
        if(postsOptional.isPresent()) {
            posts = postsOptional.get();
            System.out.println(posts);
        }
        System.out.println(posts);
        comments.setPosts(posts);
        comments.setBloguser(user);
        return commentRepository.save(comments);
    }

    @Override
    public void deleteComment(Long postId,Long commentId,HttpSession httpSession) {
        BlogUser user = (BlogUser) httpSession.getAttribute("BlogUser");

        if(user == null){
            System.out.println("its null");
        }
        Posts post = new Posts();
        Optional<Posts> posts = postRepository.findById(postId);
        if(posts.isPresent()){
            post = posts.get();
        }
        commentRepository.delete(commentRepository.findCommentsByBloguserAndPostsAndCommentID(user,post,commentId));

    }

    @Override
    public Comments editComment(Long postId, Long commentId,Comments comment, HttpSession httpSession) {
        BlogUser loggedInUser = (BlogUser) httpSession.getAttribute("BlogUser");

        if(loggedInUser == null){
            System.out.println("its null");
        }
        Posts post = new Posts();
        Optional<Posts> posts = postRepository.findById(postId);
        if(posts.isPresent()){
            post = posts.get();
        }
        Comments comments = commentRepository.findCommentsByBloguserAndPostsAndCommentID(loggedInUser,post,commentId);
        comments.setComment(comment.getComment());
        return commentRepository.save(comments);
    }
}
