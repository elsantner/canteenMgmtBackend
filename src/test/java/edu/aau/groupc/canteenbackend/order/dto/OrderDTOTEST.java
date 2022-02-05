package edu.aau.groupc.canteenbackend.order.dto;

import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.util.ValidationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDTOTEST implements ValidationTest {

    final String name = "name";
    final Float price = 1f;
    final String type = "MAIN";
    final Integer id = 1;
    final Integer count = 1;
    final Date futureDate = new Date(new Date().getTime() + 3600 * 1000 * 3);

    @Test
    public void testValid_ThenOk() {
        assertValid(createOrderDTO(1, 1, generateDishList(2), false, futureDate));
    }

    @Test
    public void testNullId_ThenInvalid() {
        assertInvalid(createOrderDTO(null, 1, generateDishList(2), false, futureDate));
    }

    @Test
    public void testNullCanteenId_ThenInvalid() {
        assertInvalid(createOrderDTO(1, null, generateDishList(2), true, futureDate));
    }

    @Test
    public void testNullDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, null, false, futureDate));
    }

    @Test
    public void testEmptyDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, new LinkedList<>(), false, futureDate));
    }

    // this test is only to check if the "parent" dto catches invalid states in the dtos inside the list
    @Test
    public void testInvalidDishList_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, generateInvalidDishList(), false, futureDate));
    }

    @Test
    public void testDateNow_ThenInvalid() {
        assertInvalid(createOrderDTO(1, 1, new LinkedList<>(), false, new Date()));
    }


    private OrderDTO createOrderDTO(Integer id, Integer canteenId, List<OrderDishDTO> dishList, boolean reserveTable, Date pickupDate) {
        OrderDTO dto = new OrderDTO();
        dto.setId(id);
        dto.setDishes(dishList);
        dto.setCanteenId(canteenId);
        dto.setReserveTable(reserveTable);
        dto.setPickupDate(pickupDate);
        return dto;
    }

    // dont really care about the dishDTOs except for them being valid
    private List<OrderDishDTO> generateDishList(int length) {
        List<OrderDishDTO> dishList = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            dishList.add(new OrderDishDTO(name, price, type, id, count));
        }
        return dishList;
    }

    // dont really care about the dishDTOs except for them being invalid
    private List<OrderDishDTO> generateInvalidDishList() {
        List<OrderDishDTO> dishList = new LinkedList<>();
        dishList.add(new OrderDishDTO());
        return dishList;
    }
}