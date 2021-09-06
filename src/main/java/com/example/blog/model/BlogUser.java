package com.example.blog.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String status;
    private String username;
    private String password;
    private String profilePicsUrl;

    @OneToMany(mappedBy = "bloguser",cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Comments> comments;

    @OneToMany(mappedBy = "bloguser",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posts> posts;

/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bloguser")
    private List<Comments> comments;*/
  /*  @OneToMany
    private List<Posts> posts;*/


}
