package inno.innocv.utils;

import inno.innocv.R;

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


    public static final String MESSAGE_NO_DATA = "There aren't data for this id";
    public static final String MESSAGE_EMPTY = "You must fill in all fields";

}
