package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.repository.BlogUserRepository;
import com.example.blog.services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@Component
public class BlogUserServiceImpl implements BlogUserService {
    private final BlogUserRepository blogUserRepository;
    @Autowired
    public BlogUserServiceImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public BlogUser login(BlogUser blogUser, HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute(blogUser.getUsername());
        if(loginBlogUser != null){
            if(loginBlogUser.getStatus().equalsIgnoreCase("Inactive")){
                System.out.println("====>"+"Your account is deactivated");
                httpSession.removeAttribute(loginBlogUser.getUsername());
                return null;
            }
            return loginBlogUser;
        }

        BlogUser currentUser = blogUserRepository.findBlogUserByUsernameAndPassword(blogUser.getUsername(), blogUser.getPassword());
        if(currentUser.getStatus().equalsIgnoreCase("Inactive")){
            System.out.println("Your account is deactivated");
            return null;
        }
        System.out.println(currentUser.getListOfPostsOwned());
        httpSession.setAttribute(currentUser.getUsername(), currentUser);
        return currentUser;
    }

    @Override
    public BlogUser register(BlogUser blogUser) {
        BlogUser newBlogUser = new BlogUser();
        newBlogUser.setFirstName(blogUser.getFirstName());
        newBlogUser.setLastName(blogUser.getLastName());
        newBlogUser.setGender(blogUser.getGender());
        newBlogUser.setStatus(blogUser.getStatus());
        newBlogUser.setUsername(blogUser.getUsername());
        newBlogUser.setPassword(blogUser.getPassword());
        System.out.println(newBlogUser);
        return blogUserRepository.saveAndFlush(newBlogUser);
    }

    @Override
    public String logout(HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute("BlogUser");
        if(loginBlogUser == null){
            System.out.println("You're not logged in");
        }

        httpSession.removeAttribute("BlogUser");
        httpSession.invalidate();
        return "Logged out successfully";
    }

    @Override
    @Async
    public void deactivateAccount(Long userId, HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if(loginBlogUser == null){
            System.out.println("You're not logged in");
        }
        System.out.println("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            assert loginBlogUser != null;
            loginBlogUser.setStatus("Inactive");
            blogUserRepository.saveAndFlush(loginBlogUser);
            Thread.sleep(10000);
            System.out.println("Account deactivated");
        } catch (InterruptedException e) {
            //
        }
    }

    @Override
    public String cancelDeactivation(Long userId) {
        cancel();
        return null;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public ResponseEntity<BlogUser> getBlogUser(Long blogUserID) {
        Optional<BlogUser> blogUser = Optional.ofNullable(blogUserRepository.findBlogUserByUserID(blogUserID));
        return blogUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public List<BlogUser> getAllBlogUsers() {
        return blogUserRepository.findAll();
    }

    @Override
    public BlogUser updateBlogUser(BlogUser blogUser) {
        BlogUser currentBlogUser = blogUserRepository.findBlogUserByUserID(blogUser.getUserID());
        currentBlogUser.setFirstName(blogUser.getFirstName());
        return blogUserRepository.saveAndFlush(currentBlogUser);
    }

    @Override
    public void deleteBlogUser(Long blogUserID) {

    }
}
