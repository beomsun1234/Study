package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@RequiredArgsConstructor//final 생성자 생성
public class MemberRepository {

    //@PersistenceContext
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findById(long id){
        return em.find(Member.class, id);// 첫번쨰는 타입, 두번째는 pk
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).
                getResultList();

    }
    public List<Member> finByName(String name){
        return em.createQuery("select m from Member m where m.name=:name", Member.class).setParameter("name",name).getResultList();
    }
    public Member findByEmail(String email){
        return (Member) em.createQuery("select m from Member m where m.email=:email", Member.class).setParameter("email",email).getResultList();
    }


    public void delete(Long memberId){
        em.createQuery("delete from Member m where m.id=:memberId").setParameter("memberId",memberId).executeUpdate();
    }
}
