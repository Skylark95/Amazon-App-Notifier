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

require_once 'classes/dao/AppDataDao.php';
require_once 'classes/dao/DataAccessException.php';
require_once 'config.php';

class MysqliMock {
	
	public $errno;
	public $error;
	public $affected_rows;
	
	public function real_escape_string($arg) {		
	}
	
	public function query($arg) {		
	}
	
}

class Mysqli_resultMock {
	
	public function fetch_assoc() {
	}
	
}

class AppDataDaoTest extends PHPUnit_Framework_TestCase {
	
	/**
	 * @test
	 */
	public function verifyInsertTodaysAppData() {		
		$appData = $this->getAppData();
		$appUrl = $appData[APP_URL];
		$appTitle = $appData[APP_TITLE];
		$appDeveloper = $appData[APP_DEVELOPER];
		$appListPrice = $appData[APP_LIST_PRICE];
		$appCategory = $appData[APP_CATEGORY];
		$appDescription = $appData[APP_DESCRIPTION];
		
		$mockMysqli = $this->getMock('MysqliMock', array('query', 'real_escape_string'));
	
		$mockMysqli->expects($this->exactly(6))
				->method('real_escape_string')
				->will($this->returnArgument(0));
		
		$mockMysqli->expects($this->once())
				->method('query')
				->will($this->returnValue(true))
				->with($this->equalTo("INSERT INTO free_app_of_day (
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
					)"));
		
		$appDataDao = new AppDataDao($mockMysqli);
		$appDataDao->insertTodaysAppData($appData);
	}
	
	/**
	 * @test
	 * @expectedException DataAccessException
	 * @expectedExceptionMessage AppDataDao: Failed to insert: (0) Some Error Message
	 */
	public function throwDataAccessExceptionIfInsertTodaysAppDataFails() {
		$appData = $this->getAppData();
		$mockMysqli = $this->getMock('MysqliMock', array('query', 'real_escape_string'));
		
		$mockMysqli->expects($this->exactly(6))
				->method('real_escape_string')
				->will($this->returnArgument(0));
		
		$mockMysqli->expects($this->once())
				->method('query')
				->will($this->returnValue(false));
		
		$mockMysqli->errno = 0;
		$mockMysqli->error = 'Some Error Message';
		
		$appDataDao = new AppDataDao($mockMysqli);
		$appDataDao->insertTodaysAppData($appData);
	}
	
	/**
	 * @test
	 */
	public function verifySelectAppDataForDate() {
		$mockMysqli = $this->getMock('MysqliMock', array('query'));
		$mockMysqli_result = $this->getMock('Mysqli_resultMock', array('fetch_assoc'));
		
		$appData = $this->getAppData();
		$mockMysqli_result->expects($this->once())
				->method('fetch_assoc')
				->will($this->returnValue($appData));
		
		$date = '2013-03-17';
		$mockMysqli->expects($this->once())
				->method('query')
				->will($this->returnValue($mockMysqli_result))
				->with($this->equalTo("SELECT
								app_date,
								app_url,
								app_title,
								app_developer,
								app_list_price,
								app_category,
								app_description
							  FROM free_app_of_day
							  WHERE app_date = '$date'"));
				
		
		$mockMysqli->affected_rows = 1;
		
		$appDataDao = new AppDataDao($mockMysqli);
		$returnedData = $appDataDao->selectAppDataForDate($date);
		$this->assertEquals($appData, $returnedData);
	}
	
	/**
	 * @test
	 * @expectedException DataAccessException
	 * @expectedExceptionMessage AppDataDao: No rows found for 2013-03-17 
	 */
	public function selectAppDataForDateWhenNoRecordsThrowsException() {
		$mockMysqli = $this->getMock('MysqliMock', array('query'));
		
		$date = '2013-03-17';
		$mockMysqli->expects($this->once())
				->method('query')
				->will($this->returnValue(true));
		
		$mockMysqli->affected_rows = 0;
		
		$appDataDao = new AppDataDao($mockMysqli);
		$appDataDao->selectAppDataForDate($date);
	}
	
	/**
	 * @test
	 * @expectedException DataAccessException
	 * @expectedExceptionMessage AppDataDao: Failed to select: (1) Another Error
	 */
	public function selectAppDataForDateWhenFailedThrowsException() {
		$mockMysqli = $this->getMock('MysqliMock', array('query'));
		
		$date = '2013-03-17';
		$mockMysqli->expects($this->once())
				->method('query')
				->will($this->returnValue(false));
		
		$mockMysqli->errno = 1;
		$mockMysqli->error = 'Another Error';
		
		$appDataDao = new AppDataDao($mockMysqli);
		$appDataDao->selectAppDataForDate($date);
	}
	
	private function getAppData() {
		$appData = array();
		$appData[APP_URL] = 'http://www.amazon.com/gp/product/B008I3RARW/';
		$appData[APP_TITLE] = 'Race Rally 3D - Racing Car Arcade Fun';
		$appData[APP_DEVELOPER] = 'Sulaba Inc';
		$appData[APP_LIST_PRICE] = '$4.99';
		$appData[APP_CATEGORY] = 'Games';
		$appData[APP_DESCRIPTION] = 'The Race Rally is the best 3d arcade racing game';
		return $appData;
	}
	
}

?>