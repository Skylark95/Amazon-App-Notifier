<?php

namespace Skylark95\AmazonFreeApp;

class FreeAppProviderTest extends \PHPUnit_Framework_TestCase {

    private $parser;
    private $cache;
    private $provider;
    private $locale;
    private $url;
    private $cache_time;

    /**
     * @before
     */
    public function setUp()
    {
        $this->parser = $this->getMockBuilder('Skylark95\AmazonFreeApp\FreeAppDomParser')
            ->disableOriginalConstructor()
            ->getMock();
        $this->cache = $this->getMockBuilder('Skylark95\AmazonFreeApp\FreeAppCache')
            ->disableOriginalConstructor()
            ->getMock();
        $this->provider = new FreeAppProvider($this->parser, $this->cache);
        $this->url = 'http://www.skylark95.com';
        $this->locale = 'en_US';
        $this->cache_time = 123;
    }

    /**
     * @test
     */
    public function canGetFreeAppFromCache()
    {
        $data = '{"name": "I am cached"}';
        $this->givenAppIsCached($data);
        $this->thenTheProviderReturns($data);
    }

    /**
     * @test
     */
    public function canGetFreeAppFromAmazon()
    {
        $data = '{"name": "I am not cached"}';
        $this->givenAppIsNotCached();
        $this->andTheParserWillReturn($data);
        $this->thenTheProviderReturns($data);
    }

    /**
     * @test
     */
    public function canCacheFreeAppFromAmazon()
    {
        $data = '{"name": "I will be cached"}';
        $this->givenAppIsNotCached();
        $this->andTheParserWillReturn($data);
        $this->thenTheDataWillBeCached($data);
    }

    private function givenAppIsCached($data)
    {
        $this->cache
            ->expects($this->once())
            ->method('is_cached')
            ->with('freeapp-en_US')
            ->willReturn(true);
        $this->cache
            ->expects($this->once())
            ->method('get_cache_data')
            ->with('freeapp-en_US')
            ->willReturn($data);
    }

    private function givenAppIsNotCached()
    {
        $this->cache
            ->expects($this->once())
            ->method('is_cached')
            ->with('freeapp-en_US')
            ->willReturn(false);
        $this->cache
            ->expects($this->never())
            ->method('get_cache_data');
    }

    private function andTheParserWillReturn($data)
    {
        $this->parser
            ->expects($this->once())
            ->method('fetch_json')
            ->with($this->url, $this->locale)
            ->willReturn($data);
    }

    private function thenTheProviderReturns($data)
    {
        $this->assertEquals($data, $this->getFreeApp());
    }

    private function thenTheDataWillBeCached($data)
    {
        $this->cache
            ->expects($this->once())
            ->method('set_cache_data')
            ->with('freeapp-en_US', $data);
        $this->getFreeApp();
    }

    private function getFreeApp()
    {
        return $this->provider->get_free_app($this->url, $this->locale, $this->cache_time);
    }

}
