<?php 
$host = $_SERVER['HTTP_HOST'];
$dir = '';
$get = '?location=US&format=v2';
if (isset($_GET['appVersionCode'])) {
	$app = 	$_GET['appVersionCode'];
	$get = "$get&app=$app";
}
$header = "Location: http://$host/$dir$get";
header($header);
?>