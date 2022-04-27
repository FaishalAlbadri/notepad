<?php 
	
	require_once("koneksi.php");

	$response = array();

	$keyword = $_POST['key'];

	$result = mysqli_query($conn, "SELECT * FROM ayat WHERE ayat_indo LIKE '%$keyword%'");

	$row = mysqli_num_rows($result);

	if ($row > 0) {

		$response['alquran'] = array();

		foreach ($result as $key) {
			
			$data = array();
			$data['id'] = $key['id'];
			$idsurah = $key['id_surah'];
			$resultsurah = mysqli_query($conn, "SELECT * FROM surah WHERE id = '$idsurah'");
			foreach ($resultsurah as $keysurah) {
				$data['surah_name'] = $keysurah['name'];
			}
			$data['verse'] = $key['verse'];
			$data['ayat_arabic'] = $key['ayat_arabic'];
			$data['ayat_indo'] = $key['ayat_indo'];
			$data['ayat_latin'] = $key['ayat_latin'];

			array_push($response['alquran'], $data);
		}
		
		$response['msg'] = "Berhasil";
		echo json_encode($response);

	} else {
		$response['msg'] = "Data Kosong";
		echo json_encode($response);
	}
?>