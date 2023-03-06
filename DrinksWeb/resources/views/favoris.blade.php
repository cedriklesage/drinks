<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="{{asset('css/style.css')}}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    <title>Drink's</title>
</head>
<body>

    <div class="d-flex">
        <div class="left-section p-10">
            <div class="nav-menu p-15">
                <div class="nav-menu-logo">Drink's</div>
                <div class="nav-menu-links">
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/alert.png')}}" alt=""><span>Accueil</span></a>
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/edit.png')}}" alt=""><span>Recettes</span></a>
                    <a href="{{route('favoris')}}" class="nav-menu-link p-10"><img src="{{asset('img/icons/alert.png')}}" alt=""><span>Favoris</span></a>
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/edit.png')}}" alt=""><span>Profil</span></a>
                </div>
                <div class="nav-menu-logout">Logout</div>
            </div>
        </div>
    
        <div class="right-section p-10">
            <div class="drink-popup-right p-25">
                <div>
                    <button class="popup-drink-back-button">X</button>
                    <div class="main-info">
                        <h1 class="popup-drink-name">Placeholder name</h1>
                        <p class="popup-drink-desc">Placeholder description</p>
                    </div>
                    <h2 class="m-b-25">Ingrédients</h2>
                    <div class="main-ingredients">
                    </div>
                </div>


                <a href="" class="btn start-drink">Commencer la recette</a>

            </div>
            @if($drinks != null)
                <div class="drink-grid">
                    @foreach($drinks as $drink)
                    <button href="" class="drink-card"
                    data-id="{{$drink->id}}" data-title="{{$drink->title}}"
                    data-description="{{$drink->description}}"data-image="{{$drink->image}}">
                        <img src="{{$drink->image}}" alt="">
                        <span class="drink-name">{{$drink->title}}</span>
                    </button>
                    @endforeach
                </div>
            @else
                <div class="middle-message">
                    <img src="{{asset('img/sad-monster.png')}}" alt="">
                    <h1 class="m-b-10">Aucun favoris</h1>
                    <p class="">Vous n'avez pas de goût, pour le moment...</p>
                </div>
            </div>
            @endif

        </div>
    </div>

    <script>

        //For each button with .drink-card class, add an event listener
        const drinkCards = document.querySelectorAll('.drink-card');
        //For each button, add an event listener
        drinkCards.forEach((drinkCard) => {
            drinkCard.addEventListener('click', (e) => {
                e.preventDefault();
                // Open the drink popup
                document.querySelector('.drink-popup-right').classList.add('active');
                document.querySelector('.drink-content').classList.add('active');

                document.querySelector('.nav-menu').classList.add('active');

                document.querySelector('.popup-drink-name').innerHTML = drinkCard.dataset.title;
                document.querySelector('.popup-drink-desc').innerHTML = drinkCard.dataset.description;
                document.querySelector('.start-drink').href = `/drink/${drinkCard.dataset.id}`;

                /* Delete every div in the main-ingredients div */
                document.querySelectorAll('.main-ingredients .ingredient').forEach(ingredient => {
                    ingredient.remove();
                })

                const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php';
                const data = new URLSearchParams();
                data.append('requete', 'getDrinkIngredients');
                data.append('id', drinkCard.dataset.id);

                fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: data
                })
                .then(response => response.json())
                .then(data => {
                    data.forEach(ingredient => {
                        const ingredientDiv = document.createElement('div');
                        ingredientDiv.classList.add('ingredient');
                        ingredientDiv.innerHTML = `
                            <img src="${ingredient.image}" alt="">
                            <span>${ingredient.nom}</span>
                        `;
                        document.querySelector('.main-ingredients').appendChild(ingredientDiv);
                    })
                })
                .catch(error => console.error(error));
            })
        })

        document.querySelector('.see-more-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.add('active');
            document.querySelector('.drink-content').classList.add('active');

            document.querySelector('.nav-menu').classList.add('active');

            document.querySelector('.popup-drink-name').innerHTML = document.querySelector('.see-more-button').dataset.title;
            document.querySelector('.popup-drink-desc').innerHTML = document.querySelector('.see-more-button').dataset.description;
            document.querySelector('.start-drink').href = "/drink/" + document.querySelector('.see-more-button').dataset.id;
            
            /* Delete every div in the main-ingredients div */
            document.querySelectorAll('.main-ingredients .ingredient').forEach(ingredient => {
                ingredient.remove();
            })

            const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php';
            const data = new URLSearchParams();
            data.append('requete', 'getDrinkIngredients');
            data.append('id', document.querySelector('.see-more-button').dataset.id);   

            fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
            })
            .then(response => response.json())
            .then(data => {
                data.forEach(ingredient => {
                    const ingredientDiv = document.createElement('div');
                    ingredientDiv.classList.add('ingredient');
                    ingredientDiv.innerHTML = `
                        <img src="${ingredient.image}" alt="">
                        <span>${ingredient.nom}</span>
                    `;
                    document.querySelector('.main-ingredients').appendChild(ingredientDiv);
                })
            })
            .catch(error => console.error(error));

        })

        // Close the drink popup
        document.querySelector('.popup-drink-back-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.remove('active');
            document.querySelector('.drink-content').classList.remove('active');
            
            document.querySelector('.nav-menu').classList.remove('active');
        })

        
        
        // Get the <body> element
        const body = document.querySelector('body');
      
        // Add the 'loading' class to the <body> element
        body.classList.add('loading');
        
        // Or, remove the 'loading' class once all assets (images, scripts, etc.) have finished loading
        window.addEventListener('load', () => {
            body.classList.remove('loading');
        });
        
        // Add the 'loading' class back to the <body> element when the user navigates to a different page
        window.addEventListener('beforeunload', () => {
            body.classList.add('loading');
        });

    </script>
</body>
</html>