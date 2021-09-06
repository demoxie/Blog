package com.example.blog.model;

import javax.persistence.ElementCollection;
import java.util.List;

public class commentersToComment {
    private Comments comments;
    @ElementCollection
    private List<BlogUser> blogUser;
}
