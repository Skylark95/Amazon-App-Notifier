<?php

require 'Constants.php';
require 'p/DBConstants.php';

class DBHelper {
	
	function insertAppData($appData) {		
		$con = mysql_connect("localhost", DB_USER, DB_PASS);
		if (!$con)
		{
			die('Could not connect: ' . mysql_error());
		}
		
		mysql_select_db("skylarkn_amazonnotify_dev", $con);

		$appUrl = mysql_real_escape_string($appData[APP_URL]);
		$appTitle = mysql_real_escape_string($appData[APP_TITLE]);
		$appDeveloper = mysql_real_escape_string($appData[APP_DEVELOPER]);
		$appListPrice = mysql_real_escape_string($appData[APP_LIST_PRICE]);
		$appCategory = mysql_real_escape_string($appData[APP_CATEGORY]);
		$appDescription = mysql_real_escape_string($appData[APP_DESCRIPTION]);
		
		$query = mysql_query("INSERT INTO FREE_APP_OF_DAY (APP_DATE, APP_URL, APP_TITLE, APP_DEVELOPER, APP_LIST_PRICE, APP_CATEGORY, APP_DESCRIPTION)
			  		 VALUES (CURDATE(), '$appUrl', '$appTitle', '$appDeveloper', '$appListPrice', '$appCategory', '$appDescription')");
		
		if(!$query) {
			die('Could not insert: ' . mysql_error());
		}
		
		mysql_close($con);		
	}
	
}

?>