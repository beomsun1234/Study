package jpabook.jpashop.controller.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SignUpForm {
    private String email;
    private String password;
    private String name;
    private String city;
    private String street;
    private String homecode;
}
