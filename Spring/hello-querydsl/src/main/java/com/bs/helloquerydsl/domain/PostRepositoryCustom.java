package com.bs.helloquerydsl.domain;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findByTitle(String title);

    List<Post> findByAuthor(String author);
    List<Post>findByTitleContainingOrContentContaining(String title, String content);
    List<Post>findByContent(String Content);

    List<Post> findDynamicQuery(String title, String content, String author);
    List<Post>findByTitleContainingOrContentContainingUsingBE(String title, String content);
}
