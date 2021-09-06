package com.example.blog.model;

import javax.persistence.ElementCollection;
import java.util.Set;

public class postLikers {
    private Posts posts;
    @ElementCollection
    private Set<BlogUser> blogUser;
}
