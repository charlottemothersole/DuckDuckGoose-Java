package com.duckduckgoose.app.models.database;

import jakarta.persistence.*;

@Entity
public class Honk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Member author;

    private String content;

    protected Honk() {}

    public Honk(Member author, String content) {
        this.author = author;
        this.content = content;
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

}
