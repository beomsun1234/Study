package com.bs.hellofileupload.domain.board.repository;
import static com.bs.hellofileupload.domain.board.QBoard.board;

import com.bs.hellofileupload.domain.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Board> findAll(){
        return queryFactory
                .selectFrom(board)
                .leftJoin(board.file).fetchJoin()
                .orderBy(board.id.asc())
                .fetch();
    }
    public List<Board> findAllNplusOne(){
        return queryFactory
                .selectFrom(board)
                .fetch();
    }

    public Board findById(Long id){
        return queryFactory
                .selectFrom(board)
                .leftJoin(board.file).fetchJoin()
                .where(board.id.eq(id))
                .fetchOne();
    }

}
