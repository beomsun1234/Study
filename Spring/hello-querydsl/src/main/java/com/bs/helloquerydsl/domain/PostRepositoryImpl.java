package com.bs.helloquerydsl.domain;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bs.helloquerydsl.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findByTitle(String title) {
        return queryFactory.selectFrom(post)
                .where(post.title.eq(title))
                .fetch();
    }

    @Override
    public List<Post> findByAuthor(String author) {
        return queryFactory.selectFrom(post)
                .where(post.author.eq(author))
                .fetch();
    }

    @Override
    public List<Post> findByTitleContainingOrContentContaining(String title, String content) {
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
    public List<Post> findByContent(String Content) {
        return queryFactory.selectFrom(post)
                .where(post.content.eq(Content))
                .fetch();
    }

    @Override
    public List<Post> findDynamicQuery(String title, String content, String author) {
        return queryFactory.selectFrom(post)
                .where( eqAuthor(author),
                        (containsContent(title).
                                or(containsTitle(content)))
                ).fetch();
    }

    @Override
    public List<Post> findByTitleContainingOrContentContainingUsingBE(String title, String content) {
        return queryFactory.selectFrom(post)
                .where(containsContent(title)
                        .or(containsTitle(content))
                ).fetch();
    }

    private BooleanExpression eqAuthor(String author){
        if (StringUtils.isEmpty(author)){
            return null;
        }
        return post.author.eq(author);
    }
    private BooleanExpression containsTitle(String title){
        if (StringUtils.isEmpty(title)){
            return null;
        }
        return post.title.contains(title);
    }
    private BooleanExpression containsContent(String content){
        if (StringUtils.isEmpty(content)){
            return null;
        }
        return post.content.contains(content);
    }


}
