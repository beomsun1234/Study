package com.bs.helloquerydsl.domain;

//@SpringBootTest
//public class PostRepositorySupportTest {
//
//    @Autowired
//    private PostRepositorySupport postRepositorySupport;
//
//    @Autowired
//    private PostRepository postRepository;
//
//
//    @DisplayName("postRepositorySupport 테스트")
//    @Test
//    public void Title이T인_2건의_Post찾기(){
//        //given
//        String title = "testT";
//        String content = "testC";
//        String author = "park";
//        Post post = Post.builder()
//                .title(title)
//                .content(content)
//                .author(author).build();
//
//        postRepository.save(post);
//
//        String title1 = "testT";
//        String content2 = "testC";
//        String author2 = "park";
//        Post post2 = Post.builder()
//                .title(title1)
//                .content(content2)
//                .author(author2).build();
//
//        postRepository.save(post2);
//
//        //when
//        List<Post> posts = postRepositorySupport.findByTitle("testT");
//
//        //then
//        Assertions.assertThat(posts.size()).isEqualTo(2);
//
//    }
//}