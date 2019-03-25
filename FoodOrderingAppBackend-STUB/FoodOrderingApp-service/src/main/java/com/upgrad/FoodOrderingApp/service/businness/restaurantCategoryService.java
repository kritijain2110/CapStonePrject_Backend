package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.Restaurant_CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant_CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RestaurantCategoryService {

    @Autowired
    Restaurant_CategoryDao restaurantCategoryDao;

    @Autowired
    CategoryDao categoryDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<String> getAllCategoriesByRestaurant(RestaurantEntity r) {
        List<Restaurant_CategoryEntity> restaurantCategoryEntities = restaurantCategoryDao.getAllCategoriesByRestaurant(r);
        //   System.out.println(restaurantCategoryEntities);
        List<String> categories = new ArrayList<>();
        for (Restaurant_CategoryEntity rcEntity : restaurantCategoryEntities) {
            categories.add(categoryDao.getCategoryName(rcEntity.getCategoryEntity()));
        }
        java.util.Collections.sort(categories, Collator.getInstance());
        return categories;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryEntity> getAllCategoryEntitiesByRestaurant(RestaurantEntity r) {
        List<Restaurant_CategoryEntity> restaurantCategoryEntities = restaurantCategoryDao.getAllCategoriesByRestaurant(r);
        List<CategoryEntity> categories = new ArrayList<>();
        for (Restaurant_CategoryEntity rcEntity : restaurantCategoryEntities) {
            categories.add(rcEntity.getCategoryEntity());
        }

        return categories;

    }

    public List<Restaurant_CategoryEntity> getAllRestaurantsByCategory(CategoryEntity categoryEntity) {
        return restaurantCategoryDao.getAllRestaurantsByCategory(categoryEntity);
    }
}