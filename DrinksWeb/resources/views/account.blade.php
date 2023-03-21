@extends('layout.main')
@section('title', 'Mon compte - Drink\'s')
@section('content')

<div class="w-100 d-flex d-center">
    <div class="w-m-50 h-100 d-flex flex-col gap-25 p-25">

        <div class="account-section edit-name-section">
            <div class="d-flex j-between w-100">
                <p>Mon identité</p>
                <button class="edit-button edit-name-button">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" fill="white"><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"/></svg>
                </button>
            </div>
            <h2 class="name-text edit-text">{{$user->first_name}} {{$user->last_name}}</h2>
            <div class="w-100 d-flex j-between gap-25">
                <input class="first-name-input edit-input" type="text" name="first-name" id="first-name" value="{{$user->first_name}}" placeholder="Prénom">
                <input class="last-name-input edit-input" type="text" name="last-name" id="last-name" value="{{$user->last_name}}" placeholder="Nom de famille">
            </div>
            <div class="action-buttons">
                <button class="cancel cancel-name">Annuler</button>
                <button class="save save-name">Modifier</button>
            </div>
        </div>

        <div class="account-section edit-email-section">
            <div class="d-flex j-between w-100">
                <p>Mon courriel</p>
                <button class="edit-button edit-email-button">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" fill="white"><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"/></svg>
                </button>
            </div>
            <h2 class="email-text edit-text">{{$user->email}}</h2>
            <input class="email-input edit-input" type="text" name="courriel" id="courriel" value="{{$user->email}}" placeholder="Courriel">
            <div class="action-buttons">
                <button class="cancel cancel-email">Annuler</button>
                <button class="save save-email">Modifier</button>
            </div>
        </div>

        <div class="account-section edit-password-section">
            <div class="d-flex j-between w-100">
                <p>Mon mot de passe</p>
                <button class="edit-button edit-password-button">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" fill="white"><path d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"/></svg>
                </button>
            </div>
            <h2 class="password-text edit-text">••••••••</h2>
            <div class="w-100 d-flex j-between gap-25">
                <input class="password-input edit-input" type="password" name="courriel" id="courriel" value="" placeholder="Mot de passe actuel">
                <input class="new-password-input edit-input" type="password" name="courriel" id="courriel" value="" placeholder="Nouveau mot de passe">
            </div>
            <div class="action-buttons">
                <button class="cancel cancel-password">Annuler</button>
                <button class="save save-password">Modifier</button>
            </div>
        </div>
    </div>
</div>


<!-- Script User Name -->
<script>

    const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/users.php';
    const userId = {{$user_id}};
    const errorPopup = document.querySelector('.error-popup div');
    const errorPopupText = document.querySelector('.error-popup div p');

    /* EDIT NAME */
    const editNameSection = document.querySelector('.edit-name-section'); // Section
    const editNameButton = document.querySelector('.edit-name-button'); // Edit button
    const name = document.querySelector('.name-text'); // Placeholder text

    const firstNameInput = document.querySelector('.first-name-input'); // Input first name
    const lastNameInput = document.querySelector('.last-name-input'); // Input last name
    
    const cancel = document.querySelector('.cancel-name'); // Cancel button
    const save = document.querySelector('.save-name'); // Save button

    editNameButton.addEventListener('click', () => {
        editNameSection.classList.toggle('active');
        name.classList.toggle('active');
        firstNameInput.classList.toggle('active');
        lastNameInput.classList.toggle('active');
    });

    cancel.addEventListener('click', () => {
        editNameSection.classList.toggle('active');
        name.classList.toggle('active');
        firstNameInput.classList.toggle('active');
        lastNameInput.classList.toggle('active');
    });

    save.addEventListener('click', () => {
        const firstName = firstNameInput.value;
        const lastName = lastNameInput.value;

        if(firstName == '' || lastName == '')
        {
            errorPopupText.innerHTML = 'Veuillez remplir tous les champs';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
            return;
        }

        const data = new URLSearchParams();
        data.append('requete', 'changeUserName');
        data.append('id', userId);
        data.append('first_name', firstName);
        data.append('last_name', lastName);

        fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {
            if(data == true)
            {
                editNameSection.classList.toggle('active');
                name.classList.toggle('active');
                firstNameInput.classList.toggle('active');
                lastNameInput.classList.toggle('active');
                name.innerHTML = firstName + ' ' + lastName;
            }
            else{
                errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
                errorPopup.classList.toggle('active');
                setTimeout(() => {
                    errorPopup.classList.toggle('active');
                }, 3000);
            }
        })
        .catch(error => 
        {
            errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
        }
        );

    });
</script>

