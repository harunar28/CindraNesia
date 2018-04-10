<?php
  include "koneksi.php";

  $user = $_GET['user'];
  $pass  = $_GET['pass'];

  $password = md5($pass);


  $query = "SELECT * FROM akun where username = '$user' and password = '$password'";


  $hasil = mysqli_query($connect,$query) or die (mysqli_error());
  $row = mysqli_fetch_assoc($hasil);
  
        if(mysqli_num_rows($hasil)> 0){
        
            $response['hasil']= array() ;
            $response["data"] = array();
        
            $pl = array();
            $hs = array();
        
            $pl["role"] = $row["role"];
            $pl["id"] = $row["id"];
            
            $hs["result"] = "true";
        
            array_push($response["hasil"], $hs);
            array_push($response["data"], $pl);
        
            echo json_encode($response);
        
        
        } else {
            $response['hasil']= array();
            $hs = array();
            $hs["result"] = "false";
            array_push($response["hasil"], $hs);
        
            echo json_encode($response);
        }
 ?>