package com.bs.hellodockercompose.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BoardSaveDto {
    private String title;

    private String content;


    @Builder
    public BoardSaveDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Board toEntity(){
        return Board.builder().title(title).content(content).build();
    }
}
