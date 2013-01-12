<?php

include 'libs/simple_html_dom.php';
include 'AppData.php';


function parseAppData() {	
	$appUrl = getAppUrl();
	$html = file_get_html($appUrl);
	
	$appData = new AppData();
	
	$appData->appUrl = $appUrl;
	$appData->appTitle = getAppTitle($html);
	
	return $appData;
}

function getAppUrl() {
	$html = file_get_html('http://www.amazon.com/mobile-apps/b?ie=UTF8&node=2350149011');
	
	$domain = 'http://www.amazon.com';
	$url = $html->find('div[class=app-info-name]', 0)->find('a', 0)->href;
	return $domain . $url;
}

function getAppTitle($html) {
	return $html->find('span[id=btAsinTitle]', 0)->plaintext;
}

?>