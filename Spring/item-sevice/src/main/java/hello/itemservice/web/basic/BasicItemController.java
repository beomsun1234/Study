package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //파이널이 붙은것에 생성자 자동생성
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemID, Model model){

        Item item = itemRepository.findById(itemID);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add") //폼을 열때는 get
    public String openAddForm(){
        return "basic/add";
    }

    @PostMapping("/add") //등록할때는 post
    public String saveItem1(@ModelAttribute("item") Item item,Model model){
        itemRepository.save(item);
        //model.addAttribute(item); -> 자동추가
        log.info("data={}",item);
        return "basic/item";
    }

//    @PostMapping("/add") //등록할때는 post
//    public String saveItem2(Item item,Model model){
//        itemRepository.save(item);
//        log.info("data={}",item);
//        return "basic/item";
//    }






}
