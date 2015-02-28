<?php

require 'vendor/autoload.php';
use Sunra\PhpSimple\HtmlDomParser;
use Gilbitron\Util\SimpleCache;

$locale = 'en_US';
$cache_time = 3600;
date_default_timezone_set('UTC');
header('Content-type: application/json');
header("Cache-Control: max-age=$cache_time");
echo get_free_app_json($locale, $cache_time);
exit();

/*
 * FUNCTIONS
 */
function build_json_from_amazon($locale)
{
    $output = [];
    $output['locale'] = $locale;
    $output['timestamp'] = date(DATE_ISO8601);

    // Store Page
    $html = HtmlDomParser::file_get_html('http://www.amazon.com/mobile-apps/b?node=2350149011');
    if (empty($html)) {
        fail("Failed to load store page");
    }

    $output['appUrl'] = $html->find('h3.fad-widget-app-name a', 0);
    if (empty($output['appUrl'])) {
        fail("Could not find app url");
    }

    $output['appUrl'] = 'http://www.amazon.com' . $output['appUrl']->href;
    $output['appUrl'] = substr($output['appUrl'], 0, strpos($output['appUrl'], 'product/') + 18);
    $output['asin'] = substr($output['appUrl'], strlen($output['appUrl']) - 10);


    // App Page
    $html = HtmlDomParser::file_get_html($output['appUrl']);
    if (empty($html)) {
        fail("Failed to load app page");
    }

    $output['name'] = trim(safe_find($html->find('span[id=btAsinTitle]', 0), 'plaintext'));
    if (empty($output['name'])) {
        fail("Could not find app name");
    }

    $output['originalPrice'] = trim(substr(safe_find($html->find('span[id=listPriceValue]', 0), 'plaintext'), 1));
    $output['rating'] = explode(' ', safe_find($html->find('span.asinReviewsSummary span[title]', 0), 'plaintext'))[0];
    $output['developer'] = trim(safe_find($html->find('div.buying span a', 0), 'plaintext'));
    $output['category'] = trim(safe_find($html->find('div.bucket ul li a', -1), 'plaintext'));
    $output['iconUrl'] = safe_find($html->find('img[id=main-image-nonjs]', 0), 'src');
    $output['description'] = trim(safe_find($html->find('div.bucket div.content', 1), 'plaintext'));

    return json_encode($output);
}

function safe_find($html, $property)
{
    if (!empty($html)) {
        return $html->$property;
    }
    return '';
}

function fail($reason)
{
    error_log("amazonfreenotify: $reason");
    http_response_code(503);
    exit();
}

function get_cache($cache_time)
{
    $cache = new SimpleCache();
    $cache->cache_path = 'cache/';
    $cache->cache_time = $cache_time;
    return $cache;
}

function get_free_app_json($locale, $cache_time)
{
    $output;
    $label = "freeapp-$locale";
    $cache = get_cache($cache_time);

    if ($cache->is_cached($label)) {
        $output = $cache->get_cache($label);
    } else {
        $output = build_json_from_amazon($locale);
        $cache->set_cache($label, $output);
    }

    return $output;
}

