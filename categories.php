<?php
    $requete = $_POST["requete"];
    $con = new PDO('mysql:host=cours.cegep3r.info;dbname=h2023_420606ri_eq6;charset=utf8', '2057794', 'Cnqo1248+' ); 

    switch($requete)
    {
        case "getDrinkCategories":
            getDrinkCategories($con);
            break;
    }
    

    function getDrinkCategories($con)
    {
        $id = $_POST["id"];
        //Get the name of the category using the joint table recettes_categories
        $sql = "SELECT categories.id, categories.nom
        FROM categories
        INNER JOIN recettes_categories ON categories.id = recettes_categories.categorie_id
        INNER JOIN recettes ON recettes_categories.recette_id = recettes.id
        WHERE recettes.id = $id";
        $result = $con->query($sql);
        $categories = $result->fetchAll();

        echo json_encode($categories);
    }

?>
