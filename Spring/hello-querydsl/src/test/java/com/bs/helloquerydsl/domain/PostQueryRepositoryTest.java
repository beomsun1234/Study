package com.bs.helloquerydsl.domain;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
class PostQueryRepositoryTest {


    @Autowired
    private PostQueryRepository postQueryRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;


    @BeforeEach
    void setPost(){
        Post post1 = Post.builder()
                .title("spring")
                .content("querydsl")
                .author("park")
                .build();
        postRepository.save(post1);
        Post post2 = Post.builder()
                .title("mvc")
                .content("spring")
                .author("park")
                .build();
        postRepository.save(post2);

    }
    
    @DisplayName("park이 작성한 모든 포스트")
    @Test
    void test01(){

        //when
        List<Post> posts = postQueryRepository.findByAuthor("park");
        //then
        assertThat(posts.size()).isEqualTo(2);
    }
    @DisplayName("기본조회, 특정 제목을 포함하는 모든 포스트")
    @Test
    void test01_1(){
        //given
        Post post3 = Post.builder()
                .title("spring")
                .content("querydsl")
                .author("park")
                .build();
        postRepository.save(post3);

        String searchText = "spring";
        //when
        List<Post> posts = postQueryRepository.findByContaingTitle(searchText);
        //then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getTitle()).isEqualTo("spring");
    }
    @DisplayName("기본조회, 특정 컨텐츠를 포함하는 모든 포스트")
    @Test
    void test01_2(){
        //given
        Post post3 = Post.builder()
                .title("spring")
                .content("querydsl")
                .author("park")
                .build();
        postRepository.save(post3);

        String searchText = "querydsl";
        //when
        List<Post> posts = postQueryRepository.findByCntatingContent(searchText);
        //then
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getContent()).isEqualTo("querydsl");
    }

    @DisplayName("동적쿼리, spring이라는 단어가 포함된 title,content중 하나라도 가지고있는 모든 post")
    @Test
    void test02(){
        //given
        // 2개추가 총 4개
        Post post3 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post3);

        Post post4 = Post.builder()
                .title("notexists")
                .content("notexists")
                .author("park")
                .build();
        postRepository.save(post4);
        //when
        String searchText = "spring";
        List<Post> posts = postQueryRepository.findByContaingTitleOrContatingContent(searchText, searchText);

        System.out.println(posts.size());
        //then 
        //3개가 존재해야한다.
        assertThat(posts.size()).isEqualTo(3);
    }

    @DisplayName("동적쿼리, 검색값이 null일경우 전체 post 조회")
    @Test
    void test03(){
        //given
        Post post3 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post3);
        Post post4 = Post.builder()
                .title("test1")
                .content("test1")
                .author("park")
                .build();
        postRepository.save(post4);
        Post post5 = Post.builder()
                .title("tset2")
                .content("test2")
                .author("park")
                .build();
        postRepository.save(post5);
        //when
        List<Post> posts = postQueryRepository.findByContaingTitleOrContatingContent("", "");
        //then
        assertThat(posts.size()).isEqualTo(5);

    }
    @DisplayName("Join test, 특정 포스트id에 작성된 모든 댓글보기")
    @Test
    void test04() {
        //given
        Reply replybyKim = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply").author("kim").build();
        Reply replybyKim2 = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply2").author("kim").build();
        Reply replybyKim3 = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply3").author("kim").build();
        List<Reply> replies = new ArrayList<>();
        replies.add(replybyKim);
        replies.add(replybyKim2);
        replies.add(replybyKim3);
        replyRepository.saveAll(replies);

        //when
        List<Reply> reply = postQueryRepository.findAllReplyByPostId(1L);

        //then
        assertThat(reply.size()).isEqualTo(replies.size());
    }
    @DisplayName("Join test,   left join -> 모든 포스트와 해당 포스트에 댓글도 함께")
    @Test
    @Transactional
    void test05() {
        //given
        Reply replybyKim = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply").author("kim").build();
        Reply replybyKim2 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply2").author("kim").build();
        Reply replybyKim3 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply3").author("kim").build();
        List<Reply> replies = new ArrayList<>();
        replies.add(replybyKim);
        replies.add(replybyKim2);
        replies.add(replybyKim3);
        replyRepository.saveAll(replies);
        //when
        List<Tuple> posts = postQueryRepository.leftJoin();
        //then
        assertThat(posts.size()).isEqualTo(3);
    }
    @DisplayName("Join test, right join -> 모든 댓글과 댓글에 대한 포스트 함께 조회(4개의 포스트에 3개의 댓글존재)")
    @Test
    @Transactional
    void test06() {
        //given
        Post post3 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post3);
        Post post4 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post4);
        Reply replybyKim = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply").author("kim").build();
        Reply replybyKim2 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply2").author("kim").build();
        Reply replybyKim3 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply3").author("kim").build();
        List<Reply> replies = new ArrayList<>();
        replies.add(replybyKim);
        replies.add(replybyKim2);
        replies.add(replybyKim3);
        replyRepository.saveAll(replies);
        //when
        List<Tuple> posts = postQueryRepository.rightJoin();
        //then
        assertThat(posts.size()).isEqualTo(3);
    }

    @DisplayName("Join test, fetch join 연관된 엔티티나 컬렉션을 한번에 같이 조회해온다.")
    @Test
    @Transactional
    void test07() {
        //given
        Post post3 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post3);
        Post post4 = Post.builder()
                .title("spring")
                .content("mvc")
                .author("park")
                .build();
        postRepository.save(post4);
        Reply replybyKim = Reply.builder().post(postRepository.findById(1L).orElseThrow()).content("reply").author("kim").build();
        Reply replybyKim2 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply2").author("kim").build();
        Reply replybyKim3 = Reply.builder().post(postRepository.findById(2L).orElseThrow()).content("reply3").author("kim").build();
        List<Reply> replies = new ArrayList<>();
        replies.add(replybyKim);
        replies.add(replybyKim2);
        replies.add(replybyKim3);
        replyRepository.saveAll(replies);
        //when
        List<Post> posts = postQueryRepository.fetchJoin();
        //then
        assertThat(posts.size()).isEqualTo(3);
    }



}