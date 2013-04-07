<?php

require_once 'classes/parser/AppStoreParser.php';
require_once 'classes/parser/ParserException.php';
require_once 'libs/simple_html_dom.php';
require_once 'config.php';

class AppStoreParserTest extends PHPUnit_Framework_TestCase {
	
	
	/**
	 * @test
	 * @expectedException InvalidArgumentException
	 */
	public function appStoreParserThrowsExceptionIfNullHtml() {
		new AppStoreParser(null);
	}
	
	/**
	 * @test
	 * @expectedException ParserException
	 * @expectedExceptionMessage AppStoreParser: An error occoured loading the page
	 */
	public function appStoreParserThrowsParserExceptionIfFalseHmtl() {
		new AppStoreParser(false);
	}
	
	/**
	 * @test
	 */
	public function canGetAppOfTheDayUrl() {
		$htmlStr = file_get_contents('AppStoreParserTestResource.html', true);
		$html = new simple_html_dom();
		$html->load($htmlStr);
		$appStoreParser = new AppStoreParser($html);
		$this->assertEquals('http://www.amazon.com/gp/product/B008I3RARW/ref=mas_faad', $appStoreParser->getAppUrl());
	}
	
	/**
	 * @test
	 * @expectedException ParserException
	 */
	public function doesThrowParserExceptionIfClassAppInfoNameNotFound() {
		$html = new simple_html_dom();
		$html->load('<html><body>Hello!</body></html>');
		$appStoreParser = new AppStoreParser($html);
		$appStoreParser->getAppUrl();
	}
	
	/**
	 * @test
	 * @expectedException ParserException
	 */
	public function doesThrowParserExceptionIfAnchorNotFound() {
		$html = new simple_html_dom();
		$html->load('<html><body><div class="app-info-name">No Url found here</div></body></html>');
		$appStoreParser = new AppStoreParser($html);
		$appStoreParser->getAppUrl();
	}
	
}
?>