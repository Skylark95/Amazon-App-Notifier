<?php

include 'AppDataParser.php';

$parser = new AppDataParser();
$appData = $parser->parseToArray();

echo json_encode($appData);

?>