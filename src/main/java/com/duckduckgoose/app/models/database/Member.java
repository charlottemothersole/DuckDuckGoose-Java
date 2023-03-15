package com.duckduckgoose.app.models.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotBlank
    private String hashedPassword;

    @OneToMany(mappedBy = "author")
    private List<Honk> honks;

    @ManyToMany(mappedBy = "followedMembers", fetch = FetchType.EAGER)
    private Set<Member> followerMembers;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Member> followedMembers;

    protected Member() {}

    public Member(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.honks = List.of();
        this.followerMembers = Set.of();
        this.followedMembers = Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return ((Member)o).getId().equals(this.id);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Honk> getHonks() {
        return honks;
    }

    public Set<Member> getFollowerMembers() {
        return followerMembers;
    }

    public Set<Member> getFollowedMembers() {
        return followedMembers;
    }
}
