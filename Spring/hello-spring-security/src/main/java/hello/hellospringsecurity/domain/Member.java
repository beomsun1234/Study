package hello.hellospringsecurity.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;

    @NonNull
    private String password;

    private String picture;

    @NonNull
    private String name;

    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authenticationProvider;

    @Builder
    public Member(String email, String name,String picture, Role role){
        this.email =email;
        this.name = name;
        this.picture = picture;
        this.role = role;
    }
    public Member update(String name){
        this.name=name;

        return this;
    }


}
