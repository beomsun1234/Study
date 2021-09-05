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

    @DisplayName("커스텀repo test 동적쿼리 BooleanBulider사용")
    @Test
    public void test02() {
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
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining("tes","");

        //then
        Assertions.assertThat(posts.size()).isEqualTo(2);
    }

    @DisplayName("커스텀repo test BooleanExpression 사용")
    @Test
    public void test03() {
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
        List<Post> posts = postRepository.findByTitleContainingOrContentContainingUsingBE("tes", "tes");

        //then
        Assertions.assertThat(posts.size()).isEqualTo(2);
    }
    @DisplayName("커스텀repo test BooleanExpression 사용 어떤 작성자가 작성한 찾고자하는 제목과 컨텐츠")
    @Test
    public void test04() {
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
        List<Post> posts = postRepository.findDynamicQuery("test","", "park");

        //then
        Assertions.assertThat(posts.size()).isEqualTo(2);

    }
}