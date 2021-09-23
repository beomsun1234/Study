package com.bs.hellofileupload.dto;

import com.bs.hellofileupload.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;

    private String email;

    private String name;

    @Builder
    public MemberInfoDto(Member entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
    }
}
