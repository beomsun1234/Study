package hello.itemservice.repository;


import hello.itemservice.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {




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

