import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
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
    CustomerService customerService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    RestaurantCategoryService restaurantCategoryService;

    @Autowired
    AddressService addressService;

    @Autowired
    CategoryItemService categoryItemService;

    @Autowired
    ItemService itemService;

    @Autowired
    CategoryService categoryService;





    /**
     * Method implements the get all resturant endpoint
     *
     * @param accessToken assigned to the user upon signin
     * @return ResponseEntity to indicate the status of the query as well as the list of questions
     * @throws AuthorizationFailedException
     */

    @GetMapping(path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() {

        RestaurantListResponse restaurantListResponse = null;
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        List<RestaurantList> restaurantList = new ArrayList<>();
        if (!restaurantService.getAllRestaurants().isEmpty()) {

            restaurantEntities = restaurantService.getAllRestaurants();
            for (RestaurantEntity r : restaurantEntities) {

                //Get the categories
                List<String> categories = restaurantCategoryService.getAllCategoriesByRestaurant(r);
                StringBuilder strBuilder = new StringBuilder("");
                for(int i=0;i< categories.size();i++){
                    if(i== categories.size()-1){
                        strBuilder.append(categories.get(i));
                    }else{
                        strBuilder.append(categories.get(i)+", ");
                    }
                }
                String categoriesString = strBuilder.toString();

                RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
                RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();

                AddressEntity addressEntity = addressService.getAddressById(r.getAddress());

                StateEntity stateEntity = addressService.getStateById(addressEntity.getStateEntity());
                restaurantDetailsResponseAddressState.setId(UUID.fromString(stateEntity.getUuid()));
                restaurantDetailsResponseAddressState.setStateName(stateEntity.getState_name());

                restaurantDetailsResponseAddress.city(addressEntity.getCity());
                restaurantDetailsResponseAddress.pincode(addressEntity.getPincode());
                restaurantDetailsResponseAddress.flatBuildingName(addressEntity.getFlat_buil_number());
                restaurantDetailsResponseAddress.locality(addressEntity.getLocality());
                restaurantDetailsResponseAddress.id(UUID.fromString(addressEntity.getUuid()));
                restaurantDetailsResponseAddress.state(restaurantDetailsResponseAddressState);

                RestaurantList restaurant = new RestaurantList().id(UUID.fromString(r.getUuid()))
                        .address(restaurantDetailsResponseAddress)
                        .numberCustomersRated(r.getNumberOfCustomersRated()).photoURL(r.getPhotoUrl())
                        .restaurantName(r.getRestaurantName()).averagePrice(r.getAveragePriceForTwo())
                        .customerRating(r.getCustomerRating()).categories(categoriesString);


                restaurantList.add(restaurant);
            }
            ;
        }

        return new ResponseEntity(new RestaurantListResponse().restaurants(restaurantList), HttpStatus.OK);


    }

    }


