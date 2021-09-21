package com.bs.hellofileupload.domain.board;


import com.bs.hellofileupload.domain.BaseTimeEntity;
import com.bs.hellofileupload.domain.file.File;
import com.bs.hellofileupload.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String author;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Board(String title, String content, String author, Member member){
        this.title =title;
        this.content = content;
        this.author = author;
        setMember(member);
    }

    private void setMember(Member member){
        this.member = member;
        member.getBoards().add(this);
    }

    //서비스로직
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
