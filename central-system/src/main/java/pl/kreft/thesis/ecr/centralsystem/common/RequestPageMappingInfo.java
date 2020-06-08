package pl.kreft.thesis.ecr.centralsystem.common;

public class RequestPageMappingInfo {

    //REDIRECT
    public static final String REDIRECT = "redirect:";

    //INDEX
    public static final String INDEX_PAGE_REQUEST = "/index";

    //HOME
    public static final String HOME_PAGE_REQUEST = "/home";

    // PAGE FOR LOGIN
    public static final String LOGIN_PAGE_REQUEST = "/login";

    //PAGE FOR CAR
    public static final String CAR_REQUEST = "/cars";

    //ERROR
    public static final String ERROR_REQUEST = "/error";
    public static final String ERROR_403_REQUEST = "/403";

    //Rental
    public static final String RENTAL_REQUEST = "/rentals";
    public static final String RENTAL_RETURN_REQUEST_WITH_ID = "/return/{id}";
    public static final String RENTAL_RETURN_REQUEST = "/return";
    public static final String RENTAL_HISTORY_REQUEST = "/history";

    //Common
    public static final String CREATE_NEW_REQUEST = "/create";
}
