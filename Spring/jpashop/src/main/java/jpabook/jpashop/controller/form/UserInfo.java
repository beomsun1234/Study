package jpabook.jpashop.controller.form;


import jpabook.jpashop.domain.Member;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String userName;
    private String city;
    private String street;
    private String homeCode;

    @Builder
    public UserInfo(Member member){
        id = member.getId();
        userName = member.getName();
        city = member.getAddress().getCity();
        street = member.getAddress().getStreet();
        homeCode = member.getAddress().getHomecode();
    }

}
