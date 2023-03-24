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

        case "searchDrinks":
            searchDrinks($con);
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
        $first_name = $_POST["first_name"];
        $last_name = $_POST["last_name"];
        $email = $_POST["email"];
        $admin = $_POST["admin"];

        $sql = "update users set first_name = '$first_name', last_name = '$last_name', email = '$email', admin = '$admin' where id = $id";
        $resultat = $con->query($sql);

        echo json_encode(true);
    }

    function createUserAdmin($con)
    {
        $first_name = $_POST["first_name"];
        $last_name = $_POST["last_name"];
        $email = $_POST["email"];
        $password = $_POST["password"];
        $admin = $_POST["admin"];

        $sql = "insert into users (first_name, last_name, email, password, admin) values ('$first_name', '$last_name', '$email', MD5('$password'), '$admin')";
        $resultat = $con->query($sql);

        echo json_encode(true);
    }

    function searchDrinks($con)
    {

        $search = $_POST["search"];

        $sql = "SELECT DISTINCT recettes.id, recettes.title, recettes.image, recettes.description, recettes.etapes, recettes.temps, recettes.main_color
        FROM recettes
        LEFT JOIN recettes_categories ON recettes_categories.recette_id = recettes.id
        LEFT JOIN categories ON categories.id = recettes_categories.categorie_id
        WHERE (recettes.title LIKE '%$search%' OR categories.nom LIKE '%$search%')
        ";
        $resultat = $con->query($sql); 

        $ligne = $resultat->fetchAll();
            
        echo json_encode($ligne);


    }
?>