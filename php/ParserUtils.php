<?php

function getBucketByName($html, $name) {
	$retVal = null;

	foreach ($html->find('.bucket') as $bucket) {
		$current = $bucket->find('h2', 0)->plaintext;
		if ($current == $name) {
			$retVal = $bucket;
			break;
		}
	}

	return $retVal;
}

?>