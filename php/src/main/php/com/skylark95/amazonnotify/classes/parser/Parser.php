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
require_once 'classes/parser/AppStoreParser.php';
require_once 'libs/simple_html_dom.php';
require_once 'config.php';

class Parser {
	
	public function parseToArray() {
		$appUrl = $this->getAppOfDayUrl(); 
		return $this->buildAppData($appUrl);
	}
	
	private function getAppOfDayUrl() {
		$html = new simple_html_dom();
		$html->load_file(AMAZON_APPSTORE_URL);
		$appStoreParser = new AppStoreParser($html);
		return $appStoreParser->getAppUrl();
	}
	
	private function buildAppData($appUrl) {
		$html = new simple_html_dom();
		$html->load_file($appUrl);
		$appDataParser = new AppDataParser($html);
		
		$appData = array();
		$appData[APP_URL] = $appUrl;
		$appData[APP_TITLE] = $appDataParser->getAppTitle();
		$appData[APP_DEVELOPER] = $appDataParser->getAppDeveloper();
		$appData[APP_LIST_PRICE] = $appDataParser->getAppListPrice();
		$appData[APP_CATEGORY] = $appDataParser->getAppCategory();
		$appData[APP_DESCRIPTION] = $appDataParser->getAppDescription();
		
		return $appData;
	}
	
}