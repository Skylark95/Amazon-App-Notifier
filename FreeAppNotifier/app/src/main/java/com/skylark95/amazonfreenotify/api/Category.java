package com.skylark95.amazonfreenotify.api;

import android.content.Context;

import com.skylark95.amazonfreenotify.R;
import com.skylark95.amazonfreenotify.util.LogFactory;
import com.skylark95.amazonfreenotify.util.Logger;

public enum Category {

    BOOKS_COMICS(R.string.category_books_comics),
    CITY_INFO(R.string.category_city_info),
    COMMUNICATION(R.string.category_communication),
    COOKING(R.string.category_cooking),
    EDUCATION(R.string.category_education),
    ENTERTAINMENT(R.string.category_entertainment),
    FINANCE(R.string.category_finance),
    GAMES(R.string.category_games),
    HEALTH_FITNESS(R.string.category_health_fitness),
    KIDS(R.string.category_kids),
    LIFESTYLE(R.string.category_lifestyle),
    MUSIC(R.string.category_music),
    NAVIGATION(R.string.category_navigation),
    NEWS_MAGAZINES(R.string.category_news_magazines),
    NOVELTY(R.string.category_novelty),
    OTHER(R.string.category_other),
    PHOTOGRAPHY(R.string.category_photography),
    PODCASTS(R.string.category_podcasts),
    PRODUCTIVITY(R.string.category_productivity),
    REAL_ESTATE(R.string.category_real_estate),
    REFERENCE(R.string.category_reference),
    RINGTONES(R.string.category_ringtones),
    SHOPPING(R.string.category_shopping),
    SOCIAL_NETWORKING(R.string.category_social_networking),
    SPORTS(R.string.category_sports),
    THEMES(R.string.category_themes),
    TRAVEL(R.string.category_travel),
    UTILITIES(R.string.category_utilities),
    WEATHER(R.string.category_weather),
    WEB_BROWSERS(R.string.category_web_browsers);

    public static final Logger LOGGER = LogFactory.getLogger(Category.class);

    private int resId;

    Category (int resId) {
        this.resId = resId;
    }

    public String toString(Context context) {
        return context.getString(resId);
    }

    public static Category forString(String categoryStr, Context context) {
        Category[] values = values();
        for(Category category : values) {
            if (category.toString(context).equalsIgnoreCase(categoryStr)) {
                return category;
            }
        }

        String message = String.format("Unknown category \"%s\"", categoryStr);
        LOGGER.warn(message);
        return OTHER;
    }
}
