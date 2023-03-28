<?php
    header("Access-Control-Allow-Origin: *");
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

        case "changeUserName":
            changeUserName($con);
            break;

        case "changeUserEmail":
            changeUserEmail($con);
            break;

        case "checkPassword":
            checkPassword($con);
            break;
        
        case "changePassword":
            changePassword($con);
            break;

        case "getUserInfo":
            getUser($con);
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

            $sql = "SELECT id FROM users WHERE email = '$courriel' AND password = MD5('$motDePasse')";
            $resultat = $con->query($sql);
            $ligne = $resultat->fetch();

            echo json_encode($ligne["id"]);

        }catch(Exception $e){
            echo json_encode(false);
            return;
        }        
    }

    function login($con)
    {
        $courriel = $_POST["email"];
        $motDePasse = $_POST["password"];

        $sql = "SELECT id FROM users WHERE email = '$courriel' AND password = MD5('$motDePasse')";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();

        if($ligne == false)
            echo json_encode(0);
        else
            echo json_encode($ligne["id"]);
    }
    
    function changeUserName($con)
    {
        $nom = $_POST["last_name"];
        $prenom = $_POST["first_name"];
        $id = $_POST["id"];

        try{
            $sql = "UPDATE users SET first_name = '$prenom', last_name = '$nom' WHERE id = '$id'";
            $resultat = $con->query($sql);

            echo json_encode(true);
        }
        catch(Exception $e){
            echo json_encode(false);
        }
    }

    function changeUserEmail($con)
    {
        $email = $_POST["email"];
        $id = $_POST["id"];

        try{
            $sql = "UPDATE users SET email = '$email' WHERE id = '$id'";
            $resultat = $con->query($sql);

            echo json_encode(true);
        }
        catch(Exception $e){
            echo json_encode(false);
        }
    }

    function checkPassword($con)
    {
        $id = $_POST["id"];
        $password = $_POST["password"];
        $sql = "SELECT id FROM users WHERE id = '$id' AND password = MD5('$password')";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();

        if($ligne == false)
            echo json_encode($resultat);
        else
            echo json_encode(true);
    }

    function changePassword($con)
    {
        $id = $_POST["id"];
        $password = $_POST["password"];

        try{
            $sql = "UPDATE users SET password = MD5('$password') WHERE id = '$id'";
            $resultat = $con->query($sql);

            echo json_encode(true);
        }
        catch(Exception $e){
            echo json_encode(false);
        }

    }

    function getUser($con)
    {
        $id = $_POST["id"];
        $sql = "SELECT * FROM users WHERE id = '$id'";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();

        echo json_encode($ligne);
    }

?>