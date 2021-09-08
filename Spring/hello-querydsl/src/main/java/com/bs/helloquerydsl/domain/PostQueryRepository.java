package com.bs.helloquerydsl.domain;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bs.helloquerydsl.domain.QPost.post;
import static com.bs.helloquerydsl.domain.QReply.reply;
@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     *
     *
     *
     * 간단조회
     */
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
    //------------------------------------


    /**
     *
     * @param title
     * @param content
     * 동적쿼리
     */
    public List<Post> findByContaingTitleOrContatingContent(String title, String content){
        return queryFactory.selectFrom(post)
                .where(contatingTitle(title)
                        .or(contatingContent(content)))
                .fetch();
    }


    /**
     * 기본 페이징
     */
    public Page<Post> findPagePost(Pageable pageable){
        QueryResults<Post> postQueryResults = queryFactory
                .selectFrom(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        long total = postQueryResults.getTotal(); // 전체 포스트
        List<Post> posts = postQueryResults.getResults();

        return new PageImpl(posts, pageable,total);
    }

    /**
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     * 페이징 동적쿼리 (제목, 내용으로 찾기)
     */
    public Page<Post> dynamicPagePostv2(String title, String content, Pageable pageable){
        QueryResults<Post> postQueryResults = queryFactory
                .selectFrom(post)
                .where(contatingTitle(title)
                        .or(contatingContent(content)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> posts = postQueryResults.getResults();
        long total = postQueryResults.getTotal();
        return new PageImpl(posts, pageable , total);
    }

    /**
     *
     * @param author
     * @param title
     * @param content
     * @param pageable
     * @return
     * 페이징 동적쿼리(작성자, 제목, 내용으로찾기)
     */
    public Page<Post> dynamicPagePostV3(String author, String title, String content, Pageable pageable){
        QueryResults<Post> postQueryResults = queryFactory
                .selectFrom(post)
                .where( eqAuthor(author)
                        .or(contatingTitle(title)
                                .or(contatingContent(content)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> posts = postQueryResults.getResults();
        long total = postQueryResults.getTotal();
        return new PageImpl(posts, pageable , total);
    }

    private BooleanExpression eqAuthor(String author){
        if(author == null){
            return null;
        }
        return post.author.eq(author);
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
