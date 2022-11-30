package come.code.challenge.integration;

import com.bestseller.coffeestore.ChallengeApplication;
import com.bestseller.coffeestore.admin.model.Drink;
import com.bestseller.coffeestore.admin.model.Topping;
import com.bestseller.coffeestore.admin.repository.DrinkRepository;
import com.bestseller.coffeestore.admin.repository.OrderRepository;
import com.bestseller.coffeestore.admin.repository.ToppingRepository;
import jdk.jfr.Description;
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
class AdminServiceIT {

	final String ADMIN_API= "/admin/menu";
	final String ORDER_API= "/orders/register";
	final String ORDER_REPORT = "/admin/menu/order-report";

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingRepository toppingRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	@Description("add a new drink item by admin successfully")
    void createAdminItemDrinkTest() throws Exception {
        //************************
        //          Given
        //************************
		cleanDB();
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
	@Description("add a new topping item by admin successfully")
	void addToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		cleanDB();
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
	@Description("update an exist drink item by admin successfully")
	void updateDrinkItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		cleanDB();
		Drink drink = createDrink("latte", 5.0);
		String requestBody = "{\"id\":"+drink.getId()+",\"name\":\"big latte\",\"price\":3,\"itemType\":\"DRINK\"}";

		//************************
		//          WHEN
		//************************
		mockMvc.perform(put(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		Drink updateDrink = drinkRepository.findById(drink.getId()).get();
		assertThat(updateDrink.getPrice()).isEqualTo(3.0);
		assertThat(updateDrink.getName()).isEqualTo("big latte");
	}

	@Test
	@Description("update an topping item by admin successfully")
	void updateToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		toppingRepository.deleteAll();
		Topping topping = createTopping("suger", 1.0);
		String requestBody = "{\"id\":"+topping.getId()+",\"name\":\"brown suger\",\"price\":2.0,\"itemType\":\"TOPPING\"}";

		//************************
		//          WHEN
		//************************
		mockMvc.perform(put(ADMIN_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		//************************
		//          THEN
		//************************
		Topping updateTopping = toppingRepository.findById(topping.getId()).get();
		assertThat(updateTopping.getPrice()).isEqualTo(2.0);
		assertThat(updateTopping.getName()).isEqualTo("brown suger");
	}

	@Test
	@Description("delete an exist topping item by admin successfully")
	void deleteToppingItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		toppingRepository.deleteAll();
		Topping topping = createTopping("cinnamon", 2.0);
		String requestBody = "{\"id\":"+topping.getId()+",\"name\":\"cinnamon\",\"price\":2.0,\"itemType\":\"TOPPING\"}";

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
	@Description("delete an exist drink item by admin successfully")
	void deleteDrinkItemToMenuTest() throws Exception {
		//************************
		//          Given
		//************************
		drinkRepository.deleteAll();
		Drink drink = createDrink("latte", 6.0);
		String requestBody = "{\"id\":"+drink.getId()+",\"name\":\"latte\",\"price\":6.0,\"itemType\":\"DRINK\"}";

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
		cleanDB();
		Drink drink1 = createDrink("big latte", 7.0);
		Drink drink2 = createDrink("moca", 4.0);
		Drink drink3 = createDrink("tea", 2.0);

		Topping topping1 = createTopping("suger", 1.0);

		String requestBody = "{\"ordersList\":[{\"drink\":{\"id\":"+drink1.getId()+",\"name\":\"latte\",\"price\":7.0}," +
				"\"toppings\":[{\"id\":"+topping1.getId()+",\"name\":\"honey\",\"price\":1.0}]}," +
				"{\"drink\":{\"id\":"+drink2.getId()+",\"name\":\"moca\",\"price\":4.0},\"toppings\":[]}," +
				"{\"drink\":{\"id\":"+drink3.getId()+",\"name\":\"tea\",\"price\":3.0},\"toppings\":[]}]}";

		mockMvc.perform(post(ORDER_API)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          WHEN
		//************************
		MvcResult result = mockMvc.perform(post(ORDER_REPORT)
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

	private void cleanDB() {
		orderRepository.deleteAll();
		drinkRepository.deleteAll();
		toppingRepository.deleteAll();
	}

	private Drink createDrink(String name, Double price) {
		Drink drink = new Drink();
		drink.setPrice(price);
		drink.setName(name);
		return drinkRepository.save(drink);
	}

	private Topping createTopping(String name, Double price) {
		Topping topping = new Topping();
		topping.setPrice(price);
		topping.setName(name);
		return toppingRepository.save(topping);
	}
}
