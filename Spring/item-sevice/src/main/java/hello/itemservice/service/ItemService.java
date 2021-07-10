package hello.itemservice.service;

import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ItemService {


    @Autowired
    private ItemRepository itemRepository;

    public void update(long id, Item updateParam){
        Optional<Item> item=itemRepository.findById(id);
        item.get().setItemName(updateParam.getItemName());
        item.get().setPrice(updateParam.getPrice());
        item.get().setQuantity(updateParam.getQuantity());
        itemRepository.save(item.get());
    }


    //em.persist(item);
    //return em.find(Item.class,id);

    //private static final Map<Long, Item> store = new ConcurrentHashMap<>(); //static

    //private static long sequence = 0L; //static 사용

//    public Item save(Item item) {
//        item.setId(++sequence);
//        store.put(item.getId(), item);
//        return item;
//    }
//    public Item findById(Long id) {
//        return store.get(id);
//    }
//
//    public List<Item> findAll() {
//        return new ArrayList<>(store.values());
//    }
//
//    public void clearStore() {
//        store.clear();
//    }
}
