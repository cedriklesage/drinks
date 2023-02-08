<?php

    $requete = $_POST["requete"];
    $con = new PDO('mysql:host=cours.cegep3r.info;dbname=h2023_420606ri_eq6;charset=utf8', '2057794', 'Cnqo1248+' ); 

    switch($requete)
    {
        case "getDailyDrink":
            getDailyDrink($con);
            break;

        case "getWeeklyDrinks":
            getWeeklyDrinks($con);
            break;
        
        case "getDrinkIngredients":
            getDrinkIngredients($con);
            break;

        case "checkLike":
            checkLike($con);
            break;

        case "switchLike":
            switchLike($con);
            break;
    } 


    function getDailyDrink($con)
    {

        $sql = "select * from recettes order by rand() limit 1";
        $resultat = $con->query($sql); 
                
        $ligne = $resultat->fetch(); 

        echo json_encode($ligne);
    }

    function getWeeklyDrinks($con)
    {

        //Retourne 7 recettes aléatoires
        $sql = "select * from recettes order by rand() limit 7";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function getDrinkIngredients($con)
    {
        $idDrink = $_POST["id"];

        $sql = "SELECT ingredients.id, ingredients.nom, ingredients.type, ingredients.alcool, ingredients.pays, ingredients.image, ri.quantite
                FROM ingredients
                INNER JOIN recettes_ingredients ri on ingredients.id = ri.ingredient_id
                INNER JOIN recettes r on ri.recette_id = r.id
                WHERE r.id = $idDrink";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function checkLike($con)
    {
        $idDrink = $_POST["idDrink"];
        $idUser = $_POST["idUser"];

        $sql = "SELECT * FROM favoris WHERE recette_id = $idDrink AND user_id = $idUser";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();
        if($ligne == false)
            echo json_encode(false);
        else
            echo json_encode(true);

    }

    function switchLike($con)
    {
        $idDrink = $_POST["idDrink"];
        $idUser = $_POST["idUser"];

        $sql = "SELECT * FROM favoris WHERE recette_id = $idDrink AND user_id = $idUser";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetch();
        if($ligne == false)
        {
            $sql = "INSERT INTO favoris (recette_id, user_id) VALUES ($idDrink, $idUser)";
            $con->exec($sql);
            echo json_encode(true);
        }
        else
        {
            $sql = "DELETE FROM favoris WHERE recette_id = $idDrink AND user_id = $idUser";
            $con->exec($sql);
            echo json_encode(false);
        }
    }

?>