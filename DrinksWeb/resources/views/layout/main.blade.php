<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="{{asset('css/style.css')}}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <title>@yield('title')</title>
</head>
<body>
    

    <header class="header p-25">
        <div class="left-header">
            <a href="/"><img class="header-logo" src="{{asset('img/drink-white-logo.png')}}" alt=""></a>
            <a href="/">Découvrir</a>
            <a href="">Créer</a>
            <a href="/favoris">Favoris</a>
        </div>

        <div class="right-header">
            <button class="search-button">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <path d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6 0-33.9zM208 336c-70.7 0-128-57.2-128-128s57.2-128 128-128 128 57.2 128 128-57.2 128-128 128z"/>
                </svg>
            </button>
            <a href="/profil">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                    <path d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/>
                </svg>
            </a>
            <a href="/deconnexion">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"/>
                </svg>
            </a>
        </div>
        
    </header>
    <div class="drink-popup">
        <div class="w-50 drink-popup-content">
            <div>
                <button class="popup-drink-back-button">X</button>
                <div class="main-info">
                    <div class="drink-categories"></div>
                    <h1 class="popup-drink-name">Placeholder name</h1>
                    <p class="popup-drink-desc">Placeholder description</p>
                </div>
                <h2 class="m-b-25">Ingrédients</h2>
                <div class="main-ingredients">
                </div>
            </div>
        </div>
        <div class="start-drink">
            <a href="" class="btn">Commencer la recette</a>
            <button class="btn heart-button" data-uid="{{$user_id}}">
                <svg id="heart-svg" viewBox="467 392 58 57" xmlns="http://www.w3.org/2000/svg">
                    <g id="Group" fill="none" fill-rule="evenodd" transform="translate(467 392)">
                      <path id="heart" d="M29.144 20.773c-.063-.13-4.227-8.67-11.44-2.59C7.63 28.795 28.94 43.256 29.143 43.394c.204-.138 21.513-14.6 11.44-25.213-7.214-6.08-11.377 2.46-11.44 2.59z" fill="#AAB8C2"/>
                    </g>
                  </svg>
            </button>
        </div>
    </div>

    <div class="search-popup">
        <div class="w-50 search-popup-content">
            <button class="search-popup-back-button">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                    <path d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l160 160c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L109.2 288 416 288c17.7 0 32-14.3 32-32s-14.3-32-32-32l-306.7 0L214.6 118.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-160 160z"/>
                </svg>                    
            </button>
            <form action="{{ route('search', ['query' => request()->input('search', '')]) }}" method="GET">
                <input type="text" name="search" placeholder="Rechercher un cocktail">
            </form>
        </div>
    </div>
    @yield('content')
</body>
</html>