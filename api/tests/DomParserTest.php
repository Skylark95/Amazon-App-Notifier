<?php

namespace Skylark95\AmazonFreeApp;

class DomParserTest extends \PHPUnit_Framework_TestCase {

    private $parser;
    private $html;

    /**
     * @before
     */
    public function setUp()
    {
        $this->parser = DomParser::create();
        $this->html = $this->getMockBuilder('html')
            ->setMethods(['find'])
            ->getMock();
    }

    /**
     * @test
     */
    public function willReturnEmptyStringIfNothingFound()
    {
        $this->assertEquals('', $this->parser->find($this->html, 'query', 0, 'property'));
    }

    /**
     * @test
     */
    public function canReturnPropertyOfResult()
    {
        $result = $this->getMockBuilder('result');
        $result->plaintext = 'propertyValue';
        $this->html->method('find')
            ->with('query', 0)
            ->willReturn($result);

        $this->assertEquals('propertyValue', $this->parser->find($this->html, 'query', 0, 'plaintext'));
    }

}
