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

require_once 'classes/parser/ParserException.php';
require_once 'libs/simple_html_dom.php';

class AppStoreParser {
	
	private $html;
	
	public function __construct($html) {
		if ($html === null) {
			throw new InvalidArgumentException('AppStoreParser: $html cannot be null');
		} else if (!$html) {
			throw new ParserException('AppStoreParser: An error occoured loading the page');
		}
		$this->html = $html;
	}
	
	public function getAppUrl() {
		$appInfoName = $this->html->find('div[class=app-info-name]', 0);		
		if ($appInfoName === null) {
			throw new ParserException('AppStoreParser: Could not find div[class=app-info-name]');
		}
		
		$anchor = $appInfoName->find('a', 0);
		if ($anchor === null) {
			throw new ParserException('AppStoreParser: Could not find url');
		}
		
		return AMAZON_DOMAIN . $anchor->href;
	}
}

?>