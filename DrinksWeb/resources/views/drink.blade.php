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
    <div class="p-25">
        <a href="/" class="x-button">X</a>
        <div class="m-t-25">
            <p class="drink-step-header">En attente de connexion avec la balance.</p>
            <p class="m-t-25">Veuillez vous assurer que la balance est allumé et connecté sur le même réseau.</p>
        </div>

    </div>
    <script>
        const url = 'http://cours.cegep3r.info/H2023/420606RI/GR06/drinks.php';
        const data = new URLSearchParams();
        data.append('requete', 'loadSteps');
        data.append('idDrink', {{$id}});   

        fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
        })
        .then(response => response.json())
        .then(data => {let steps = data})
        .catch(error => console.error(error));

        console.log(steps);
    </script>
</body>
</html>