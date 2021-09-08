package com.bs.helloquerydsl.controller;

import com.bs.helloquerydsl.domain.Post;
import com.bs.helloquerydsl.domain.PostQueryRepository;
import com.bs.helloquerydsl.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PostController {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    @Transactional
    public void setPost(){
        List<Post> posts = new ArrayList<>();
        for(int i =1; i<=10; i++){
            String name = "park";
            String title = "spring";
            if (i>=6){
                name = "kim";
                title = "test";
            }
            Post post = Post.builder()
                    .title(title + i)
                    .content("test"+i)
                    .author(name)
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);
    }

    @GetMapping("testset")
    public String setTest(){
        setPost();
        return "저장완료";
    }
    @GetMapping("v2/post")
    public Page<Post> findDynamicPostPageV2(@RequestParam(required = false, defaultValue = "") String searchText, Pageable pageable){
        Page<Post> pagePost = postQueryRepository.dynamicPagePostv2(searchText,searchText,pageable);
        List<Post> posts = pagePost.toList();
        log.info("posts={}"+posts);
        return pagePost;
    }
    @GetMapping("v3/post")
    public Page<Post> findDynamicPostPageV3(@RequestParam(required = false, defaultValue = "") String searchText, Pageable pageable){
        Page<Post> pagePost = postQueryRepository.dynamicPagePostV3(searchText,searchText,searchText, pageable);
        List<Post> posts = pagePost.toList();
        log.info("posts={}"+posts);
        return pagePost;
    }
    @GetMapping("v1/post")
    public Page<Post> findPost(Pageable pageable){
        return postQueryRepository.findPagePost(pageable);
    }
}
