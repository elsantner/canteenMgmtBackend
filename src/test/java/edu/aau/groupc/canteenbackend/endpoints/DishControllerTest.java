package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.services.DishService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("H2Database")
public class DishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService dishService;

    private final HttpHeaders headers = new HttpHeaders();


    protected ResponseEntity<String> makeGetRequest(String uri) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }

    protected ResponseEntity<String> makePostRequest(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST,
                new HttpEntity<>(newDish, headers),
                String.class);
    }
    protected ResponseEntity<String> makePutRequest(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.PUT,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    protected ResponseEntity<String> makeDeleteRequest(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.DELETE,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    @Test
    public void testGetDishes() throws JSONException {
        dishService.deleteAllDishes("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));

        ResponseEntity<String> response = makeGetRequest("/dish");
        String expected = "[{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}," +
                           "{\"name\":\"Cheese Burger\",\"price\":4.0,\"type\":\"MAIN\",\"dishDay\":\"MONDAY\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCreateDishes() throws JSONException {
        ResponseEntity<String> response = makePostRequest("/dish", new Dish("Salad1", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        String expected = "{\"name\":\"Salad1\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
//
//
    @Test
    public void testUpdateDish()  {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePutRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
        String expected = "dish updated";
        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testUpdateDishNON()  {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePutRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
        String expected = "No Such Dish found";
        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testDeleteDish() {

        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        dishService.delete(new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));

        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
        String expected = "dish deleted";

        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testDeleteDishNonExisting() {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
        String expected = "No Such Dish";
        assertThat(expected.equals(response.getBody())).isTrue();
    }



}
