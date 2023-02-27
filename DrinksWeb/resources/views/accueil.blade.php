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

    <div class="drink-popup">
        <div class="drink-section">
            <img src="" alt="">
            <div class="drink-info d-flex j-center flex-col">
                <h3 class="drink-name">
                    Placeholder name
                </h3>
                <p>Placeholder description</p>
                <a href="" class="start-drink">Commencer la recette</a>
            </div>
        </div>
    </div>

    <div class="d-flex">
        <div class="left-section p-10">
            <div class="nav-menu p-15">
                <div class="nav-menu-logo">Drink's</div>
                <div class="nav-menu-links">
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/alert.png')}}" alt=""><span>Accueil</span></a>
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/edit.png')}}" alt=""><span>Recettes</span></a>
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/alert.png')}}" alt=""><span>Favoris</span></a>
                    <a href="" class="nav-menu-link p-10"><img src="{{asset('img/icons/edit.png')}}" alt=""><span>Profil</span></a>
                </div>
                <div class="nav-menu-logout">Logout</div>
            </div>
        </div>
    
        <div class="right-section p-10">

            <div class="drink-popup-right p-25">
                <button class="popup-drink-back-button">X</button>
                <div class="main-info">
                    <h1 class="popup-drink-name">Placeholder name</h1>
                    <p class="popup-drink-desc">Placeholder description</p>
                </div>
                <div class="main-ingredients">
                    <div class="ingredient">
                        <img src="{{asset('img/icons/edit.png')}}" alt="">
                        <span>Placeholder ingredient</span>
                    </div>
                    <div class="ingredient">
                        <img src="{{asset('img/icons/edit.png')}}" alt="">
                        <span>Placeholder ingredient</span>
                    </div>
                    <div class="ingredient">
                        <img src="{{asset('img/icons/edit.png')}}" alt="">
                        <span>Placeholder ingredient</span>
                    </div>
                </div>

            </div>

            <div class="drink-content">
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

                document.querySelector('.nav-menu').classList.add('active');

                document.querySelector('.popup-drink-name').innerHTML = drinkCard.dataset.title;
                document.querySelector('.popup-drink-desc').innerHTML = drinkCard.dataset.description;
            })
        })

        document.querySelector('.see-more-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.add('active');
            document.querySelector('.drink-content').classList.add('active');

            document.querySelector('.nav-menu').classList.add('active');

            document.querySelector('.popup-drink-name').innerHTML = document.querySelector('.see-more-button').dataset.title;
            document.querySelector('.popup-drink-desc').innerHTML = document.querySelector('.see-more-button').dataset.description;
        })

        // Close the drink popup
        document.querySelector('.popup-drink-back-button').addEventListener('click', () => {
            document.querySelector('.drink-popup-right').classList.remove('active');
            document.querySelector('.drink-content').classList.remove('active');
            
            document.querySelector('.nav-menu').classList.remove('active');
        })


        const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php?id=' + encodeURIComponent('2');

        fetch(url)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));

        //console.log response
        console.log(response);

    </script>
</body>
</html>