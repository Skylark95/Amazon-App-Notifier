<?php

require_once 'classes/parser/AppDataParser.php';
require_once 'libs/simple_html_dom.php';

class AppDataParserTest extends PHPUnit_Framework_TestCase {
	
	private $appDataParser;
	
	
	protected function setUp() {
		$this->appDataParser = null;
	}
	
	/**
	 * @test
	 * @expectedException InvalidArgumentException
	 */
	public function appStoreParserThrowsExceptionIfNullHtml() {
		new AppDataParser(null);
	}

	/**
	 * @test
	 */
	public function canGetAppListPrice() {
		$this->appDataParserWithDefaultTestResource();
		$this->assertEquals('$4.99', $this->appDataParser->getAppListPrice());
	}
	
	/**
	 * @test
	 */
	public function canGetAppTitle() {
		$this->appDataParserWithDefaultTestResource();
		$this->assertEquals('Race Rally 3D - Racing Car Arcade Fun', $this->appDataParser->getAppTitle());
	}
	
	/**
	 * @test
	 */
	public function canGetAppCategory() {
		$this->appDataParserWithDefaultTestResource();
		$this->assertEquals('Games', $this->appDataParser->getAppCategory());
	}
	
	/**
	 * @test
	 */
	public function canGetAppDeveloper() {
		$this->appDataParserWithDefaultTestResource();
		$this->assertEquals('Sulaba Inc', $this->appDataParser->getAppDeveloper());
	}
	
	/**
	 * @test
	 */
	public function canGetAppDescription() {
		$this->appDataParserWithDefaultTestResource();
		$this->assertStringStartsWith('The Race Rally is the best 3d arcade racing game features day or night',
		$this->appDataParser->getAppDescription());
	}

	
	private function appDataParserWithDefaultTestResource() {
		$htmlStr = file_get_contents('AppDataParserTestResource.html', true);
		$html = new simple_html_dom();
		$html->load($htmlStr);
		$this->appDataParser = new AppDataParser($html);
	}
	
	
}
?>