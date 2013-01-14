<?php

require_once 'libs/simple_html_dom.php';

function getBucketByName($html, $name) {
	$retVal = null;

	foreach ($html->find('.bucket') as $bucket) {
		$header1 = $bucket->find('h2', 0); 
		$header2 = $bucket->find('div[class=h2]', 0);
		if ($header1 != null) $header1 = $header1->plaintext;
		if ($header2 != null) $header2 = $header2->plaintext;
		
		if ($header1 == $name || $header2 == $name) {
			$retVal = $bucket;
			break;
		} 
	}
	
	return $retVal;
}

?>
