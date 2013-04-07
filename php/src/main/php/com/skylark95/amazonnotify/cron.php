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