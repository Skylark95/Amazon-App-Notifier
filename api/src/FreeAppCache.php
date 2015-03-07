<?php

namespace Skylark95\AmazonFreeApp;

use Gilbitron\Util\SimpleCache;

class FreeAppCache {

    private $cache;

    public static function create()
    {
        $simple_cache = new SimpleCache();
        return new FreeAppCache($simple_cache);
    }

    public function __construct(SimpleCache $cache)
    {
        $this->cache = $cache;
        $this->cache->cache_path = 'cache/';
    }

    public function get_cache_data($label)
    {
        return $this->cache->get_cache($label);
    }

    public function is_cached($label)
    {
        return $this->get_cache()->is_cached($label);
    }

    public function set_cache_data($label, $data)
    {
        $this->get_cache()->set_cache($label, $data);
    }

    public function set_cache_time($cache_time)
    {
        $this->cache->cache_time = $cache_time;
    }

    private function get_cache()
    {
        return $this->cache;
    }

}
