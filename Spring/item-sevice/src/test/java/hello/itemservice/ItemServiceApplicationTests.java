package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
@Rollback(false)
class ItemServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	ItemRepository itemRepository;


	@Test
	public void save(){
		Item item = new Item();
		item.setItemName("testA");
		item.setPrice(10000);
		item.setQuantity(10);

		Item item2 = itemRepository .save(item);


		Assertions.assertThat(item2.getId()).isEqualTo(item.getId());
	}

}
