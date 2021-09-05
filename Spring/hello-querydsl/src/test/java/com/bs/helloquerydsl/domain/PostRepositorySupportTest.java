package com.bs.helloquerydsl.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class PostRepositorySupportTest {

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    @Autowired
    private PostRepository postRepository;

    
    @DisplayName("postRepositorySupport 테스트")
    @Test
    public void Title이T인_2건의_Post찾기(){
        //given
        String title = "testT";
        String content = "testC";
        String author = "park";
        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(author).build();

        postRepository.save(post);

        String title1 = "testT";
        String content2 = "testC";
        String author2 = "park";
        Post post2 = Post.builder()
                .title(title1)
                .content(content2)
                .author(author2).build();

        postRepository.save(post2);

        //when
        List<Post> posts = postRepositorySupport.findByTitle("testT");

        //then
        Assertions.assertThat(posts.size()).isEqualTo(2);

    }
}