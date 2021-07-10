package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
@Slf4j
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemID, Model model){
        itemRepository.findById(itemID);
        Optional<Item> item = itemRepository.findById(itemID);
        log.info("item={}",item);
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
        Optional<Item> item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/edit";
    }

    //@PostMapping("/{itemId}/edit") //등록할때는 post
    public String editItemV1(@PathVariable("itemId") long itemId, @ModelAttribute Item item){
        itemService.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //url은 항상 인코딩해서 넘거여함
    }

    @PostMapping("/{itemId}/edit") //등록할때는 post
    public String editItemV2(RedirectAttributes redirectAttributes, @ModelAttribute Item item){
        redirectAttributes.addAttribute(item.getId());
        itemService.update(item.getId(), item);
        return "redirect:/basic/items/{itemId}"; //url은 항상 인코딩해서 넘거여함
    }






}
