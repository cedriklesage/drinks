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
    <div class="p-25 making-drink">
        <div class="step-info">
            <a href="/" class="x-button">X</a>
            <div class="m-t-25 w-50">
                <p class="drink-step-title">En attente de connexion avec la balance.</p>
                <p class="m-t-25 drink-step-description">Veuillez vous assurer que la balance est allumé et connecté sur le même réseau.</p>
            </div>
        </div>
        <button class="next-step-button btn">Commencer la recette</button>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script>

        var mqtt;
        var reconnectTimeout = 2000;
        var host = "172.16.206.130";
        var port = 9001;

        var stepCount = -1;
        var steps = <?php echo json_encode($steps)?>;
        
        var title = document.querySelector('.drink-step-title');
        var description = document.querySelector('.drink-step-description');
        var background = document.querySelector('.making-drink');

        var nextStepButton = document.querySelector('.next-step-button');
        nextStepButton.addEventListener('click', nextStep);
        nextStepButton.style.visibility = "hidden";

        var currentWeight = 0;
        var startWeight = 0;
        var targetWeight = 0;

        var waitForWeight = false;
        var waitForShake = false;

        window.onload = function() {
            MQTTconnect();
        }

        function MQTTconnect() {
            var x=Math.floor(Math.random() * 10000); 
            var cname="orderform-"+x;
            mqtt = new Paho.MQTT.Client(host,port,cname);
            var options = {
                timeout: 3,
                onSuccess: onConnect,
                onFailure: onFailure,
            };
            mqtt.onMessageArrived = onDataReceived;
            mqtt.connect(options); //connect
        }

        function onFailure(message) {
            console.log("Connection Attempt to Host "+host+"Failed");
            setTimeout(MQTTconnect, reconnectTimeout);
        }

        function onConnect() {
            console.log("Connected to "+ host +" "+ port);
            mqtt.subscribe("Drinks");
            title.innerHTML = "Veuillez placer votre verre sur la balance.";
            description.innerHTML = "Placez votre verre sur la balance pour faire la calibration. Appuyez sur le bouton pour commencer la recette quand vous êtes prêt.";
            nextStepButton.innerHTML = "Étape suivante";
            nextStepButton.style.visibility = "visible";
            
        }

        function onDataReceived(message) {
            currentWeight = message.payloadString;
            console.log("current weight: " + currentWeight);

            if(waitForWeight)
            {
                background.style.background = "linear-gradient(0deg, rgba(33, 32, 42,1) 0%, rgba(33, 32, 42,1) "+((currentWeight - startWeight) / (targetWeight - startWeight) * 100)+"%, rgba(255,255,255,0) "+((currentWeight - startWeight) / (targetWeight - startWeight) * 100)+"%)";

                if(currentWeight >= targetWeight)
                {
                    waitForWeight = false;
                    nextStep();
                }
            }
            if(waitForShake)
            {
                if(currentWeight <= 2)
                {
                    shakeDrink();
                }
            }
        }

        function nextStep()
        {
            stepCount++;
            if(stepCount == steps.length)
            {
                title.innerHTML = "Votre boisson est prête !";
                description.innerHTML = "Vous pouvez la récupérer sur la balance.";
                nextStepButton.style.visibility = "hidden";
                return;
            }
            else
            {
                startWeight = currentWeight;
                
                targetWeight = parseInt(startWeight) + parseInt(steps[stepCount].quantite);
                console.log("target is: " + targetWeight)

                title.innerHTML = "Étape "+(stepCount+1)+" : "+steps[stepCount].titre;
                description.innerHTML = steps[stepCount].description;
                nextStepButton.style.visibility = "hidden";
                background.style.background = "linear-gradient(0deg, rgba(33, 32, 42,1) 0%, rgba(33, 32, 42,1) 0%, rgba(255,255,255,0) 0%)";

                if(steps[stepCount].stepType == "glace")
                {
                    waitForWeight = false;
                    nextStepButton.style.visibility = "visible";
                }
                else if(steps[stepCount].stepType == "verse")
                {
                    waitForWeight = true;
                }
                else if(steps[stepCount].stepType == "shake")
                {
                    waitForShake = true;
                }

            }
        }

        function shakeDrink()
        {
            waitForShake = false;
            var i = 0;
            var interval = setInterval(function(){
                i++;
                background.style.background = "linear-gradient(0deg, rgba(33, 32, 42,1) 0%, rgba(33, 32, 42,1) "+(i * 10 / steps[stepCount].temps)+"%, rgba(255,255,255,0) "+(i * 10 / steps[stepCount].temps)+"%)";
                
                if(i == steps[stepCount].temps * 10)
                {
                    clearInterval(interval);
                    nextStep();
                }
            }, 100);
        }

    </script>
</body>
</html>