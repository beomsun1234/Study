package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  //----------------------------------------------add----------------------------
    //@PostMapping("/add") //등록할때는 post
    public String addItemV4(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        //model.addAttribute(item); -> 자동추가
        log.info("data={}",item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item saveditem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveditem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }
    //---------------------------------------------------------------

    @GetMapping("/{itemId}/edit")
    public String openEditForm(@PathVariable("itemId") long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/edit";
    }

    @PostMapping("/{itemId}/edit") //등록할때는 post
    public String editItemV1(@PathVariable("itemId") long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //url은 항상 인코딩해서 넘거여함
    }

    //@PostMapping("/{itemId}/edit") //등록할때는 post
    public String editItemV2(RedirectAttributes redirectAttributes, @ModelAttribute Item item){
        redirectAttributes.addAttribute(item.getId());
       itemRepository.update(item.getId(), item);

        return "redirect:/basic/items/{itemId}"; //url은 항상 인코딩해서 넘거여함
    }






}
