<?php

require_once 'classes/parser/Parser.php';

$parser = new Parser();
echo json_encode($parser->parseToArray());

?>