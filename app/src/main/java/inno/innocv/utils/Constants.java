package inno.innocv.utils;

/**
 * @author eladiofreire on 21/8/17.
 */

public class Constants {

    public static final int RESPONSE_UNKNOWN_ERROR = 0;
    public static final int RESPONSE_NO_DATA = 10;
    public static final int RESPONSE_CODE_200 = 200;
    public static final int RESPONSE_CODE_400 = 400;
    public static final int RESPONSE_CODE_500 = 500;
    public static String URL = "http://hello-world.innocv.com/api/user";

    public static final String HOST_GET_USERS = "/getall";
    public static final String HOST_GET_USER = "/get";
    public static final String HOST_DELETE_USER = "/remove";
    public static final String HOST_CREATE_USER = "/create";
    public static final String HOST_UPDATE_USER = "/update";


    public static final String TITTLE_ADDED = "User added";
    public static final String MESSAGE_ADDED = "User succesfully added";
    public static final String TITTLE_DELETE = "User deleted";
    public static final String MESSAGE_DELETE = "User succesfully deleted";
    public static final String TITTLE_EDIT = "User updated";
    public static final String MESSAGE_EDIT = "User succesfully updated";
    public static final String TITTLE_ERROR = "Error";
    public static final String MESSAGE_ERROR = "Network Error try again later";
    public static final String MESSAGE_NO_DATA = "There aren't data for this id";
    public static final String MESSAGE_EMPTY = "You must fill in all fields";

}
