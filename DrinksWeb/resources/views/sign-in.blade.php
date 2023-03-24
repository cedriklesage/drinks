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
        <a href="/"><img class="header-logo" src="{{asset('img/drink-white-logo.png')}}" alt=""></a>
    </header>
    <div class="w-100 h-100 d-flex d-center" style="position: absolute;top: 0;left: 0;">
        <div class="w-m-30 p-25 trans-1">
            <h1 class="m-b-25">Connexion</h1>
            <form action="{{route('sign-in-attempt')}}" method="POST">
                @csrf
                <input type="text" placeholder="Adresse courriel" id="email" name="email" required>
                <input type="password" placeholder="Mot de passe" id="password" name="password" required>
                <div class="w-100 d-flex gap-25">
                    <a href="{{route('sign-up')}}" class="sub-button btn login-btn w-100" style="width: 100% !important;">Créer un compte</a>
                    <button type="submit" class="main-button btn sign-in" style="width: 100% !important;">Se connecter</button>
                </div>
            </form>
        </div>
    </div>
    <div class="error-popup">
        <div class="error">
            <p>Une erreur s'est produite. Veuillez réessayer.</p>
        </div>
    </div>
</body>

<script>
    const errorPopup = document.querySelector('.error-popup div');
    const errorPopupText = document.querySelector('.error-popup div p');
    
    @if(session('error'))
        errorPopupText.innerHTML = 'Les informations de connexion sont incorrectes.';
        errorPopup.classList.toggle('active');
        setTimeout(() => {
            errorPopup.classList.toggle('active');
        }, 3000);
    @endif

</script>
</html>