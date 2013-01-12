<?php

include 'parser.php';

$appData = parseAppData();

echo '<b>appUrl:</b> ' . $appData->appUrl . '<br>';
echo '<b>appTitle:</b> ' . $appData->appTitle . '<br>';

?>