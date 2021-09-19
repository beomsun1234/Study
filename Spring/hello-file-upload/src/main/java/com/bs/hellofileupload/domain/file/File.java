package com.bs.hellofileupload.domain.file;


import com.bs.hellofileupload.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    
    private String originalName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public File(String filePath, String originalName, Board board){
        this.filePath = filePath;
        this.originalName = originalName;
        this.board = board;
    }

    public File update(String filePath, String originalName){
        this.filePath = filePath;
        this.originalName = originalName;
        return this;
    }





}
