package com.bs.helloquerydsl.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class PostRepositoryImplTest {

    @Autowired
    private PostRepository postRepository;


    @DisplayName("커스텀repo test")
    @Test
    public void test01(){
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
        List<Post> posts = postRepository.findByTitle("testT");

        //then
        Assertions.assertThat(posts.size()).isEqualTo(2);
    }

}