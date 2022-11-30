package come.code.challenge.integration;

import com.bestseller.coffeestore.CoffeeStoreApplication;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CoffeeStoreApplication.class)
@AutoConfigureMockMvc
public class OrdersServiceIT {

    final String ORDER_API= "/orders/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Description("discount not included")
    void registerOrdersTest_1() throws Exception {
        //************************
        //          Given
        //************************
        cleanDB();
        Drink drink1 = createDrink("latte", 5.0);

        Topping topping1 = createTopping("honey", 2.0);

        String requestBody = "{\"ordersList\":[{\"drink\":{\"id\":"+drink1.getId()+",\"name\":\"latte\",\"price\":5.0}," +
                "\"toppings\":[{\"id\":"+topping1.getId()+",\"name\":\"honey\",\"price\":2.0}]}]}";

        //************************
        //          WHEN
        //************************
        MvcResult result = mockMvc.perform(post(ORDER_API)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //************************
        //          THEN
        //************************
        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotBlank();
        assertThat(response.contains("latte")).isTrue();
        assertThat(response.contains("honey")).isTrue();
        assertThat(response.contains("discount not included")).isTrue();
        assertThat(response.contains("totalAmount")).isTrue();
        assertThat(response.contains("amountAfterDiscount")).isTrue();
        assertThat(response.contains("7.0")).isTrue();
    }

    @Test
    @Description("discount included, for more than 12$ orders")
    void registerOrdersTest_2() throws Exception {
        //************************
        //          Given
        //************************
        cleanDB();
        Drink drink1 = createDrink("latte", 5.0);
        Drink drink2 = createDrink("moca", 7.0);

        Topping topping1 = createTopping("honey", 2.0);
        Topping topping2 = createTopping("suger", 1.0);

        String requestBody = "{\"ordersList\":[{\"drink\":{\"id\":"+drink1.getId()+",\"name\":\"latte\",\"price\":5.0}," +
                "\"toppings\":[{\"id\":"+topping1.getId()+",\"name\":\"honey\",\"price\":2.0}]}," +
                "{\"drink\":{\"id\":"+drink2.getId()+",\"name\":\"moca\",\"price\":5.0}," +
                "\"toppings\":[{\"id\":"+topping2.getId()+",\"name\":\"suger\",\"price\":1}]}]}";

        //************************
        //          WHEN
        //************************
        MvcResult result = mockMvc.perform(post(ORDER_API)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //************************
        //          THEN
        //************************
        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotBlank();
        assertThat(response.contains("latte")).isTrue();
        assertThat(response.contains("honey")).isTrue();
        assertThat(response.contains("suger")).isTrue();
        assertThat(response.contains("moca")).isTrue();
        assertThat(response.contains("discount included")).isTrue();
        assertThat(response.contains("totalAmount")).isTrue();
        assertThat(response.contains("amountAfterDiscount")).isTrue();
        assertThat(response.contains("payableAmount")).isTrue();
        assertThat(response.contains("11.25")).isTrue();
        assertThat(response.contains("3.75")).isTrue();
        assertThat(response.contains("15.0")).isTrue();
    }

    @Test
    @Description("discount included, for more than three drinks")
    void registerOrdersTest_3() throws Exception {
        //************************
        //          Given
        //************************
        cleanDB();
        Drink drink1 = createDrink("latte", 3.0);
        Drink drink2 = createDrink("moca", 4.0);
        Drink drink3 = createDrink("tea", 3.0);

        Topping topping1 = createTopping("suger", 1.0);

        String requestBody = "{\"ordersList\":[{\"drink\":{\"id\":"+drink1.getId()+",\"name\":\"latte\",\"price\":3.0}," +
                "\"toppings\":[{\"id\":"+topping1.getId()+",\"name\":\"honey\",\"price\":1.0}]}," +
                "{\"drink\":{\"id\":"+drink2.getId()+",\"name\":\"moca\",\"price\":4.0},\"toppings\":[]}," +
                "{\"drink\":{\"id\":"+drink3.getId()+",\"name\":\"tea\",\"price\":3.0},\"toppings\":[]}]}";

        //************************
        //          WHEN
        //************************
        MvcResult result = mockMvc.perform(post(ORDER_API)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //************************
        //          THEN
        //************************
        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotBlank();
        assertThat(response.contains("latte")).isTrue();
        assertThat(response.contains("moca")).isTrue();
        assertThat(response.contains("tea")).isTrue();
        assertThat(response.contains("discount included")).isTrue();
        assertThat(response.contains("totalAmount")).isTrue();
        assertThat(response.contains("amountAfterDiscount")).isTrue();
        assertThat(response.contains("payableAmount")).isTrue();
        assertThat(response.contains("11.0")).isTrue();
        assertThat(response.contains("1.0")).isTrue();
        assertThat(response.contains("10.0")).isTrue();
    }

    @Test
    @Description("discount included, for more than three drinks and more than 12$ orders")
    void registerOrdersTest_4() throws Exception {
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

        //************************
        //          WHEN
        //************************
        MvcResult result = mockMvc.perform(post(ORDER_API)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //************************
        //          THEN
        //************************
        String response = result.getResponse().getContentAsString();
        assertThat(response).isNotBlank();
        assertThat(response.contains("latte")).isTrue();
        assertThat(response.contains("moca")).isTrue();
        assertThat(response.contains("tea")).isTrue();
        assertThat(response.contains("discount included")).isTrue();
        assertThat(response.contains("totalAmount")).isTrue();
        assertThat(response.contains("amountAfterDiscount")).isTrue();
        assertThat(response.contains("payableAmount")).isTrue();
        assertThat(response.contains("14.0")).isTrue();
        assertThat(response.contains("1.0")).isTrue();
        assertThat(response.contains("13.0")).isTrue();
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
