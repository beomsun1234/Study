package com.bs.helloquerydsl.domain;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findByTitle(String title);
}
