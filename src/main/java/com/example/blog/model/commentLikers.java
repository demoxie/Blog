package com.example.blog.model;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Set;

public class commentLikers {
    private Comments comments;
    @ElementCollection
    private Set<BlogUser> blogUsers;
}
