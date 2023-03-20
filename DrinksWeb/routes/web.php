<?php

use Illuminate\Support\Facades\Route;

use App\Http\Controllers\RecettesController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

/* Onboarding and login */

Route::get('/bienvenue',
    [RecettesController::class, 'onboarding'])->name('onboarding');

Route::get('/connexion',
    [RecettesController::class, 'sign_in_page'])->name('sign-in');

Route::post('/connexion',
    [RecettesController::class, 'sign_in'])->name('sign-in-attempt');

Route::get('/inscription',
    [RecettesController::class, 'sign_up_page'])->name('sign-up');

Route::post('/inscription',
    [RecettesController::class, 'sign_up'])->name('sign-up-attempt');

Route::get('/deconnexion',
    [RecettesController::class, 'sign_out'])->name('sign-out');



Route::get('/',
    [RecettesController::class, 'accueil'])->name('accueil');

Route::get('/drink/{id}',
    [RecettesController::class, 'drink'])->name('drink');

Route::get('/favoris',
    [RecettesController::class, 'favoris'])->name('favoris');

Route::get('/search',
    [RecettesController::class, 'search'])->name('search');


// Mon compte
Route::get('/compte',
    [RecettesController::class, 'account'])->name('compte');


