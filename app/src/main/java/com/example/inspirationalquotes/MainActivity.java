package com.example.inspirationalquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private TextView mValue;
    private Button mButton;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValue = findViewById(R.id.tvValue);
        mButton = findViewById(R.id.loadButton);
        mImage = findViewById(R.id.ivImage);
        mImage.setImageResource(getResources().getIdentifier("chuck_norris", "drawable", "com.example.inspirationalquotes"));
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuote();
            }
        });

    }

    public void loadQuote(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.chucknorris.io").addConverterFactory(GsonConverterFactory.create()).build();
        QuoteService service = retrofit.create(QuoteService.class);
        Call<Quote> quoteCall = service.getQuotes();
        quoteCall.enqueue(new Callback<Quote>(){
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response){
                Log.d(TAG, "OnResponse: SUCCESS");
                mValue.setText(response.body().getValue());
            }
            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                Log.d(TAG, "onFailure: FAILURE");
            }
        });
        mButton.setText("Get a New Quote");
    }
}
