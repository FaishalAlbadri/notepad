<?php 
	define('HOST', 'localhost');
	define('USER', 'root');
	define('PASS', 'tropistcoffee2021');
	define('DB', 'alquran');
	
	$conn = new mysqli(HOST, USER, PASS, DB) or die('Koneksi Ke Database Error');
 ?>