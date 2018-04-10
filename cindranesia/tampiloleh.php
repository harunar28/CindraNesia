<?php
  include "koneksi.php";

  $query = "SELECT produk.jenis_produk AS jenis_produk, produk.judul_produk AS judul_produk,
	toko.nama_toko AS nama_toko, toko.alamat_toko AS alamat_toko,
	toko.kota_toko AS kota_toko FROM produk LEFT JOIN toko ON toko.id_toko = produk.id_toko";

  $hasil = mysqli_query($connect,$query) or die (mysql_error());

  if(mysqli_num_rows($hasil)> 0){

    $response['result']= "true" ;
    $response["data"] = array();

    // fungsi perulangan
 while ($row = mysqli_fetch_assoc($hasil)) {

     $pl = array();

     $pl["judul_produk"] = $row["judul_produk"];
     $pl["nama_toko"] = $row["nama_toko"];
     $pl["alamat_toko"] = $row["alamat_toko"];
     $pl["kota_toko"] = $row["kota_toko"];
     $pl["jenis_produk"] = $row["jenis_produk"];

     array_push($response["data"], $pl);


 }


 echo json_encode($response);


} else {
 $response['result']= "false" ;
}

 ?>