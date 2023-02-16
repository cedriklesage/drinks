package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.squareup.picasso.Picasso;

public class DoingDrink extends AppCompatActivity {

    public static ProgressBar bgProgressBar;

    ImageView img;

    ImageButton cancelBtn;

    public static String broker = "172.16.206.130";
    public static String port = "1883";
    public static String clientId = "Android";
    public static String topic = "Drinks";

    public static final String BROKER = "172.16.206.130:1883";
    public static final String CLIENT_ID = "Android";
    public static final String TOPIC = "Drinks";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_drink);

        bgProgressBar = findViewById(R.id.bgProgress);
        img = findViewById(R.id.imgIngredient);
        cancelBtn = findViewById(R.id.cancelBtn);
        Picasso.get().load("http://cours.cegep3r.info/H2023/420606RI/GR06/drinks_images/vodka.png").into(img);



        cancelBtn.setOnClickListener(v -> cancel());

        connection();



    }


    public void connection() {
        Mqtt3AsyncClient client = MqttClient.builder()
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
        finish();
    }

    private static void onMessageReceived(Mqtt3Publish message) {
        String payload = new String(message.getPayloadAsBytes());
        // Round up the payload
        int progress = (int) Math.ceil(Double.parseDouble(payload) * 100);
        bgProgressBar.setProgress(progress);
        System.out.println("Message received: " + progress);
    }

}