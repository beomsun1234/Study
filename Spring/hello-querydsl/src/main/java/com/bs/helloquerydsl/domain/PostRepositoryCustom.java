package com.bs.helloquerydsl.domain;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findByTitle(String title);

    List<Post>findByTitleContainingOrContentContaining(String title, String content);

    List<Post> findDynamicQuery(String title, String content, String author);
    List<Post>findByTitleContainingOrContentContainingUsingBE(String title, String content);
}
