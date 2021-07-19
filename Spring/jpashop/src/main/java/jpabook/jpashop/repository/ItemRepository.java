package jpabook.jpashop.repository;


import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){
            em.persist(item);
        } else{
            em.merge(item); //update와 비슷한 느낌
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

    public void deleteById(Long itemId){
        em.createQuery("delete from Item i where i.id = :itemId").setParameter("itemId",itemId).executeUpdate();
    }

    

}
