package com.example.dacn.modules.dish.service;

import com.example.dacn.entity.Account;
import com.example.dacn.entity.Category;
import com.example.dacn.entity.Dish;
import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.category.repository.CategoryRepository;
import com.example.dacn.modules.category.service.CategoryService;
import com.example.dacn.modules.dish.dto.DishDTO;
import com.example.dacn.modules.dish.repository.DishRepository;
import com.example.dacn.utils.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;

    public ResponseModel addDish(DishDTO dishDTO)
    {
        dishDTO.setDishID("");
        try
        {
            ResponseModel categoryResponse=categoryService.findById(dishDTO.getCategoryID());
            if(categoryResponse.getData()==null)
            {
                return new ResponseModel("CategoryNotFound","Không tìm thấy danh mục");
            }

            Dish newDish = dishDTO.convertToEntity();
            newDish.setCategory((Category) categoryResponse.getData());

            dishRepository.save(newDish);
            String dishId=dishRepository.findLatestAddressId();
            String imageUrl=imageService.uploadImage(dishDTO.getImage(),"dishImage/",dishId);
            newDish.setDishID(dishId);
            newDish.setImageUrl(imageUrl);
            Dish savedDish = dishRepository.save(newDish);
            return new ResponseModel("Success",savedDish);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail","Thêm món thất bại");
        }
    }
    public ResponseModel updateDish(String dishID,DishDTO dishDTO) {
        try {
            ResponseModel categoryResponse = categoryService.findById(dishDTO.getCategoryID());
            if (categoryResponse.getData() == null)
            {
                return new ResponseModel("CategoryNotFound", "Không tìm thấy danh mục");
            }

            Dish optionalDish = dishRepository.findById(dishID).orElse(null);
            if (optionalDish!=null) {
                dishDTO.setDishID(optionalDish.getDishID());
                Dish updatedDish=dishDTO.convertToEntity();
                updatedDish.setCategory((Category) categoryResponse.getData());
                if(dishDTO.getImage()!=null)
                {
                    String imageUrl=imageService.uploadImage(dishDTO.getImage(),"dishImage/",optionalDish.getDishID());
                    updatedDish.setImageUrl(imageUrl);
                }
                else
                {
                    updatedDish.setImageUrl(optionalDish.getImageUrl());
                }
                dishRepository.save(updatedDish);
                return new ResponseModel("Success", updatedDish);
            }
            else
            {
                return new ResponseModel("DishNotFound", "Không tìm thấy món ăn");
            }
        } catch (Exception e) {
            return new ResponseModel("Fail", "Sửa món ăn thất bại");
        }
    }
    public ResponseModel deleteDish(String dishID) {
        try {
            Optional<Dish> optionalDish = dishRepository.findById(dishID);
            if (optionalDish.isPresent()) {
                Dish dish = optionalDish.get();
                dishRepository.delete(dish); // Xóa món ăn
                return new ResponseModel("Success", "Đã xoá món ăn");
            } else {
                return new ResponseModel("DishNotFound", "Không tìm thấy món ăn");
            }
        } catch (Exception e) {
            return new ResponseModel("Fail", "Xoá món ăn thất bại");
        }
    }

    public ResponseModel getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        if(!dishes.isEmpty())
        {
            return new ResponseModel("Success", dishes);
        }
        return new ResponseModel("NoDish", "Không có món ăn");
    }
    public Dish findById(String dishID)
    {
        Optional<Dish> dish=dishRepository.findById(dishID);

        if(dish.isEmpty())
        {
            return null;
        }
        return dish.get();
    }
}
