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