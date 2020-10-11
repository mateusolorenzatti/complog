<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
 
include_once '../config/database.php';
include_once '../objects/desafio.php';
 
$database = new Database();
$db = $database->getConnection();
 
$desafio = new Desafio($db);
 
$stmt = $desafio->read();
$num = $stmt->rowCount();
 
if($num > 0){
 
    $desafios_arr = array();
 
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);
 
        $desafio_item = array(
            "id" => $idDesafio,
            "nome" => $nome,
            "nivel" => $nivel,
            "counteudo" => $conteudo,
            "autor" => $autor
        );
 
        array_push($desafios_arr, $desafio_item);
    }

    echo json_encode($desafios_arr);
}
 
else{
    echo json_encode(
        array("message" => "Sagu")
    );
}
?>