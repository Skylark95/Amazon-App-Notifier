package com.skylark95.amazonfreenotify.api;

import java.math.BigDecimal;

public interface FreeApp {

    String getName();
    BigDecimal getOriginalPrice();
    BigDecimal getRating();
    String getDeveloper();
    Category getCateogry();
    String getIconUrl();
    String getAppUrl();
    String getDescription();

}