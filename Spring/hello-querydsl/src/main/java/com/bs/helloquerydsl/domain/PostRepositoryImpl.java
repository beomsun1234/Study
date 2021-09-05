package com.bs.helloquerydsl.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findByTitle(String title) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(post.title.eq(title))
                .fetch();
    }
}
