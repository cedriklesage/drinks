<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

use App\Models\Recette;


class RecettesController extends Controller
{
    public function accueil()
    {
        // Random daily drink
        $dailyDrink = Recette::inRandomOrder()->first();

        // Return all drinks
        $recettes = Recette::all();

        return view('accueil', [
            'drinks' => $recettes,
            'dailyDrink' => $dailyDrink
        ]);
    }


    public function drink($id)
    {
        $recette = Recette::find($id);

        $steps = DB::table('etapes as e')
        ->select('e.id as idStep', 'e.titre as titre', 'e.description as description', 'e.type as stepType', 'e.quantite as quantite', 'e.temps as temps', 'i.nom as nomIngredient', 'i.image as imageIngredient')
        ->join('ingredients as i', 'e.ingredient_id', '=', 'i.id')
        ->where('e.recette_id', '=', $id)
        ->get()
        ->toArray();

        return view('drink', [
            'drink' => $recette,
            'steps' => $steps,
            'id' => $id
        ]);
    }

}
