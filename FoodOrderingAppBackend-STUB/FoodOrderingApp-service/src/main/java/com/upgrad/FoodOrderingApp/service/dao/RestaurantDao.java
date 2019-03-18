package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant_CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method to create a new RestaurantEntity
     *
     * @param restaurantEntity contains the questionEntity to be persisted
     * @return RestaurantEntity that has been persisted in the database
     */

    public RestaurantEntity createRestaurant(RestaurantEntity restaurantEntity) {
        entityManager.persist(restaurantEntity);
        return restaurantEntity;
    }


    /**
     * Method to get the List of All the questions
     *
     * @return List<QuestionEntity>
     */

    public List<RestaurantEntity> getRestaurants() {

        try {

            String query = "select r from RestaurantEntity r";
            return entityManager.createQuery(query, RestaurantEntity.class)
                    .getResultList();
        } catch (NoResultException nre) {

            return null;
        }
    }

}
