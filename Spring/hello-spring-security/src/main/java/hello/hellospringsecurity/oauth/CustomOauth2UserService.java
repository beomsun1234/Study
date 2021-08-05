package hello.hellospringsecurity.oauth;

import hello.hellospringsecurity.Repository.MemberRepository;
import hello.hellospringsecurity.domain.AuthenticationProvider;
import hello.hellospringsecurity.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        /**
         * registrationId
         * 현재 로그인 진행 중인 서비스 구분하는 코드.
         * 이후에 여러가지 추가할 때 네이버인지 구글인지 구분
        */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // oauth2 로그인 진행 시 키가 되는 필드값
        /**
         *OAuth2 로그인 진행 시 키가 되는 필드값 (=Primary Key)
         * 구글 기본 코드: sub, 네이버 카카오 등은 기본 지원 x
         * 이후 네이버, 구글 로그인 동시 지원시 사용
         */
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
        /**
         * OAuthAttributes
         * OAuth2UserService를 통해 가져온 OAuth2User의 attribute
         * 네이버 등 다른 소셜 로그인도 이 클래스 사용
        */
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new SesstionUser(member));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity-> entity.update(attributes.getName())
                )
                .orElse(attributes.toEntity());
        attributes.toEntity();
        return memberRepository.save(member);
    }

    /**
     * registrationId
     * 현재 로그인 진행 중인 서비스를 구분하는 코드입니다.
     * 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용합니다.
     * userNameAttributeName
     * OAuth2 로그인 진행 시 키가 되는 필드값을 이야기합니다. Primary Key와 같은 의미입니다.
     * 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않습니다. 구글의 기본 코드는 "sub"입니다.
     * OAuthAttributes
     * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스입니다.
     * 이후 네이버 등 다른 소셜 로그인도 이 클래스 사용합니다.
     * 바로 아래에서 이 클래스의 코드가 나오니 차례로 생성하시면 됩니다.
     * SessionUser
     * 세션에 사용자 정보를 저장하기 위한 Dto 클래스입니다.
     */
}
