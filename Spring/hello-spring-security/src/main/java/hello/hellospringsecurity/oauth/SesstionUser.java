package hello.hellospringsecurity.oauth;

import hello.hellospringsecurity.domain.Member;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class SesstionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SesstionUser(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getName();
    }
}
