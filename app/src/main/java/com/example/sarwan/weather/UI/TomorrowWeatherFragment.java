package com.example.sarwan.weather.UI;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sarwan.weather.Constants.constant;
import com.example.sarwan.weather.R;
import com.example.sarwan.weather.model.WeatherData;
import com.example.sarwan.weather.model.WeatherDetailedData;
import com.example.sarwan.weather.network.APIClient;
import com.example.sarwan.weather.network.ServicesDownloader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sarwan.weather.Constants.constant.API_KEY;

public class TomorrowWeatherFragment extends Fragment {

    private ScrollView scrollView;
    public  View mainView;
    public WeatherDetailedData data;
    public double TO_CELSIUS=273.15;
    private String status,max,min,humidity_value,wind_value;
    ImageView imageView;
    List<String> match = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        scrollView = (ScrollView)  rootView.findViewById(R.id.scrollView);
        scrollView.setBackground(getResources().getDrawable(R.drawable.bluebg));
        mainView = rootView;
        GetDetailedData();
        return rootView;
    }


    public TomorrowWeatherFragment() {
    }

    public void GetDetailedData() {

        ServicesDownloader servicesDownloader = APIClient.getClient()
                .create(ServicesDownloader.class);

        Call<WeatherDetailedData> listCall = servicesDownloader.
                getdetailedWeatherData(constant.latitude,constant.longitude,API_KEY);

        listCall.enqueue(new Callback<WeatherDetailedData>() {
            @Override
            public void onResponse(Call<WeatherDetailedData> call, Response<WeatherDetailedData> response) {
                calculate(response.body());
                SettingupUI();
            }

            @Override
            public void onFailure(Call<WeatherDetailedData> call, Throwable t) {
                Log.d("Failed",t.getMessage());
            }
        });
    }


    private void calculate(WeatherDetailedData data) {

        List<String> finalList = new ArrayList<>();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DATE,1);
        gregorianCalendar.add(Calendar.MONTH,1);
        String nextDate = (String.valueOf(gregorianCalendar.get(Calendar.YEAR) + "-0"+
                gregorianCalendar.get(Calendar.MONTH) + "-"+
                gregorianCalendar.get(Calendar.DATE)));
        int value = 0;
        for(int i=0;i<8;i++){
            if(value<=9){
                String toMatch = nextDate + " 0" + value+":00:00";
                match.add(toMatch);
            }
            else {
                String toMatch = nextDate + " "+value+":00:00";
                match.add(toMatch);
            }
            value+=3;

            Log.d("match", match.get(i));
        }

        for(int j=0;j<data.getList().size();j++){
        for(int i=0;i<8;i++){
            if(data.getList().get(j).getDt_txt().equalsIgnoreCase(match.get(i))){
                status = data.getList().get(j).getWeather().get(0).getMain();
                max = (String.valueOf(Math.round(data.getList().get(i).getMain().getTemp_max() - TO_CELSIUS)));
                min = (String.valueOf(Math.round(data.getList().get(i).getMain().getTemp_min() - TO_CELSIUS)-2));
                humidity_value = String.valueOf(data.getList().get(0).getMain().getHumidity() + "%");
                wind_value = (String.valueOf((Math.round(data.getList().get(0).getWind().
                        getSpeed().floatValue()) * 3.6)*1.0d) +" km/hr"+ "\n"+
                        String.valueOf(data.getList().get(0).getMain().getPressure()) + " mBar");
            }
        }
        }

    }

    public int setImage(String value){
        String uri = "@drawable/"+value.toLowerCase();  // where myresource (without the extension) is the file

        int resource = getResources().getIdentifier(uri,
                "drawable", getContext().getPackageName());
        return resource;
    }

    public void SettingupUI()
    {

        TextView current_date = (TextView) mainView.findViewById(R.id.current_date_tom);
        TextView tempstatus = (TextView) mainView.findViewById(R.id.current_temp_en_tom);
        TextView dayandnight = (TextView) mainView.findViewById(R.id.dayandnight_tom);

        TextView humidity = (TextView) mainView.findViewById(R.id.Humidityvalue_tom);
        TextView wind = (TextView) mainView.findViewById(R.id.windstatus1_tom);

        current_date.setText(calculateDate());
        tempstatus.setText(status);

        int resId = setImage(String.valueOf(tempstatus.getText()));
        imageView = (ImageView) mainView.findViewById(R.id.imageView_tom);

        Drawable res = getResources().getDrawable(resId);
        imageView.setImageDrawable(res);

        humidity.setText(humidity_value);
        wind.setText(wind_value);
        dayandnight.setText("Day : " + max + "C " + "Night : "+ min + " C");

        Log.d("hum",humidity_value);
        Log.d("wind",wind_value);
        Log.d("day and night",max + " " + min);
        Log.d("date",String.valueOf(current_date));
    }
    public String calculateDate(){

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE,1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE yyyy-MMM-dd");
        Date tomorrow = calendar.getTime();
        String strDate =  dateFormat.format(tomorrow);
        return String.valueOf(strDate);
    }


}
