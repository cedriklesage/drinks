<?php
    $requete = $_POST["requete"];
    $con = new PDO('mysql:host=cours.cegep3r.info;dbname=h2023_420606ri_eq6;charset=utf8', '2057794', 'Cnqo1248+' );

    switch($requete)
    {
        case "getUsers":
            getUsers($con);
            break;

        case "createUserAdmin":
            createUserAdmin($con);
            break;
        
        case "deleteUser":
            deleteUser($con);
            break;
        
        case "updateUser":
            updateUser($con);
            break;
    }

    function getUsers($con)
    {
        $sql = "select * from users";
        $resultat = $con->query($sql); 
                
        $ligne = $resultat->fetchAll(); 

        echo json_encode($ligne);
    }

    function deleteUser($con)
    {
        $id = $_POST["id"];
        $sql = "delete from users where id = $id";
        $resultat = $con->query($sql); 
    }

    function updateUser($con)
    {
        $id = $_POST["id"];
        $nom = $_POST["nom"];
        $prenom = $_POST["prenom"];
        $email = $_POST["email"];
        $password = $_POST["password"];
        $admin = $_POST["admin"];

        $sql = "update users set nom = '$nom', prenom = '$prenom', email = '$email', password = '$password', admin = '$admin' where id = $id";
        $resultat = $con->query($sql); 
    }

    function createUserAdmin($con)
    {
        $nom = $_POST["nom"];
        $prenom = $_POST["prenom"];
        $email = $_POST["email"];
        $password = $_POST["password"];
        $admin = $_POST["admin"];

        $sql = "insert into users (nom, prenom, email, password, admin) values ('$nom', '$prenom', '$email', '$password', '$admin')";
        $resultat = $con->query($sql);
    }
?>