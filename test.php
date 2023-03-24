<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$con = new PDO('mysql:host=cours.cegep3r.info;dbname=h2023_420606ri_eq6;charset=utf8', '2057794', 'Cnqo1248+' );


function test($con)
{
    $search = ;

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

test($con);
?>