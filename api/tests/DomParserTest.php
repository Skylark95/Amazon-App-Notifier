<?php

namespace Skylark95\AmazonFreeApp;

class DomParserTest extends \PHPUnit_Framework_TestCase {

    private $parser;

    /**
     * @before
     */
    public function setUp()
    {
        $this->parser = DomParser::create();
    }

}
