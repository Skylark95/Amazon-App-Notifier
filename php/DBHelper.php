<?php

require_once 'Constants.php';
require_once 'DataAccessException.php';
require 'p/DBConstants.php';


class DBHelper {
	
	function insertTodaysAppData($appData) {		
		$con = $this->getCon();
		
		mysql_select_db("skylarkn_amazonnotify_dev", $con);

		$appUrl = mysql_real_escape_string($appData[APP_URL]);
		$appTitle = mysql_real_escape_string($appData[APP_TITLE]);
		$appDeveloper = mysql_real_escape_string($appData[APP_DEVELOPER]);
		$appListPrice = mysql_real_escape_string($appData[APP_LIST_PRICE]);
		$appCategory = mysql_real_escape_string($appData[APP_CATEGORY]);
		$appDescription = mysql_real_escape_string($appData[APP_DESCRIPTION]);
		
		$result = mysql_query("INSERT INTO free_app_of_day (
								app_date,
								app_url,
								app_title,
								app_developer,
								app_list_price,
								app_category,
								app_description)
			  		 		  VALUES (
								CURDATE(),
								'$appUrl',
								'$appTitle',
								'$appDeveloper',
								'$appListPrice',
								'$appCategory',
								'$appDescription'
							  )");
		
		if(!$result) {
			$message = 'Could not insert: ' . mysql_error();
			mysql_close($con);
			throw new DataAccessException($message);
		}
		
		mysql_close($con);		
	}
	
	function selectAppDataForDate($date) {
		$con = $this->getCon();
		
		mysql_select_db("skylarkn_amazonnotify_dev", $con);
		
		$result = mysql_query("SELECT
								app_date,
								app_url,
								app_title,
								app_developer,
								app_list_price,
								app_category,
								app_description
							  FROM free_app_of_day
							  WHERE app_date = '$date'");
		
		if(!$result) {
			$message = 'Could not select: ' . mysql_error();
			mysql_close($con);
			throw new DataAccessException($message);			
		}		
		if (mysql_num_rows($result) == 0) {
			$message = "No rows found for $date";
			mysql_close($con);
			throw new DataAccessException($message);
		}
		
		$row = mysql_fetch_assoc($result);		
		mysql_close($con);
		
		return $row;
	}
	
	private function getCon() {
		$con = mysql_connect("localhost", DB_USER, DB_PASS);
		if (!$con)
		{
			$message = 'Could not connect: ' . mysql_error();
			throw new DataAccessException($message);
		}
		return $con; 
	}
	
}

?>