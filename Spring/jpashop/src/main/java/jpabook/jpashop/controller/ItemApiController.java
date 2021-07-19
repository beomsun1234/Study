package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class ItemApiController {

    private final ItemService itemService;

    //상품  전체 조회회
    @GetMapping("/items")
    public ResponseEntity<List<Item>> responseFindAllEntity(){
        //DTO로 조회 바꾸기
        List<Item> items = itemService.findItem();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> responseFindEntity(@PathVariable Long itemId){
        //DTO로 조회 바꾸기
        Item item = itemService.findOne(itemId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    //상품등록
    @PostMapping("/items")
    public ResponseEntity<BookForm> responseAddEntity(@RequestBody BookForm bookForm){
        // BOOKFORM 대신 BOOK DTO로 바꾸기
        // 등록 dto 만들기
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setIsbn(bookForm.getIsbn());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        itemService.saveItem(book);
        return new ResponseEntity<>(bookForm,HttpStatus.OK);
    }
    //상품 수정
    @PutMapping("/items/{itemId}")
    public ResponseEntity<BookForm> responseUpdateEntity(@PathVariable Long itemId, @RequestBody BookForm bookForm) throws Exception{
        if (!itemId.equals(bookForm.getId())){
            new IllegalArgumentException("아이디가 맞지 않습니다!");
        }
        //수정 dto 만들어서 보내기
        itemService.updateItem(itemId,bookForm.getName(),bookForm.getPrice(),bookForm.getStockQuantity());

        return new ResponseEntity<>(bookForm,HttpStatus.OK);
    }
    //상품삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Long> responseDeleteEntity(@PathVariable Long itemId) throws Exception{
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(itemId,HttpStatus.OK);
    }

}
