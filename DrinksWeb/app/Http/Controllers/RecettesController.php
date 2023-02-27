<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

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
}
