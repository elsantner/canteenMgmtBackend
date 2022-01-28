package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateOrderDTOTEST implements ValidationTest {

    @Test
    public void testValid_ThenOk() {
        assertValid(createOrderDTO(1, 1, generateDishList(2)));
    }

    @Test
    public void testNullCanteenId_ThenInvalid() {
        assertInvalid(createOrderDTO(1, null, generateDishList(2)));
    }

    @Test
    public void testNullDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, null));
    }

    @Test
    public void testEmptyDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, new LinkedList<>()));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    public void testInvalidDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, generateInvalidDishList()));
    }


    private CreateOrderDTO createOrderDTO(Integer userId, Integer canteenId, List<DishForOrderCreationDTO> dishList) {
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setDishes(dishList);
        dto.setUserId(userId);
        dto.setCanteenId(canteenId);
        return dto;
    }

    // dont really care about the dishDTOs except for thembeing valid
    private List<DishForOrderCreationDTO> generateDishList(int length) {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            DishForOrderCreationDTO dishDto = new DishForOrderCreationDTO();
            dishDto.setId(i);
            dishDto.setCount(i + 1);
            dishList.add(dishDto);
        }
        return dishList;
    }

    // dont really care about the dishDTOs except for them being invalid
    private List<DishForOrderCreationDTO> generateInvalidDishList() {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        DishForOrderCreationDTO dishDto = new DishForOrderCreationDTO();
        dishDto.setId(null);
        dishDto.setCount(0);
        dishList.add(dishDto);
        return dishList;
    }
}
