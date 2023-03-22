<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Session;

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

        if ($user && md5($request->password) === $user->password) {
            Session::put('user', $user);
            return redirect()->route('accueil');
        } else {
            $error = true;
            return redirect()->route('sign-in')->with('error', $error);
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
                'password' => md5($request->password)
            ]);
        }
        catch(\Exception $e)
        {
            return redirect()->route('sign-up');
        }

        return redirect()->route('sign-in');
    }

    public function sign_out()
    {
        Session::forget('user');
        return redirect()->route('accueil');
    }










    public function accueil()
    {
        // Random daily drink
        $dailyDrink = Recette::inRandomOrder()->first();

        // Return all drinks
        $recettes = Recette::all();


        if(Session::get('user') == null) {
            return redirect()->route('onboarding');
        }
        else
        {
            $user = Session::get('user');
            $user_id = $user->id;
        }


        $categories = DB::table('recettes_categories as rc')
        ->select('c.nom as title', 'rc.recette_id as recette_id', 'rc.categorie_id as categorie_id')
        ->join('categories as c', 'rc.categorie_id', '=', 'c.id')
        ->get()
        ->toArray();

        return view('accueil', [
            'drinks' => $recettes,
            'dailyDrink' => $dailyDrink,
            'user_id' => $user_id,
            'categories' => $categories
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
            'id' => $id,
        ]);
    }

    public function favoris()
    {
        if(Session::get('user') == null) {
            return redirect()->route('onboarding');
        }
        else
        {
            $recettes = DB::table('recettes')
            ->select('recettes.id', 'recettes.title', 'recettes.image', 'recettes.description', 'recettes.etapes', 'recettes.temps', 'recettes.main_color')
            ->join('favoris', 'recettes.id', '=', 'favoris.recette_id')
            ->where('favoris.user_id', Session::get('user')->id)
            ->get();

            $categories = DB::table('recettes_categories as rc')
            ->select('c.nom as title', 'rc.recette_id as recette_id', 'rc.categorie_id as categorie_id')
            ->join('categories as c', 'rc.categorie_id', '=', 'c.id')
            ->get()
            ->toArray();

            $user = Session::get('user');
            $user_id = $user->id;

            return view('accueil', [
                'drinks' => $recettes,
                'user_id' => $user_id,
                'categories' => $categories
            ]);
        }

    }

    public function search(Request $request)
    {
        $searchTerms = explode(' ', $request->search);

        $recettes = DB::table('recettes')
            ->select('recettes.id', 'recettes.title', 'recettes.image', 'recettes.description', 'recettes.etapes', 'recettes.temps', 'recettes.main_color')
            ->leftJoin('recettes_categories', 'recettes_categories.recette_id', '=', 'recettes.id')
            ->leftJoin('categories', 'categories.id', '=', 'recettes_categories.categorie_id')
            ->where(function ($query) use ($searchTerms) {
                foreach ($searchTerms as $term) {
                    $query->orWhere('recettes.title', 'like', '%' . $term . '%')
                          ->orWhere('categories.nom', 'like', '%' . $term . '%');
                }
            })
            ->distinct()
            ->get();

        $categories = DB::table('recettes_categories as rc')
        ->select('c.nom as title', 'rc.recette_id as recette_id', 'rc.categorie_id as categorie_id')
        ->join('categories as c', 'rc.categorie_id', '=', 'c.id')
        ->get()
        ->toArray();

        if(Session::get('user') == null) {
            return redirect()->route('onboarding');
        }
        else{
            $user = Session::get('user');
            $user_id = $user->id;
        }


        return view('accueil', [
            'drinks' => $recettes,
            'user_id' => $user_id,
            'categories' => $categories,
            'search_request' => $request->search
        ]);
    }



    // MON COMPTE

    public function account()
    {
        if(Session::get('user') == null) {
            return redirect()->route('onboarding');
        }
        else
        {
            $user = Session::get('user');
            $user_id = $user->id;

            $user = DB::table('users')->where('id', $user_id)->first();

            return view('account', [
                'user' => $user,
                'user_id' => $user_id
            ]);
        }
    }


}
