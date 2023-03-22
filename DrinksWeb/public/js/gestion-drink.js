//For each button with .drink-card class, add an event listener
const drinkCards = document.querySelectorAll('.drink-card');
const loader = document.querySelector('.loader');
const antiLoader = document.querySelectorAll('.anti-loader');
//For each button, add an event listener
drinkCards.forEach((drinkCard) => {
    drinkCard.addEventListener('click', (e) => {
        e.preventDefault();

        
        loader.classList.add('active');
        antiLoader.forEach(anti => {
            anti.classList.add('active');
        })

        // Open the drink popup
        document.querySelector('.drink-popup').classList.add('active');
        document.querySelector('body').classList.add('no-scroll');

        document.querySelector('.popup-drink-name').innerHTML = drinkCard.dataset.title;
        document.querySelector('.popup-drink-name').dataset.id = drinkCard.dataset.id;
        document.querySelector('.popup-drink-desc').innerHTML = drinkCard.dataset.description;
        document.querySelector('.start-drink .btn').href = `/drink/${drinkCard.dataset.id}`;

        /* Delete every div in the main-ingredients div */
        document.querySelectorAll('.main-ingredients .ingredient').forEach(ingredient => {
            ingredient.remove();
        })

        document.querySelectorAll('.drink-categories .drink-category-popup').forEach(ingredient => {
            ingredient.remove();
        })


        // Get the drink ingredients
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
            loader.classList.remove('active');
            antiLoader.forEach(anti => {
                anti.classList.remove('active');
            })
        })
        .catch(error => console.error(error));

        // Get the drink categories
        const data2 = new URLSearchParams();
        data2.append('requete', 'getDrinkCategories');
        data2.append('id', drinkCard.dataset.id);
        fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data2
        })
        .then(response => response.json())
        .then(data2 => {
            data2.forEach(category => {
                const categoryDiv = document.createElement('div');
                categoryDiv.classList.add('drink-category-popup');
                categoryDiv.innerHTML = `
                    <div>${category.titre}</div>
                `;
                document.querySelector('.drink-categories').appendChild(categoryDiv);
            })
        })
        .catch(error => console.error(error));
    

    // Get the like status
    const heart_button = document.querySelector('.heart-button');
    const data3 = new URLSearchParams();
    data3.append('requete', 'checkLike');
    data3.append('idDrink', drinkCard.dataset.id);
    data3.append('idUser', document.querySelector('.heart-button').dataset.uid);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data3
        })
        .then(response => response.json())
        .then(data3 => {
            if(data3 == true) {
                document.querySelector('.heart-button svg').classList.add('active');
            }
            else {
                document.querySelector('.heart-button svg').classList.remove('active');
            }

        })
        .catch(error => console.error(error));
    })

})

// Close the drink popup
document.querySelector('.popup-drink-back-button').addEventListener('click', () => {
    document.querySelector('.drink-popup').classList.remove('active');
    document.querySelector('body').classList.remove('no-scroll');
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

document.querySelector('.heart-button').addEventListener('click', (e) => {
    e.preventDefault();
    const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php';
    const data = new URLSearchParams();
    data.append('requete', 'switchLike');
    data.append('idDrink', document.querySelector('.popup-drink-name').dataset.id);
    data.append('idUser', document.querySelector('.heart-button').dataset.uid);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {
            if(data == true) {
                document.querySelector('.heart-button svg').classList.add('active');
            }
            else {
                document.querySelector('.heart-button svg').classList.remove('active');
            }
        })
        .catch(error => console.error(error));
})
