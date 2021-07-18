package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItem(){
        return itemRepository.findAll(); //read only
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
    @Transactional //-> 바뀐값을 업데이트를함 //변경감지
    public void updateItem(Long itemId, String name, int price, int stockQauantity){
        Item findItem = itemRepository.findOne(itemId); //영속성 컨텐츠를 가져옴
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQauantity);
    }

//    public void updateItem(Long itemId, UpdateItemDto itemDto){
//        Item findItem = itemRepository.findOne(itemId); //영속성 컨텐츠를 가져옴
//        findItem.setName(name);
//        findItem.setPrice(price);
//        findItem.setStockQuantity(stockQauantity);
//    }

}
