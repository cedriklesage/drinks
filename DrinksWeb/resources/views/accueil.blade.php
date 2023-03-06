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

    <header class="header w-50 p-25">
        <a href=""><img class="header-logo" src="{{asset('img/drink-white-logo.png')}}" alt=""></a>
    </header>
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
    <div class="w-100 p-10">
        <div class="drink-content">
            <!--
            <div class="top-banner-drink">
                <img src="{{$dailyDrink->image}}" alt="">
                <div class="drink-info d-flex j-center flex-col">
                    <p class="daily-drink">Drink du jour</p>
                    <h3 class="drink-name">
                        {{$dailyDrink->title}}
                    </h3>
                    <p>{{$dailyDrink->description}}</p>
                    <button class="see-more-button"
                    data-id="{{$dailyDrink->id}}" data-title="{{$dailyDrink->title}}"
                    data-description="{{$dailyDrink->description}}" data-image="{{$dailyDrink->image}}">Voir la recette</button>
                </div>
            </div>
            -->

            <div class="drink-grid">
                @foreach($drinks as $drink)
                <button href="" class="drink-card"
                data-id="{{$drink->id}}" data-title="{{$drink->title}}"
                data-description="{{$drink->description}}"data-image="{{$drink->image}}">
                    <div class="drink-categories">
                        <div class="drink-category">Martini</div>
                    </div>
                    <img src="{{$drink->image}}" alt="">
                    <span class="drink-name">{{$drink->title}}</span>
                    <div class="see-drink">Voir plus
                        <svg viewBox="0 0 22 22" stroke-width="1" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                            <line x1="5" y1="12" x2="19" y2="12"></line>
                            <line x1="15" y1="16" x2="19" y2="12"></line>
                            <line x1="15" y1="8" x2="19" y2="12"></line>
                        </svg>
                    </div>
                </button>
                @endforeach
            </div>
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

                document.querySelector('.popup-drink-name').innerHTML = drinkCard.dataset.title;
                document.querySelector('.popup-drink-desc').innerHTML = drinkCard.dataset.description;
                document.querySelector('.start-drink').href = `/drink/${drinkCard.dataset.id}`;

                /* Deactivate scroll */
                document.querySelector('body').classList.add('no-scroll');


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

        /*
        document.querySelector('.see-more-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.add('active');
            document.querySelector('.drink-content').classList.add('active');

            document.querySelector('.popup-drink-name').innerHTML = document.querySelector('.see-more-button').dataset.title;
            document.querySelector('.popup-drink-desc').innerHTML = document.querySelector('.see-more-button').dataset.description;
            document.querySelector('.start-drink').href = "/drink/" + document.querySelector('.see-more-button').dataset.id;
            
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

        })*/

        // Close the drink popup
        document.querySelector('.popup-drink-back-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.remove('active');
            document.querySelector('.drink-content').classList.remove('active');
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