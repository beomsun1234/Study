package com.bs.helloquerydsl.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Reply {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private String author;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Reply(String content, String author, Post post){
        this.content = content;
        this.author = author;
        this.setPost(post);
    }

    public void setPost(Post post){
        this.post = post;
        post.getReplies().add(this);
    }
}
