<?php

    $requete = $_POST["requete"];
    $con = new PDO('mysql:host=cours.cegep3r.info;dbname=h2023_420606ri_eq6;charset=utf8', '2057794', 'Cnqo1248+' );

    switch($requete)
    {
        case "createUser":
            createUser($con);
            break;

        case "login":
            login($con);
            break;
    }

    function createUser($con)
    {
        $nom = $_POST["nom"];
        $prenom = $_POST["prenom"];
        $courriel = $_POST["email"];
        $motDePasse = $_POST["password"];

        try{
            $sql = "INSERT INTO users (first_name, last_name , email, password) VALUES ('$prenom', '$nom', '$courriel', MD5('$motDePasse'))";
            $resultat = $con->query($sql);
            echo json_encode(true);

        }catch(Exception $e){
            echo json_encode(false);
            return;
        }
        //Insert with MD5


        
    }

    function login($con)
    {
        $courriel = $_POST["email"];
        $motDePasse = $_POST["password"];

        $sql = "SELECT * FROM users WHERE email = '$courriel' AND password = MD5('$motDePasse')";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();

        if($ligne == false)
            echo json_encode(false);
        else
            echo json_encode(true);
    }

?>