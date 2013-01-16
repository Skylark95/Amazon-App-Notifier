<?php

require 'DataAccessException.php';
require 'config/AppData.php';
require 'config/DB.php';

class DBHelper {
	
	private function __construct() { }
	
	public static function insertTodaysAppData($appData) {
		$con = DBHelper::getCon();
		
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
	
	public static function selectAppDataForDate($date) {
		$con = DBHelper::getCon();
		
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
	
	private static function getCon() {
		$con = mysql_connect(DB_HOST, DB_USER, DB_PASS);
		if (!$con)
		{
			$message = 'Could not connect: ' . mysql_error();
			throw new DataAccessException($message);
		}
		return $con; 
	}
	
}

?>