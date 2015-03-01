<?php

namespace Skylark95\AmazonFreeApp;

use Sunra\PhpSimple\HtmlDomParser;

class DomParser {

    public static function create()
    {
        return new DomParser();
    }

    public function get_html($url)
    {
        $html = HtmlDomParser::file_get_html($url);
        if (empty($html)) {
            throw new DomParserException("Failed to load URL: $url");
        }

        return $html;
    }

    public function find($html, $query, $index, $property)
    {
        $result = $html->find($query, $index);

        if (empty($result)) {
            return '';
        }

        return $result->$property;
    }

}
