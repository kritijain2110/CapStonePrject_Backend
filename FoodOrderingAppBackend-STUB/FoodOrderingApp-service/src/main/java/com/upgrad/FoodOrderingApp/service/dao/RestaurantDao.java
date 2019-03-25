package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant_CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;

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
     * Method to get the List of All the restaurants
     *
     * @return List<RestaurantEntity>
     */

    @PersistenceContext
    private EntityManager entityManager;


    public List<RestaurantEntity> getAllRestaurants() {

        try {
            //order by r.customerRating desc
            String query = "select r from RestaurantEntity r";
            return entityManager.createQuery(query, RestaurantEntity.class)
                    .getResultList();
        } catch (NoResultException nre) {

            return new ArrayList<RestaurantEntity>();
        }
    }

    public List<RestaurantEntity> getAllRestaurantsByName(String restaurantName) {
        try {
            String query = "select r from RestaurantEntity r where lower(r.restaurantName) like lower(:name) order by r.customerRating desc";
            return entityManager.createQuery(query, RestaurantEntity.class)
                    .setParameter("name", '%' + restaurantName + '%').getResultList();

        } catch (NoResultException nre) {

            return new ArrayList<RestaurantEntity>();
        }
    }


    public RestaurantEntity getRestaurantByUuid(String restaurantId) {
        try {
            String query = "select r from RestaurantEntity r where r.uuid = :uuid";
            return entityManager.createQuery(query, RestaurantEntity.class)
                    .setParameter("uuid", restaurantId).getSingleResult();

        } catch (NoResultException nre) {

            return null;
        }
    }


    public RestaurantEntity updateRestaurantDetails(RestaurantEntity restaurantEntity) {

        entityManager.merge(restaurantEntity);
        return restaurantEntity;

    }


    public RestaurantEntity getRestaurantById(RestaurantEntity restaurantEntity) {


        try {

            String query = "select u from RestaurantEntity u where u.id = :userInput";
            return entityManager.createQuery(query, RestaurantEntity.class)
                    .setParameter("userInput", restaurantEntity.getId()).getSingleResult();

        } catch (NoResultException nre) {

            return null;
        }
    }

}
