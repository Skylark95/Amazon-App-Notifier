<?php

namespace Skylark95\AmazonFreeApp;

class FreeAppProvider {

    private $parser;

    public static function create()
    {
        $parser = FreeAppDomParser::create();
        $cache = FreeAppCache::create();
        return new FreeAppProvider($parser, $cache);
    }

    public function __construct(FreeAppDomParser $parser, FreeAppCache $cache)
    {
        $this->parser = $parser;
        $this->cache = $cache;
    }

    public function get_free_app($url, $locale, $cache_time)
    {
        $output;
        $label = "freeapp-$locale";
        $cache = $this->get_cache();
        $cache->set_cache_time($cache_time);

        if ($cache->is_cached($label)) {
            $output = $cache->get_cache_data($label);
        } else {
            $parser = $this->get_parser();
            $output = $parser->fetch_json($url, $locale);
            $cache->set_cache_data($label, $output);
        }

        return $output;
    }

    private function get_parser()
    {
        return $this->parser;
    }

    private function get_cache()
    {
        return $this->cache;
    }

}
