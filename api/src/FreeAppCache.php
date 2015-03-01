<?php

namespace Skylark95\AmazonFreeApp;

use Gilbitron\Util\SimpleCache;

class FreeAppCache {

    public static function create()
    {
        return new FreeAppCache();
    }

    public function get($cache_time)
    {
        $cache = new SimpleCache();
        $cache->cache_path = 'cache/';
        $cache->cache_time = $cache_time;
        return $cache;
    }

}
