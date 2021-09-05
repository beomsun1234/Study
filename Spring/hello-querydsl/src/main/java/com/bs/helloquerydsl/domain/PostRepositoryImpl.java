package com.bs.helloquerydsl.domain;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

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

    @Override
    public List<Post> findByTitleContainingOrContentContaining(String title, String content) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        if (!StringUtils.isEmpty(title)){
            builder.and(post.title.contains(title));
        }
        if (!StringUtils.isEmpty(content)){
            builder.or(post.content.contains(content));
        }
        return queryFactory.selectFrom(post)
                .where(builder)
                .fetch();
    }

    @Override
    public List<Post> findDynamicQuery(String title, String content, String author) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where( eqAuthor(author),
                        (containsContent(title).
                                or(containsTitle(content)))
                ).fetch();
    }

    @Override
    public List<Post> findByTitleContainingOrContentContainingUsingBE(String title, String content) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post)
                .where(containsContent(title)
                        .or(containsTitle(content))
                ).fetch();
    }

    private BooleanExpression eqAuthor(String author){
        QPost post = QPost.post;
        if (StringUtils.isEmpty(author)){
            return null;
        }
        return post.author.eq(author);
    }
    private BooleanExpression containsTitle(String title){
        QPost post = QPost.post;
        if (StringUtils.isEmpty(title)){
            return null;
        }
        return post.title.contains(title);
    }
    private BooleanExpression containsContent(String content){
        QPost post = QPost.post;
        if (StringUtils.isEmpty(content)){
            return null;
        }
        return post.content.contains(content);
    }


}
