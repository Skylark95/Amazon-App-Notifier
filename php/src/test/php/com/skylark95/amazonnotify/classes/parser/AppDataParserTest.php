<?php
/*
 * This file is part of Amazon App Notifier
 *
 * Copyright 2013 Derek <derek@skylark95.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazon App Notifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazon App Notifier. if not, If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

require_once 'classes/parser/AppDataParser.php';
require_once 'classes/parser/ParserException.php';
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
	 * @expectedException ParserException
	 * @expectedExceptionMessage AppDataParser: An error occoured loading the page
	 */
	public function appStoreParserThrowsExceptionIfFalseHtml() {
		new AppDataParser(false);
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