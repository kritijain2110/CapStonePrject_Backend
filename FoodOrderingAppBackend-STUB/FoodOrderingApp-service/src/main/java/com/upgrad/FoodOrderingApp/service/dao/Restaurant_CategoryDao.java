package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant_CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantCategoryDao {


    @PersistenceContext
    private EntityManager entityManager;

    public List<Restaurant_CategoryEntity> getAllCategoriesByRestaurant(RestaurantEntity restaurantEntity) {
        try {
            List restaurantcategory = new ArrayList();
            String query = "select r from Restaurant_CategoryEntity r where r.restaurant = :userInput";
            restaurantcategory = entityManager.createQuery(query, Restaurant_CategoryEntity.class)
                    .setParameter("userInput", restaurantEntity).getResultList();

            return restaurantcategory;

        } catch (NoResultException nre) {

            return new ArrayList<Restaurant_CategoryEntity>();
        }
    }

    public List<Restaurant_CategoryEntity> getAllRestaurantsByCategory(CategoryEntity categoryEntity) {
        try {
            List restaurantcategory = new ArrayList();
            String query = "select r from Restaurant_CategoryEntity r where r.category = :userInput";
            restaurantcategory = entityManager.createQuery(query, Restaurant_CategoryEntity.class)
                    .setParameter("userInput", categoryEntity).getResultList();

            return restaurantcategory;

        } catch (NoResultException nre) {

            return new ArrayList<Restaurant_CategoryEntity>();
        }
    }
}