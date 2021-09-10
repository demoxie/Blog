package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.model.PostLikers;
import com.example.blog.model.Posts;
import com.example.blog.repository.PostLikersRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.services.PostService;
import com.sun.istack.NotNull;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostLikersRepository postLikersRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,PostLikersRepository postLikersRepository) {

        this.postRepository = postRepository;
        this.postLikersRepository = postLikersRepository;
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
    public String deletePost(Long postId, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute("BlogUser");
        String response = "";
        if(loginUser != null ){
            Long id = loginUser.getUserID();
            Optional<Posts> posts = Optional.ofNullable(postRepository.findPostsByBloguserAndPostID(loginUser, postId));
            if(posts.isPresent()){
                Posts savedPost = posts.get();
                System.out.println(savedPost.getContent());
                postRepository.delete(savedPost);
                response = "deleted successfully";
                return response;
            }
        }
        return null;

    }

    @Override
    public Posts editPost(Long postId, Posts posts, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute("BlogUser");
        if(loginUser == null ){
            return null;
        }else
        return postRepository.findById(postId)
                .map(post -> {
                    post.setContent(posts.getContent());
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post not found " + postId));
    }

    @Override
    public PostLikers likePost(Long postId, BlogUser blogUser, HttpSession httpSession) {
        PostLikers postLikers = postLikersRepository.findByPostIDAndBloguser(postId,blogUser);
        Posts post = postRepository.findPostsByBloguserAndPostID(blogUser,postId);
        if(postLikers == null){
            post.setNoOfLikes(post.getNoOfLikes()+1);
            postRepository.save(post);
           /* PostLikers newPostLikers = new PostLikers();
            newPostLikers.setPosts(post);
            newPostLikers.*/
        }
        post.setNoOfLikes(post.getNoOfLikes()-1);
        postRepository.save(post);
        return  null;

    }
}
