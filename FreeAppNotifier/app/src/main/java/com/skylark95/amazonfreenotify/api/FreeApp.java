package com.skylark95.amazonfreenotify.api;

import java.math.BigDecimal;
import java.util.Locale;

public interface FreeApp {

    String getName();
    String getAsin();
    BigDecimal getOriginalPrice();
    float getRating();
    String getDeveloper();
    String getCateogry();
    String getIconUrl();
    String getAppUrl();
    String getDescription();
    Locale getLocale();

}
