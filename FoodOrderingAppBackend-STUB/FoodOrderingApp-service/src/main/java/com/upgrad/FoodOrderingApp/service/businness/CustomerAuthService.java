package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;

public interface CustomerAuthService {

    void addAccessToken(Integer userId, String accessToken);

    void removeAccessToken(String accessToken);

    CustomerAuthEntity isCustomerLoggedIn(String accessToken);

    Integer getCustomerId(String accessToken);
}
