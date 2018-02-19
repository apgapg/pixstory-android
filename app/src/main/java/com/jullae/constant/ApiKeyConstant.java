package com.jullae.constant;

/**
 * Developer: Rahul Abrol
 * Dated: 13-12-2017.
 */

public interface ApiKeyConstant {
    String MESSAGE = "message";
    String DATA = "data";
    String STATUS_CODE = "statusCode";
    String BOOKING_ID = "bookingId";
    String LATITUDE = "lat";
    String LONGITUDE = "lng";
    String TIMESTAMP = "timestamp";

    String ID = "id";
    String FROM_PROFILE = "from_profile";
    String VOUCHER_CODE = "vouchercode";

    //RestClient Constants.
    String csrf = "csrftoken=";
    String session = ";sessionid=";
    String AUTHORIZATION = "Authorization";
    String CSRF_TOKEN = "X-CSRFToken";
    String COOKIE = "Cookie";
    String IS_PRODUCT = "is_product";
    String IS_TRAVEL = "is_travel_pack";

    String TITLE = "title";
    String F_NAME = "first_name";
    String L_NAME = "last_name";
    String PHONE_NO = "phone_number";
    String COUNTRY = "country";
    String CITY = "state";
    String ZIP = "postcode";
    String LANE1 = "line1";
    String LANE2 = "line2";
    String LANE3 = "line3";
    String LANE4 = "line4";
    String TOKEN = "csrfmiddlewaretoken";
    String LINE_METHODS = "line_methods";
    String LINE = "line";
    String METHODS = "method";
    String CART_LIST = "cart_list";

    String ANY = "0";
    String FROM = "from";
    String SHIPPING_METHOD = "shipping_method";
    String BENEFICIARY = "beneficiaries";
    String LIMIT = "limit";
    String TRANSECTION_ID = "transaction_id";
    String PAYMENT_TYPE = "payment_type";
    String PICK_UP = "pickup";
    String ADDRESS = "address";
    String TYPE = "type";
    String ADDRESS_ID = "address_id";
    String PICK_UP_ID = "pickup_id";

    //Error Constants.
    interface ErrorKeys {
        String ERROR_NON_FIELD = "non_field_errors";
        String ERROR_REASON = "reason";
        String ERROR_PHONE_NUMBER = "phone_number";
        String ERROR_TITLE = "title";
        String ERROR_DETAIL = "detail";
    }
}
