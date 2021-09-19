package com.bs.hellofileupload.domain.board;


import com.bs.hellofileupload.domain.BaseTimeEntity;
import com.bs.hellofileupload.domain.file.File;
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

    @Builder
    public Board(String title, String content, String author){
        this.title =title;
        this.content = content;
        this.author = author;
    }

    //서비스로직
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
