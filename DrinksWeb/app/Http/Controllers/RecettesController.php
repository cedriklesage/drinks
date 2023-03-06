<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

use App\Models\Recette;
use App\Models\User;


class RecettesController extends Controller
{
    public function onboarding()
    {
        return view('welcome');
    }

    public function sign_in_page()
    {
        return view('sign-in');
    }

    public function sign_in(Request $request)
    {
        $user = DB::table('users')->where('email', $request->email)->first();

        if ($user && Hash::check($request->password, $user->password)) {
            return redirect()->route('accueil');
        } else {
            return redirect()->route('sign-in');
        }
    }

    public function sign_up_page()
    {
        return view('sign-up');
    }

    public function sign_up(Request $request)
    {
        try
        {
            $user = DB::table('users')->insert([
                'first_name' => $request->first_name,
                'last_name' => $request->last_name,
                'email' => $request->email,
                'password' => Hash::make($request->password)
            ]);
        }
        catch(\Exception $e)
        {
            return redirect()->route('sign-up');
        }

        return redirect()->route('sign-in');
    }










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

    public function favoris()
    {
        if(!Auth::check()) {
            $login = false;
            $recettes = null;
        }
        else
        {
            $login = true;
            $recettes = DB::table('recettes')
            ->select('recettes.id', 'recettes.title', 'recettes.image', 'recettes.description', 'recettes.etapes', 'recettes.temps')
            ->join('favoris', 'recettes.id', '=', 'favoris.recette_id')
            ->where('favoris.user_id', Auth::user()->id)
            ->get();
        }


        return view('favoris', [
            'drinks' => $recettes,
            'login' => $login
        ]);
    }

}
