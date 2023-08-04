package com.example.myweathercast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.myweathercast.Model.Main;
import com.example.myweathercast.Model.MausamData;
import com.example.myweathercast.Model.Weather;
import com.example.myweathercast.ServiceClass.ApiInterface;
import com.example.myweathercast.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
public static String BASE_URL="https://api.openweathermap.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM YYYY");
        String curentdate=simpleDateFormat.format(new Date());
        binding.currentDate.setText(curentdate);
        binding.currentDate.setText(curentdate);

        //by default city
        searchCityWeather("Ranchi");

        //click on search icon
        binding.searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.enterCity.getText().toString())){
                    binding.enterCity.setError("Enter cityname");
                    return;
                }

                    String serch_city=binding.enterCity.getText().toString();
                    searchCityWeather(serch_city);

            }


        });


    }
    private void searchCityWeather(String serch_city) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api=retrofit.create(ApiInterface.class);
        //Call<MausamData> call = api.getData("ranchi", "1c3f82da9806ad7633eab89862e6e54b","matric");

        Call<MausamData> call=api.getData(serch_city,"1c3f82da9806ad7633eab89862e6e54b","matric");
        call.enqueue(new Callback<MausamData>() {
            @Override
            public void onResponse(Call<MausamData> call, Response<MausamData> response) {

                if (response.isSuccessful()) {
                    MausamData data = response.body();
                    Main main = data.getMain();
                    binding.currentDegree.setText(String.valueOf(main.getTemp())+"\u2103");
                    binding.minTemperature.setText(String.valueOf(main.getTempMin()));
                    binding.maxTemperature.setText(String.valueOf(main.getTempMax()));
                    binding.currentCity.setText(data.getName());
                    binding.humidity.setText(String.valueOf(main.getHumidity()));
                    binding.pressure.setText(String.valueOf(main.getPressure()));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MausamData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



