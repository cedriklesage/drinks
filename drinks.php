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
    } 


    function getDailyDrink($con)
    {

        $sql = "select * from recettes";
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


    

?>