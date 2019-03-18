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
    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurants() {

        List<RestaurantEntity> restaurantEntityList = restaurantService.getRestaurants();

        List<RestaurantDetailsResponse> restaurantDetailsResponseList = new ArrayList<RestaurantDetailsResponse>();
        if (!restaurantEntityList.isEmpty()) {

            for (RestaurantEntity n : restaurantEntityList) {
                RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse();
               // restaurantDetailsResponse.setId(n.getUuid());
               // restaurantDetailsResponse.setContent(n.getContent());

                restaurantDetailsResponseList.add(restaurantDetailsResponse);
            }

        }

        return new ResponseEntity<>(restaurantDetailsResponseList, HttpStatus.OK);

    }


