package com.duckduckgoose.app.models.database;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Honk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Member author;

    private String content;

    private Date timestamp;

    protected Honk() {}

    public Honk(Member author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return ((Honk)o).getId().equals(this.id);
    }

    public Long getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
