package hello.hellospringsecurity.oauth;
import hello.hellospringsecurity.domain.AuthenticationProvider;
import hello.hellospringsecurity.domain.Member;
import hello.hellospringsecurity.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private AuthenticationProvider authenticationProvider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name, String email, String picture,AuthenticationProvider authenticationProvider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authenticationProvider = authenticationProvider;
    }
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .authenticationProvider(AuthenticationProvider.GOOGLE)
                .build();
    }

    /**
     * toEntity()
     * User 엔티티 생성
     * OAuthAttributes에서 엔티티 생성 시점 = 처음 가입 시
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스 생성
     * @return
     */
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.ROLE_USER)
                .authenticationProvider(authenticationProvider)
                .build();
    }

}
