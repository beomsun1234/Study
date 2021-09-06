package com.bs.helloquerydsl.domain;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bs.helloquerydsl.domain.QPost.post;
import static com.bs.helloquerydsl.domain.QReply.reply;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;


    public List<Post> findByAuthor(String author){
        return queryFactory.selectFrom(post)
                .where(post.author.eq(author))
                .fetch();
    }
    public List<Post> findByContaingTitle(String title){
        return queryFactory.selectFrom(post)
                .where(contatingTitle(title))
                .fetch();
    }
    public List<Post> findByCntatingContent(String content){
        return queryFactory.selectFrom(post)
                .where(contatingContent(content))
                .fetch();
    }


    public List<Post> findByContaingTitleOrContatingContent(String title, String content){
        return queryFactory.selectFrom(post)
                .where(contatingTitle(title)
                        .or(contatingContent(content)))
                .fetch();
    }
    private BooleanExpression contatingTitle(String title){
        if(title==null){
            return null;
        }
        return post.title.contains(title);
    }
    private BooleanExpression contatingContent(String content){
        if(content == null){
            return null;
        }
        return post.content.contains(content);
    }

    /**
     * 댓글조회
     * @param postId
     * @return
     */
    public List<Reply> findAllReplyByPostId(Long postId){
        return queryFactory.selectFrom(reply)
                .join(reply.post, post)
                .where(reply.post.id.eq(postId))
                .fetch();
    }
    /**
     * 모든 포스트 조회 댓글과 함께
     */
    public List<Tuple> leftJoin(){
        return queryFactory.select(post,reply)
                .from(post)
                .leftJoin(post.replies, reply)
                .fetch();
    }

    public List<Tuple> rightJoin(){
        return queryFactory.select(post,reply)
                .from(post)
                .rightJoin(post.replies, reply)
                .fetch();
    }

    public List<Post> fetchJoin(){
        return queryFactory.selectFrom(post)
                .join(post.replies, reply).fetchJoin()
                .fetch();
    }

}
