package hello.itemservice.repository;

import hello.itemservice.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class ItemRepositoryTest {


    @AfterEach
    void afterEach() {

    }
    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when

    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        //when

        //then

    }

}