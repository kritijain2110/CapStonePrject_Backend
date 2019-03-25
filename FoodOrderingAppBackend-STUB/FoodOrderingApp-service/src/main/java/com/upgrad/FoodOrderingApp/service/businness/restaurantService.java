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

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> getAllRestaurantsByName(String name) {

        return restaurantDao.getAllRestaurantsByName(name);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity getRestaurantByUuid(String rUuid) throws RestaurantNotFoundException {
        RestaurantEntity re = restaurantDao.getRestaurantByUuid(rUuid);
        if (re == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        } else {
            return re;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity getRestaurantById(RestaurantEntity re) throws RestaurantNotFoundException {
        RestaurantEntity r = restaurantDao.getRestaurantById(re);
        if (r == null) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        } else {
            return re;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantDetails(RestaurantEntity re) {
        return restaurantDao.updateRestaurantDetails(re);
    }
}

}


