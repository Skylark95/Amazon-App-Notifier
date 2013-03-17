<?php

require_once 'classes/dao/DataAccessException.php';
require_once 'classes/dao/DBConfig.php';
require_once 'config.php';

class AppDataDao {
	
	private $mysqli;
	
	public function __construct($mysqli) {
		$this->mysqli = $mysqli;
	}
	
	public static function connect() {
		$mysqliConn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
		if ($mysqliConn->connect_errno) {
			$message = "Failed to connect to MySQL: " . $mysqliConn->connect_error;
			throw new DataAccessException($message);
		}
		return $mysqliConn;
	}
	
	public function insertTodaysAppData($appData) {		
		$appUrl = $this->mysqli->real_escape_string($appData[APP_URL]);
		$appTitle = $this->mysqli->real_escape_string($appData[APP_TITLE]);
		$appDeveloper = $this->mysqli->real_escape_string($appData[APP_DEVELOPER]);
		$appListPrice = $this->mysqli->real_escape_string($appData[APP_LIST_PRICE]);
		$appCategory = $this->mysqli->real_escape_string($appData[APP_CATEGORY]);
		$appDescription = $this->mysqli->real_escape_string($appData[APP_DESCRIPTION]);
			
		$result = $this->mysqli->query(
			"INSERT INTO free_app_of_day (
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
			$message = 'AppDataDao: Failed to insert: (' . $this->mysqli->errno . ') ' . $this->mysqli->error;
			throw new DataAccessException($message);
		}
	}
	
	public function selectAppDataForDate($date) {		
		$result = $this->mysqli->query("SELECT
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
			$message = 'AppDataDao: Failed to select: (' . $this->mysqli->errno . ') ' . $this->mysqli->error;
			throw new DataAccessException($message);			
		}		
		
		if ($this->mysqli->affected_rows == 0) {
			$message = "AppDataDao: No rows found for $date";
			throw new DataAccessException($message);
		}
		
		return $result->fetch_assoc();
	}

	
}

?>