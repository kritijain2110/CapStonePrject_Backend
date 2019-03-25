import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.api.model.RestaurantUpdatedResponse;
import org.junit.experimental.categories.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class RestaurantController implements EndPointIdentifier {

    // Implemented Endpoint Identifier interface for generic AuthorizationFailedException Handling

    @Autowired
    RestaurantService restaurantService;

    /**
     * Method implements the get all resturantd endpoint
     *
     * @param accessToken assigned to the user upon signin
     * @return ResponseEntity to indicate the status of the query as well as the list of questions
     * @throws AuthorizationFailedException
     */

    @GetMapping(path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurants() {

        RestaurantDetailsResponse restaurantListResponse = null;
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        List<RestaurantList> restaurantList = new ArrayList<>();
        if (!restaurantService.getAllRestaurants().isEmpty()) {

            restaurantEntities = restaurantService.getAllRestaurants();
            for (RestaurantEntity r : restaurantEntities)
            {
                RestaurantList restaurant = new RestaurantList().id(UUID.fromString(r.getUuid()))

                        .numberCustomersRated(r.getNumberOfCustomersRated()).photoURL(r.getPhotoUrl())
                        .restaurantName(r.getRestaurantName()).averagePrice(r.getAveragePriceForTwo())
                        .customerRating(r.getCustomerRating()).categories("chinese");


                restaurantList.add(restaurant);
            }

        }

        return new ResponseEntity(new RestaurantListResponse().restaurants(restaurantList), HttpStatus.OK);


    }


