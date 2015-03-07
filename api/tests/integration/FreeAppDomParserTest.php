<?php

class FreeAppDomParserTest extends PHPUnit_Framework_TestCase {

    private static $output;

    /**
     * @beforeClass
     */
    public static function setUpBeforeClass()
    {
        self::$output = json_decode(file_get_contents('http://localhost:8000/?locale=en_US'), true);
    }

    /**
     * @test
     */
    public function doesReturnUSLocale()
    {
        $this->assertEquals('en_US', self::$output['locale']);
    }

    /**
     * @test
     */
    public function appUrlIs43CharactersLong()
    {
        $index = 'appUrl';
        $this->printValue($index);
        $this->assertEquals(43, strlen(self::$output[$index]));
    }

    /**
     * @test
     */
    public function asinIs10CharactersLong()
    {
        $index = 'asin';
        $this->printValue($index);
        $this->assertEquals(10, strlen(self::$output[$index]));
    }

    /**
     * @test
     */
    public function doesPopulateDeveloper()
    {
        $index = 'developer';
        $this->printValue($index);
        $this->assertNotEmpty(self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesPopulateName()
    {
        $index = 'name';
        $this->printValue($index);
        $this->assertNotEmpty(self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesPopulateTimestamp()
    {
        $index = 'timestamp';
        $this->printValue($index);
        $this->assertStringEndsWith('+0000', self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesPopulateOriginalPrice()
    {
        $index = 'originalPrice';
        $this->printValue($index);
        $this->assertNotEmpty(self::$output[$index]);
        $this->assertTrue(is_numeric(self::$output[$index]), 'Expected numeric value');
        $this->assertGreaterThan(0, (float) self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesPopulateRating()
    {
        $index = 'rating';
        $this->printValue($index);
        $this->assertNotEmpty(self::$output[$index]);
        $this->assertTrue(is_numeric(self::$output[$index]), 'Expected numeric value');
        $this->assertGreaterThan(0, (float) self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesPopulateIconUrl()
    {
        $index = 'iconUrl';
        $this->printValue($index);
        $this->assertStringStartsWith('http://', self::$output[$index]);
        $this->assertStringEndsWith('.png', self::$output[$index]);
    }

    /**
     * @test
     */
    public function doesReturnValidCategory()
    {
        $index = 'category';
        $categories = [
            'Books & Comics',
            'City Info',
            'Communication',
            'Cooking',
            'Education',
            'Entertainment',
            'Finance',
            'Games',
            'Health & Fitness',
            'Kids',
            'Lifestyle',
            'Music',
            'Navigation',
            'News & Magazines',
            'Novelty',
            'Other',
            'Photography',
            'Podcasts',
            'Productivity',
            'Real Estate',
            'Reference',
            'Ringtones',
            'Shopping',
            'Social Networking',
            'Sports',
            'Themes',
            'Travel',
            'Utilities',
            'Weather',
            'Web Browsers'
        ];
        $this->printValue($index);
        $this->assertContains(self::$output[$index], $categories);
    }

    /**
     * @test
     */
    public function doesPopulateDescription()
    {
        $index = 'description';
        $this->printValue($index);
        $this->assertNotEmpty(self::$output[$index]);
    }

    private function printValue($index)
    {
        echo "$index: '" . self::$output[$index] . "'\r\n";
    }

}
