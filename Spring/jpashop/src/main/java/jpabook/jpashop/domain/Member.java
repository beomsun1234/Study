package jpabook.jpashop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.common.reflection.XMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    private String email;
    private String role;
    private String password;
    private String name;

    @Embedded //내장타입
    private Address address;

    @JsonBackReference
    @OneToMany(mappedBy = "member") //연관관계 주인이아니에요~~  오더테이블에있는 member필드에 의해 맵핑
    private List<Order> orders = new ArrayList<>(); //읽기 전용 //변경 x



}
