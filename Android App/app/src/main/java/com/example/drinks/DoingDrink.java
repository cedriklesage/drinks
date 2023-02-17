package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoingDrink extends AppCompatActivity {

    public static ProgressBar bgProgressBar;
    ImageView img;
    ImageButton cancelBtn;
    static TextView stepCount;
    static TextView stepTitle;
    static TextView stepDesc;
    Button startBtn;
    static Button nextBtn;

    public static String broker = "172.16.206.130";
    public static String port = "1883";
    public static String clientId = "Android";
    public static String topic = "Drinks";


    InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
    private static List<Etape> etapes;
    private static double startWeight = 0, targetWeight = 0;
    private static double currentWeight = 0;
    private static int currentStep = 0;
    private static boolean waitForWeight = false;

    private static Handler handler = new Handler(Looper.getMainLooper());

    Mqtt3AsyncClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_drink);

        bgProgressBar = findViewById(R.id.bgProgress);
        img = findViewById(R.id.imgIngredient);
        cancelBtn = findViewById(R.id.cancelBtn);
        stepCount = findViewById(R.id.stepCount);
        stepTitle = findViewById(R.id.stepTitle);
        stepDesc = findViewById(R.id.stepDesc);
        startBtn = findViewById(R.id.startButton);

        cancelBtn.setOnClickListener(v -> cancel());

        connection(); // Connect to MQTT broker
        loadSteps(); // Load steps from server

        // Set up the first step (place cup)
        stepCount.setText("0 / 0");
        stepTitle.setText("Veuillez placer le verre sur la balance");
        stepDesc.setText("Placez votre verre sur la balance pour faire la calibration. Appuyez sur le bouton pour commencer quand vous êtes prêt.");
        bgProgressBar.setProgress(0);
        bgProgressBar.setMax(100);
        nextBtn = findViewById(R.id.nextButton);
        nextBtn.setVisibility(Button.GONE);
        startBtn.setOnClickListener(v -> {
            startBtn.setVisibility(Button.GONE);
            startWeight = currentWeight;
            bgProgressBar.setProgress(0);
            currentStep = 0;
            nextStep(currentStep);

        });

        nextBtn.setOnClickListener(v -> {
            if (currentStep < etapes.size()) {
                nextStep(currentStep + 1);
            }
        });

    }


    public void connection() {
        client = MqttClient.builder()
                .useMqttVersion3()
                .serverHost(broker)
                .serverPort(Integer.parseInt(port))
                .identifier(clientId)
                .buildAsync();

        client.connect();

        client.subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    // handle received message
                    onMessageReceived(publish);
                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    // handle subscription result or error
                    if (throwable != null) {
                        throwable.printStackTrace();
                    } else {
                        System.out.println("Subscription result: " + subAck);
                    }
                });
    }
    public void cancel()
    {
        client.disconnect();
        finish();

    }

    private static void onMessageReceived(Mqtt3Publish message) {

        String payload = new String(message.getPayloadAsBytes());

        if(waitForWeight)
        {
            try{
                currentWeight = Double.parseDouble(payload);
                if(currentWeight < targetWeight)
                {
                    bgProgressBar.setProgress((int) (currentWeight - startWeight));
                }
                else
                {
                    nextStep(currentStep + 1);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void loadSteps(){
        Call<List<Etape>> call = serveur.loadSteps("loadSteps", getIntent().getIntExtra("id", 0));

        call.enqueue(new Callback<List<Etape>>() {
            @Override
            public void onResponse(Call<List<Etape>> call, Response<List<Etape>> response) {
                etapes = (List<Etape>) response.body();
            }

            @Override
            public void onFailure(Call<List<Etape>> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    private static void nextStep(int step)
    {
        System.out.println("Next step");
        if(step < etapes.size())
        {
            currentStep = step;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    stepCount.setText((step + 1) + " / " + etapes.size());
                    stepTitle.setText(etapes.get(step).getTitre());
                    stepDesc.setText(etapes.get(step).getDescription());
                    bgProgressBar.setProgress(0);
                    bgProgressBar.setMax((int) etapes.get(currentStep).getQuantite() + 1);
                    targetWeight = etapes.get(step).getQuantite() + currentWeight;
                    startWeight = currentWeight;
                }
            });




            if(etapes.get(step).getType().equals("verse"))
            {
                waitForWeight = true;
                nextBtn.setVisibility(Button.GONE);
            }
            else
            {
                waitForWeight = false;
                nextBtn.setVisibility(Button.VISIBLE);
            }
        }
        else
        {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    stepCount.setText("Terminé");
                    stepTitle.setText("Votre boisson est prête !");
                    stepDesc.setText("Vous pouvez la déguster maintenant.");
                    bgProgressBar.setProgress(0);
                    nextBtn.setVisibility(Button.GONE);
                }
            });
        }


    }

}