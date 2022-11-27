package come.code.challenge.integration;

import java.math.BigInteger;

import com.bestseller.coffeestore.ChallengeApplication;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ChallengeApplication.class)
@AutoConfigureMockMvc
class UserServiceTests {

	final java.lang.String ADMIN_API= "/admin/menu";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Test
    void createAdminItemDrinkTest() throws Exception {
        //************************
        //          Given
        //************************
//        userRepository.deleteAll();

		String requestBody = "{\"name\":\"latte\",\"price\":50,\"itemType\":\"DRINK\"}";

        //************************
        //          WHEN
        //************************
		//************************
		//          THEN
		//************************
        mockMvc.perform(post(ADMIN_API)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************

		String requestBody = "{\"name\":\"latte\",\"price\":50,\"itemType\":\"TOPPING\"}";

		//************************
		//          WHEN
		//************************
		//************************
		//          THEN
		//************************
		mockMvc.perform(post(ADMIN_API)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
    }

	@Test
	void updateDrinkItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************

		String requestBody = "{\"id\":"+createDrink()+",\"name\":\"latte\",\"price\":5,\"itemType\":\"DRINK\"}";

		//************************
		//          WHEN
		//************************
		//************************
		//          THEN
		//************************
		mockMvc.perform(put(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void updateToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************

		String requestBody = "{\"id\":"+createTopping()+",\"name\":\"latte\",\"price\":5,\"itemType\":\"TOPPING\"}";

		//************************
		//          WHEN
		//************************
		//************************
		//          THEN
		//************************
		mockMvc.perform(put(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void deleteToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		long id = createTopping();
		String requestBody = "{\"id\":"+id+",\"name\":\"Cinnamon\",\"price\":5,\"itemType\":\"TOPPING\"}";

		//************************
		//          WHEN
		//************************
		mockMvc.perform(delete(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		assertThat(toppingRepository.findById(id)).isEmpty();
	}

	@Test
	void deleteDrinkItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		long id = createDrink();
		String requestBody = "{\"id\":"+id+",\"name\":\"latte\",\"price\":5,\"itemType\":\"DRINK\"}";

		//************************
		//          WHEN
		//************************
		mockMvc.perform(delete(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		assertThat(drinkRepository.findById(id)).isEmpty();
	}

	private long createDrink() {
		drinkRepository.deleteAll();

		Drink drink = new Drink();
		drink.setPrice(new BigInteger("5"));
		drink.setName("Mocka");
		drinkRepository.save(drink);

		return drink.getId();
	}

	private long createTopping() {
		toppingRepository.deleteAll();

		Topping topping = new Topping();
		topping.setPrice(new BigInteger("2"));
		topping.setName("Honey");
		toppingRepository.save(topping);

		return topping.getId();
	}
}
