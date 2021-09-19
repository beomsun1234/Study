package com.bs.hellofileupload.dto;

import com.bs.hellofileupload.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.jca.cci.object.SimpleRecordOperation;


@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long boardId;

    private String title;
    private String content;
    private String author;

    private String imagePath;
    private String imageName;

    @Builder
    public BoardResponseDto(Board board){
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        setFile(board);
    }

    private void setFile(Board board){
        if(board.getFile()==null){
            this.imagePath = null;
            this.imageName = null;
        }
        else {
            this.imagePath = board.getFile().getFilePath();
            this.imageName = board.getFile().getOriginalName();
        }
    }


}
