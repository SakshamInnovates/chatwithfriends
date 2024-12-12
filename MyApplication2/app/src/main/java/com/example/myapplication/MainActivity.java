package com.example.myapplication;

import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class MainActivity extends AppCompatActivity {

    public static class ChatActivity extends AppCompatActivity {
        private EditText messageInput;




        

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            messageInput = findViewById(R.id.message_input);
            Button sendButton = findViewById(R.id.send_button);
            ListView chatListView = findViewById(R.id.chat_list_view);

            ArrayList<String> chatMessages = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
            chatListView.setAdapter(adapter);

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = messageInput.getText().toString();
                    sendMessage(message);
                    messageInput.setText("");
                }
            });
        }

        private void sendMessage(String message) {
            // Implement sending message logic here
            // For example, you could use OkHttp to send a POST request to a server
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("message", message)
                    .build();
            Request request = new Request.Builder()
                    .url("https://example.com/send_message")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                public <Response> void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        // Handle response from server
                    } else {
                        System.out.println("invali data");
                    }
                }
            });
        }
    }
}