<?php

require 'vendor/autoload.php';

use Skylark95\AmazonFreeApp\FreeAppProvider;
use Skylark95\AmazonFreeApp\DomParserException;

$url = 'http://www.amazon.com/mobile-apps/b?node=2350149011';
$locale = 'en_US';
$cache_time = 3600;

date_default_timezone_set('UTC');
header('Content-type: application/json');
header("Cache-Control: max-age=$cache_time");

try {
    echo FreeAppProvider::create()->get_free_app($url, $locale, $cache_time);
} catch (DomParserException $e) {
    error_log('AmazonFreeApp: ' . $e->getMessage());
    http_response_code(503);
}

exit();
