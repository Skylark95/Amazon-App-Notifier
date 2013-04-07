<?php

require_once 'classes/parser/Parser.php';
require_once 'classes/parser/ParserException.php';
require_once 'classes/dao/AppDataDao.php';
require_once 'classes/dao/DataAccessException.php';
require_once 'config.php';

/*
 * Run Daily at 01:10 America/Denver
 */ 
date_default_timezone_set('America/Denver');
cron();

function cron($tries = 0) {	
	try {
		$conn = AppDataDao::connect();
		$parser = new Parser();
		$appData = $parser->parseToArray();
		if ($appData === null) {
			throw new ParserException('Null App Data!');
		}
		
		$dao = new AppDataDao($conn);
		$dao->insertTodaysAppData($appData);
		
		echo 'Amazon App Data cron finished successfully on ' . date(DATE_COOKIE);
		echo '<br/>Today\'s free app is ' . $appData[APP_TITLE];
	} catch (DataAccessException $e) {
		echo 'Amazon App Data cron failed with a DataAccessException on ' . date(DATE_COOKIE);
		echo '<br/>' . $e->getMessage();
	} catch (ParserException $e) {
		if ($tries < 3) {
			echo 'ParserException... trying again - ' . date(DATE_COOKIE);
			echo '<br/>' . $e->getMessage() . '<br/>';
			$tries++;
			cron($tries);
		} else {
			echo 'Amazon App Data cron failed with a ParserException on ' . date(DATE_COOKIE);
			echo '<br/>' . $e->getMessage();
		}
	}
}

?>