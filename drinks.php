<?php
    header("Access-Control-Allow-Origin: *");
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
        
        case "getLikedDrinks":
            getLikedDrinks($con);
            break;

        case "loadSteps":
            loadSteps($con);
            break;

        case "getRecommendedDrinks":
            getRecommendedDrinks($con);
            break;

        case "getDrinkCategories":
            getDrinkCategories($con);
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

    function getLikedDrinks($con)
    {
        $idUser = $_POST["idUser"];

        $sql = "SELECT r.id, r.title, r.image, r.description, r.etapes, r.temps
                FROM recettes r
                INNER JOIN favoris f on r.id = f.recette_id
                WHERE f.user_id = $idUser";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function loadSteps($con)
    {
        $idDrink = $_POST["idDrink"];

        $sql = "SELECT e.id AS idStep,
                e.titre AS titre,
                e.description AS description,
                e.type AS stepType,
                e.quantite AS quantite,
                e.temps AS temps,
                i.nom AS nomIngredient,
                i.image AS imageIngredient
                FROM etapes e
                INNER JOIN ingredients i on e.ingredient_id = i.id
                WHERE e.recette_id = $idDrink";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function getRecommendedDrinks($con)
    {
        // Retourne 24 recettes aléatoires
        $sql = "select * from recettes order by rand() limit 24";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function getDrinkCategories($con)
    {
        $idDrink = $_POST["id"];
        $sql = "SELECT c.nom as titre FROM categories c
                INNER JOIN recettes_categories r on c.id = r.recette_id
                WHERE r.id = $idDrink";
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

    function getSearchResults($con)
    {
        $search = $_POST["search"];
        $sql = "SELECT DISTINCT recettes.id, recettes.title, recettes.image, recettes.description, recettes.etapes, recettes.temps, recettes.main_color
                FROM recettes
                LEFT JOIN recettes_categories ON recettes_categories.recette_id = recettes.id
                LEFT JOIN categories ON categories.id = recettes_categories.categorie_id
                WHERE recettes.title LIKE '%vodka%' OR categories.nom LIKE '%vodka%'
                OR recettes.title LIKE '%rhum%' OR categories.nom LIKE '%rhum%'";
        
        $resultat = $con->query($sql);
        $ligne = $resultat->fetchAll();

        echo json_encode($ligne);
    }

?>