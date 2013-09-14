<?php

abstract class AbstractXPath {
	
	public function queryNodeValue(DOMXPath $domXPath, $xpath, $pos) {
		$results = $domXPath->query($xpath);
		if ($results->length > $pos) {
			return $results->item($pos)->nodeValue;
		}
		return null;
	}
	
}

?>