//For each button with .drink-card class, add an event listener
const drinkCards = document.querySelectorAll('.drink-card');
const loader = document.querySelector('.loader');
const antiLoader = document.querySelectorAll('.anti-loader');
const errorPopup = document.querySelector('.error-popup div');
const errorPopupText = document.querySelector('.error-popup div p');
//For each button, add an event listener

drinkCards.forEach(addEventCard);

function addEventCard(drinkCard) {
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
                if(ingredient.quantite != 0) {
                ingredientDiv.innerHTML = `
                    <img src="${ingredient.image}" alt="">
                    <div style="display:flex; flex-direction:column;" >
                        <span>${ingredient.nom}</span>
                        <span style="font-size: 14px;">${ingredient.quantite}ml</span>
                    </div>
                `;
                }
                else {
                    ingredientDiv.innerHTML = `
                    <img src="${ingredient.image}" alt="">
                    <div style="display:flex; flex-direction:column;" >
                        <span>${ingredient.nom}</span>
                    </div>
                `;
                }
                document.querySelector('.main-ingredients').appendChild(ingredientDiv);
            })
            loader.classList.remove('active');
            antiLoader.forEach(anti => {
                anti.classList.remove('active');
            })
        })
        .catch(error => 
            {
                loader.classList.remove('active');
                antiLoader.forEach(anti => {
                    anti.classList.remove('active');
                })
                errorPopupText.innerHTML = 'Une erreur est survenue. Veuillez réessayer.';
                errorPopup.classList.toggle('active');
                setTimeout(() => {
                    errorPopup.classList.toggle('active');
                }, 3000);
            }
        );

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
}

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
});



/* LOAD MORE BUTTON */
const loadMoreButton = document.querySelector('.load-more-btn');
const loadMoreButtonText = document.querySelector('.load-more-btn span');
const loadMoreButtonIcon = document.querySelector('.load-more-btn div');
const drinkGrid = document.querySelector('.drink-grid');
var offset = 24;
var numberLoad = 0;

loadMoreButton.addEventListener('click', (e) => {
    e.preventDefault();
    if(numberLoad != 2) {
    const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php';
    const data = new URLSearchParams();
    data.append('requete', 'loadMoreDrinks');
    data.append('offset', offset);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {
            data.forEach(drink => {
                // Create the button element
                const button = document.createElement('button');
                button.classList.add('drink-card');
                button.dataset.id = drink.id;
                button.dataset.title = drink.title;
                button.dataset.description = drink.description;
                button.dataset.image = drink.image;
            
                // Create the drink categories element
                const drinkCategories = document.createElement('div');
                drinkCategories.classList.add('drink-categories');
                let count = 0;
                categories.forEach(category => {
                    if (category.recette_id == drink.id) {
                        if(count < 2)
                        {
                            const drinkCategory = document.createElement('span');
                            drinkCategory.classList.add('drink-category');
                            drinkCategory.textContent = category.title;
                            drinkCategories.appendChild(drinkCategory);
                            count++;
                        }
                    }
                });
                button.appendChild(drinkCategories);
            
                // Create the drink image element
                const image = document.createElement('img');
                image.src = drink.image;
                image.alt = '';
            
                // Append the drink image element to the button element
                button.appendChild(image);
            
                // Create the drink name element
                const name = document.createElement('span');
                name.classList.add('drink-name');
                name.textContent = drink.title;
            
                // Append the drink name element to the button element
                button.appendChild(name);
            
                // Create the see drink element
                const seeDrink = document.createElement('div');
                seeDrink.classList.add('see-drink');
            
                //inner html of svg
                seeDrink.innerHTML = 
                `<svg viewBox="0 0 22 22" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                    <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                    <line x1="0" y1="12" x2="19" y2="12"></line>
                    <line x1="15" y1="16" x2="19" y2="12"></line>
                    <line x1="15" y1="8" x2="19" y2="12"></line>
                </svg>`;
                button.appendChild(seeDrink);

                const cardCircle = document.createElement('div');
                cardCircle.classList.add('card-circle');
                cardCircle.style.setProperty('--color', drink.main_color);
                button.appendChild(cardCircle);

                drinkGrid.appendChild(button);
                addEventCard(button);
            })

        })
        .catch(error => console.error(error));

    offset += 24;
    loadMoreButtonText.classList.remove('active');
    loadMoreButtonIcon.classList.remove('active');
    numberLoad += 1;
    if(numberLoad == 2)
    {
        loadMoreButton.style.display = 'none';
    }
}
});