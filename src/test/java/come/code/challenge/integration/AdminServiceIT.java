package come.code.challenge.integration;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.bestseller.coffeestore.ChallengeApplication;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Order;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ChallengeApplication.class)
@AutoConfigureMockMvc
class AdminServiceIT {

	final java.lang.String ADMIN_API= "/admin/menu";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingRepository toppingRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Test
    void createAdminItemDrinkTest() throws Exception {
        //************************
        //          Given
        //************************
		drinkRepository.deleteAll();
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
		toppingRepository.deleteAll();
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
		drinkRepository.deleteAll();
		Drink drink = drinkRepository.save(createDrink());
		String requestBody = "{\"id\":"+drink.getId()+",\"name\":\"latte\",\"price\":5,\"itemType\":\"DRINK\"}";

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
		toppingRepository.deleteAll();
		Topping topping = toppingRepository.save(createTopping("suger"));
		String requestBody = "{\"id\":"+topping.getId()+",\"name\":\"suger\",\"price\":5,\"itemType\":\"TOPPING\"}";

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
		toppingRepository.deleteAll();
		Topping topping = toppingRepository.save(createTopping("honey"));
		String requestBody = "{\"id\":"+topping.getId()+",\"name\":\"Cinnamon\",\"price\":5,\"itemType\":\"TOPPING\"}";

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
		assertThat(toppingRepository.findById(topping.getId())).isEmpty();
	}

	@Test
	void deleteDrinkItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		drinkRepository.deleteAll();
		Drink drink = drinkRepository.save(createDrink());
		String requestBody = "{\"id\":"+drink.getId()+",\"name\":\"latte\",\"price\":5,\"itemType\":\"DRINK\"}";

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
		assertThat(drinkRepository.findById(drink.getId())).isEmpty();
	}

	@Test
	void getOrderReportsTest() throws Exception {
		//************************
		//          Given
		//************************
		orderRepository.deleteAll();
		drinkRepository.deleteAll();
		toppingRepository.deleteAll();

		createSomeOrders();
		//************************
		//          WHEN
		//************************
		MvcResult result = mockMvc.perform(post(ADMIN_API+"/order-report")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		String response = result.getResponse().getContentAsString();
		assertThat(response).isNotBlank();
	}

	private Drink createDrink() {
		Drink drink = new Drink();
		drink.setPrice(new BigInteger("5"));
		drink.setName("Mocka");
		return drink;
	}

	private Topping createTopping(String name) {
		Topping topping = new Topping();
		topping.setPrice(new BigInteger("2"));
		topping.setName(name);

		return topping;
	}

	private void createSomeOrders() {
		Set<Topping> toppings1 = new HashSet<>();
		toppings1.add(createTopping("honey"));
		toppings1.add(createTopping("syrup"));
		toppings1.add(createTopping("syrup"));

		Drink drink1 = new Drink();
		drink1.setPrice(new BigInteger("5"));
		drink1.setName("Moca");
		drinkRepository.save(drink1);
//		toppingRepository.saveAll(toppings1);

		Order order1 = new Order();
		order1.setAmount(new BigInteger("11"));
		order1.setToppings(toppings1);
		order1.setDiscount(BigInteger.ZERO);
		order1.setDrink(drink1);
		orderRepository.save(order1);
//-------------------------------------------------//
//		Order order2 = new Order();
//		order2.setAmount(new BigInteger("11"));
//		order2.setToppings(toppings1);
//		order2.setDiscount(BigInteger.ZERO);
//		order2.setDrink(drink1);
//		orderRepository.save(order2);
	}
}
