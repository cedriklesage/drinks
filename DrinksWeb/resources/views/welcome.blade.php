<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="{{asset('css/login.css')}}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800;900&display=swap" rel="stylesheet">
    <title>Bienvenue sur Drink's</title>
</head>
<body>
    <header class="header w-50 p-25">
        <a href=""><img class="header-logo" src="{{asset('img/drink-white-logo.png')}}" alt=""></a>
    </header>
    <div class="w-100 h-100 d-flex p-25">
        <div class="left-section w-50 h-100 p-50 d-flex flex-col j-center">
            <h1 class="m-b-25">Des cocktails <span>créatifs</span> et <span>délicieux</span> à portée de clic, pour des soirées <span>inoubliables!</span></h1>
            <p class="sub-header-text">Découvrez de nouvelles saveurs et impressionnez vos invités avec notre sélection de recettes de cocktails. Inscrivez-vous maintenant.</p>
            <div class="d-flex w-70 m-t-25 gap-25 buttons">
                <a href="{{route('sign-up')}}" class="main-button btn sign-in">Commencer</a>
                <a href="{{route('sign-in')}}" class="sub-button btn login-btn">J'ai déjà mon compte</a>
            </div>
        </div>
        <div class="right-section w-50 h-100">
            <img src="{{asset('img/stock/cocktails-01.jpg')}}" alt="">
        </div>
    </div>
    <div class="circles">
        <div class="circle circle-1" style="--x: 0.5; --y: 0.1;"></div>
        <div class="circle" style="--x: 1; --y: 0.5;"></div>
        <div class="circle" style="--x: -0.1; --y: 1;"></div>
    </div>


</body>
</html>