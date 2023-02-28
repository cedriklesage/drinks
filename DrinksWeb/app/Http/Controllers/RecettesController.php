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

        return view('drink', [
            'drink' => $recette,
            'id' => $id
        ]);
    }

}
