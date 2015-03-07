<?php

namespace Skylark95\AmazonFreeApp;

class FreeAppCacheTest extends \PHPUnit_Framework_TestCase {

    private $cache;
    private $simple_cache;

    /**
     * @before
     */
    public function setUp()
    {
        $this->simple_cache = $this->getMockBuilder('Gilbitron\Util\SimpleCache')->getMock();
        $this->cache = new FreeAppCache($this->simple_cache);
    }

    /**
     * @test
     */
    public function doesInitWithCachePath()
    {
        $this->assertEquals($this->simple_cache->cache_path, 'cache/');
    }

    /**
     * @test
     */
    public function canCheckIfLabelIsCached()
    {
        $label = 'label';
        $this->simple_cache
            ->expects($this->once())
            ->method('is_cached')
            ->with($label)
            ->willReturn(true);

        $this->assertTrue($this->cache->is_cached($label));
    }

    /**
     * @test
     */
    public function canGetCacheData()
    {
        $label = 'label';
        $data = '{"data": true}';
        $this->simple_cache
            ->expects($this->once())
            ->method('get_cache')
            ->with($label)
            ->willReturn($data);

        $this->assertEquals($data, $this->cache->get_cache_data($label));
    }

    /**
     * @test
     */
    public function canSetCacheData()
    {
        $label = 'label';
        $data = '{"data": true}';
        $this->simple_cache
            ->expects($this->once())
            ->method('set_cache')
            ->with($label, $data);

        $this->cache->set_cache_data($label, $data);
    }

    /**
     * @test
     */
    public function canSetCacheTime()
    {
        $this->cache->set_cache_time(123);
        $this->assertEquals(123, $this->simple_cache->cache_time);
    }
}
