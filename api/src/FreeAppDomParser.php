<?php

namespace Skylark95\AmazonFreeApp;

class FreeAppDomParser {

    private $parser;

    public static function create()
    {
        $parser = DomParser::create();
        return new FreeAppDomParser($parser);
    }

    public function __construct(DomParser $parser)
    {
        $this->parser = $parser;
    }

    public function fetch_json($url, $locale)
    {
        $output = [];
        $output['locale'] = $locale;
        $output['timestamp'] = date(DATE_ISO8601);

        $this->fetch_store_page_details($url, $output);
        $this->fetch_app_page_details($output['appUrl'], $output);

        return json_encode($output);
    }

    private function fetch_store_page_details($url, array &$output)
    {
        $parser = $this->get_parser();
        $html = $parser->get_html($url);

        $output['appUrl'] = $parser->find($html, 'h3.fad-widget-app-name a', 0, 'href');
        if (empty($output['appUrl'])) {
            throw new DomParserException('Could not find app url');
        }

        // Prepend domain
        $output['appUrl'] = 'http://www.amazon.com' . $output['appUrl'];
        // Strip URL after ASIN
        $output['appUrl'] = substr($output['appUrl'], 0, strpos($output['appUrl'], 'product/') + 18);
        // Get ASIN from URL
        $output['asin'] = substr($output['appUrl'], strlen($output['appUrl']) - 10);
    }

    private function fetch_app_page_details($url, array &$output)
    {
        $parser = $this->get_parser();
        $html = $parser->get_html($url);

        $output['name'] = trim($parser->find($html, 'span[id=btAsinTitle]', 0, 'plaintext'));
        if (empty($output['name'])) {
            throw new DomParserException('Could not find app name');
        }

        // Trim $ sign from price
        $output['originalPrice'] = trim(substr($parser->find($html, 'span[id=listPriceValue]', 0, 'plaintext'), 1));
        // Retrive only first part of rating text
        $output['rating'] = explode(' ', $parser->find($html, 'span.asinReviewsSummary span[title]', 0, 'plaintext'))[0];
        $output['developer'] = trim($parser->find($html, 'div.buying span a', 0, 'plaintext'));
        $output['category'] = trim($parser->find($html, 'div.bucket ul li a', -1, 'plaintext'));
        $output['iconUrl'] = $parser->find($html, 'img[id=main-image-nonjs]', 0, 'src');

        // Find Description by content title
        $output['description'] = trim($this->find_content($html, 'Product Description'));
    }

    private function find_content($html, $title)
    {
        $parser = $this->get_parser();
        foreach ($html->find('div.bucket div.content') as $content) {
            if (trim($parser->find($content->parent(), 'h2', 0, 'plaintext') === $title)) {
                return $content->plaintext;
            }
        }
    }

    private function get_parser()
    {
        return $this->parser;
    }

}
