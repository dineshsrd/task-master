package com.taskmaster.shared.constant;

import org.springframework.beans.factory.annotation.Value;

public class AppConstant {

    public static final String BASE_URL = "https://gnews.io/api/v4/";
    @Value("${security.api_key}")
    public static String API_KEY;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGES = 5;
}
