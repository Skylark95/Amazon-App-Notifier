package com.skylark95.amazonfreenotify.api;

import com.skylark95.amazonfreenotify.util.LogFactory;
import com.skylark95.amazonfreenotify.util.Logger;

public enum Category {

    BOOKS_COMICS("Books & Comics"),
    CITY_INFO("City Info"),
    COMMUNICATION("Communication"),
    COOKING("Cooking"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    FINANCE("Finance"),
    GAMES("Games"),
    HEALTH_FITNESS("Health & Fitness"),
    KIDS("Kids"),
    LIFESTYLE("Lifestyle"),
    MUSIC("Music"),
    NAVIGATION("Navigation"),
    NEWS_MAGAZINES("News & Magazines"),
    NOVELTY("Novelty"),
    OTHER("Other"),
    PHOTOGRAPHY("Photography"),
    PODCASTS("Podcasts"),
    PRODUCTIVITY("Productivity"),
    REAL_ESTATE("Real Estate"),
    REFERENCE("Reference"),
    RINGTONES("Ringtones"),
    SHOPPING("Shopping"),
    SOCIAL_NETWORKING("Social Networking"),
    SPORTS("Sports"),
    THEMES("Themes"),
    TRAVEL("Travel"),
    UTILITIES("Utilities"),
    WEATHER("Weather"),
    WEB_BROWSERS("Web Browsers");

    public static final Logger LOGGER = LogFactory.getLogger(Category.class);

    private String text;

    Category (String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Category forString(String categoryStr) {

        for(Category category : values()) {
            if (category.getText().equalsIgnoreCase(categoryStr)) {
                return category;
            }
        }

        String message = String.format("Unknown category \"%s\"", categoryStr);
        LOGGER.warn(message);
        return OTHER;
    }
}
