package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class DishController {
    private final IDishService dishService;

    @Autowired
    public DishController(IDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "/dish")
    public List<Dish> getDishes()
    {
        return dishService.findAll();
    }

    @PostMapping(value = "/dish")
    public Dish createDish(@Valid @RequestBody DishDTO newDish)
    {
        return dishService.create(newDish.toEntity());
    }

    @PutMapping(value = "/dish")
    public Dish update(@Valid @RequestBody DishDTO updateDish)
    {
        return dishService.update(updateDish.toEntity());
    }

    @DeleteMapping (value = "/dish")
    public ResponseEntity delete(@Valid @RequestBody DishDTO deleteDish)
    {
        return dishService.delete(deleteDish.toEntity());
    }
}
