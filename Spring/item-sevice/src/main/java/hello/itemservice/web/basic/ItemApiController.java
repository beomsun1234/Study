package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api")
@RestController
public class ItemApiController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService; //jpa등록 후


    @GetMapping("/items")
    List<Item> items() {
        return itemRepository.findAll();
    }

    @GetMapping("items/{itemId}")
    Item findByItemId(@PathVariable("itemId") long itemID){
       Item finditem= itemRepository.findById(itemID).orElseThrow();
        log.info("item={}",finditem);
        return itemRepository.findById(itemID).orElseThrow();
    }

    @PostMapping("/add")
    Item addItem(@RequestBody Item item){
        Item saveditem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", saveditem.getId());
//        redirectAttributes.addAttribute("status", true);
        log.info("saveitem={}",saveditem);
        return saveditem;
    }
    //---------------------------------------------------------------

    @PutMapping("/items/{itemId}")
    Item editItem(@PathVariable("itemId") long itemId,
                  @RequestBody Item item){
        Item udateItem = itemRepository.findById(itemId).orElseThrow();
        udateItem.setItemName(item.getItemName());
        udateItem.setPrice(item.getPrice());
        udateItem.setQuantity(item.getQuantity());
        log.info("udateItem={}",udateItem);
        return itemRepository.save(udateItem);
    }
    @DeleteMapping("/items/{itemId}")
    void deleteItem( @PathVariable("itemId") long itemId){
        itemRepository.deleteById(itemId);
    }


}