<!-- Script User Email -->
<script>

    /* EDIT EMAIL */
    const editEmailSection = document.querySelector('.edit-email-section'); // Section
    const editEmailButton = document.querySelector('.edit-email-button'); // Edit button
    const email = document.querySelector('.email-text'); // Placeholder text

    const emailInput = document.querySelector('.email-input'); // Input email
    
    const cancelEmail = document.querySelector('.cancel-email'); // Cancel button
    const saveEmail = document.querySelector('.save-email'); // Save button

    editEmailButton.addEventListener('click', () => {
        editEmailSection.classList.toggle('active');
        email.classList.toggle('active');
        emailInput.classList.toggle('active');
    });

    cancelEmail.addEventListener('click', () => {
        editEmailSection.classList.toggle('active');
        email.classList.toggle('active');
        emailInput.classList.toggle('active');
    });

    saveEmail.addEventListener('click', () => {
        const emailValue = emailInput.value;

        if(emailValue == '')
        {
            errorPopupText.innerHTML = 'Veuillez remplir tous les champs';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
            return;
        }

        if(!validateEmail(emailValue))
        {
            errorPopupText.innerHTML = 'Veuillez entrer une adresse courriel valide.';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
            return;
        }

        const data = new URLSearchParams();
        data.append('requete', 'changeUserEmail');
        data.append('id', userId);
        data.append('email', emailValue);

        fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {
            if(data == true)
            {
                editEmailSection.classList.toggle('active');
                email.classList.toggle('active');
                emailInput.classList.toggle('active');
                email.innerHTML = emailValue;

                errorPopupText.innerHTML = 'Votre email a été modifié avec succès.';
                errorPopup.classList.toggle('active');
                setTimeout(() => {
                    errorPopup.classList.toggle('active');
                }, 3000);
            }
            else{
                errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
                errorPopup.classList.toggle('active');
                setTimeout(() => {
                    errorPopup.classList.toggle('active');
                }, 3000);
            }
        })
        .catch(error => 
        {
            errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
        }
        );

    });

    //validate email
    function validateEmail(email) 
    {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }
    
</script>

<!-- Script User Password -->
<script>
    const editPasswordSection = document.querySelector('.edit-password-section'); // Section
    const editPasswordButton = document.querySelector('.edit-password-button'); // Edit button
    const password = document.querySelector('.password-text'); // Placeholder text

    const passwordInput = document.querySelector('.password-input'); // Input password
    const newPasswordInput = document.querySelector('.new-password-input'); // Input new password

    const cancelPassword = document.querySelector('.cancel-password'); // Cancel button
    const savePassword = document.querySelector('.save-password'); // Save button

    editPasswordButton.addEventListener('click', () => {
        editPasswordSection.classList.toggle('active');
        password.classList.toggle('active');
        passwordInput.classList.toggle('active');
        newPasswordInput.classList.toggle('active');
    });

    cancelPassword.addEventListener('click', () => {
        editPasswordSection.classList.toggle('active');
        password.classList.toggle('active');
        passwordInput.classList.toggle('active');
        newPasswordInput.classList.toggle('active');
    });

    savePassword.addEventListener('click', () => {
        const passwordValue = passwordInput.value;
        const newPasswordValue = newPasswordInput.value;

        if(passwordValue == '' || newPasswordValue == '')
        {
            errorPopupText.innerHTML = 'Veuillez remplir tous les champs';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
            return;
        }

        const data = new URLSearchParams();
        data.append('requete', 'checkPassword');
        data.append('id', userId);
        data.append('password', passwordValue);

        fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {
            if(data == true) // Check if current password is correct
            {
                console.log('Password is good, trying to change it');
                const data2 = new URLSearchParams();
                data2.append('requete', 'changePassword');
                data2.append('id', userId);
                data2.append('password', newPasswordValue);

                fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: data2
                })
                .then(response => response.json())
                .then(data => {
                    if(data == true)
                    {
                        editPasswordSection.classList.toggle('active');
                        password.classList.toggle('active');
                        passwordInput.classList.toggle('active');
                        newPasswordInput.classList.toggle('active');

                        errorPopupText.innerHTML = 'Le mot de passe a bien été modifié.';
                        errorPopup.classList.toggle('active');
                        setTimeout(() => {
                            errorPopup.classList.toggle('active');
                        }, 3000);
                    }
                    else{
                        errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
                        errorPopup.classList.toggle('active');
                        setTimeout(() => {
                            errorPopup.classList.toggle('active');
                        }, 3000);
                    }
                })
            }
            else{
                errorPopupText.innerHTML = 'Le mot de passe actuel de correspond pas.';
                errorPopup.classList.toggle('active');
                setTimeout(() => {
                    errorPopup.classList.toggle('active');
                }, 3000);
            }
        })
        .catch(error => 
        {
            errorPopupText.innerHTML = 'Une erreur est survenue, veuillez réessayer.';
            errorPopup.classList.toggle('active');
            setTimeout(() => {
                errorPopup.classList.toggle('active');
            }, 3000);
        }
        );

    });
</script>

@endsection