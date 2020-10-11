<?php
class Desafio{
 
    private $conn;
    private $table_name = "desafio";
 
    public $id;
    public $nome;
    public $nivel;
    public $conteudo;
    public $autor;
 
    public function __construct($db){
        $this->conn = $db;
    }

    function read(){
        $query = 'select * from desafio';
     
        $stmt = $this->conn->prepare($query);

        $stmt->execute();
     
        return $stmt;
    }

    function create(){

        $query = "insert into
                    " . $this->table_name . "
                SET
                    nome=:nome, nivel=:nivel, conteudo=:conteudo, autor=:autor;";
     

        $stmt = $this->conn->prepare($query);
     
        $this->nome = htmlspecialchars(strip_tags($this->nome));
        $this->nivel = htmlspecialchars(strip_tags($this->nivel));
        $this->conteudo = htmlspecialchars(strip_tags($this->conteudo));
        $this->autor = htmlspecialchars(strip_tags($this->autor));

        $stmt->bindParam(":nome", $this->nome);
        $stmt->bindParam(":nivel", $this->nivel);
        $stmt->bindParam(":conteudo", $this->conteudo);
        $stmt->bindParam(":autor", $this->autor);
     
        if($stmt->execute()){
            return true;
        }
     
        return false;

    }     
}