package com.upgrad.foodorderingapp.service.business;


import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.upgrad.foodorderingapp.service.entity.RestaurantEntity;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService implements EndPointIdentifier {

    @Autowired
    RestaurantDao restaurantDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> getAllRestaurants() {

        return restaurantDao.getAllRestaurants();

    }

}


