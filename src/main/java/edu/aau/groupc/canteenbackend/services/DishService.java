package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dao.DishRepository;
import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.TreeUI;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DishService implements IDishService {
    private DishRepository dishRepo;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dishRepo = dishRepo;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepo.findAll();
    }

    @Override
    public Dish create(Dish newDish) {
        return dishRepo.save(newDish);
    }


    public Dish update(Dish newDish) {
        List<Dish> allDish = findAll();
        int id = 0;
        for (Dish aDish : allDish) {
            if (aDish.getName().equals(newDish.getName())) {
                aDish.setPrice(newDish.getPrice());
                aDish.setType(newDish.getType());
                aDish.setDishDay(newDish.getDishDay());
                dishRepo.save(aDish);
                id = aDish.getId();
                break;
            }
        }
        return dishRepo.getById(id);
    }

    public ResponseEntity delete(Dish newDish) {
        List<Dish> allDish = findAll();
        int flag = 0;
        for (Dish aDish : allDish) {
            if (newDish.getName().equals(aDish.getName())) {
                dishRepo.delete(aDish);
                flag = 1;
            }
        }
        if (flag == 1)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok("No Such Dish");
        }

    public ResponseEntity deleteAllDishes(String var) {
        if (var.equals("all"))
        {
            dishRepo.deleteAll();
            return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.ok("invalid input parameter");
    }
}
