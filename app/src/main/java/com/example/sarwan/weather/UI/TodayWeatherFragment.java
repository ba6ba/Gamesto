package com.example.sarwan.weather.UI;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sarwan.weather.Constants.constant;
import com.example.sarwan.weather.MainActivity;
import com.example.sarwan.weather.R;
import com.example.sarwan.weather.model.WeatherData;
import com.example.sarwan.weather.network.APIClient;
import com.example.sarwan.weather.network.ServicesDownloader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWeatherFragment extends Fragment {

    private  ScrollView scrollview;
    public  View mainView;
    public WeatherData data;
    public double TO_CELSIUS=273.15;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        scrollview = (ScrollView)  rootView.findViewById(R.id.scrollView);
        scrollview.setBackground(getResources().getDrawable(R.drawable.bluebg));
        mainView = rootView;
        GetData();
        return rootView;
    }

    public TodayWeatherFragment() {
    }

    public int setImage(String value){
        String uri = "@drawable/"+value;  // where myresource (without the extension) is the file

        int resource = getResources().getIdentifier(uri,
                "drawable", getContext().getPackageName());
         return resource;

    }

    public void SettingupUI()
    {

        TextView temperature = (TextView) mainView.findViewById(R.id.current_temp);
        TextView dayandnight = (TextView) mainView.findViewById(R.id.dayandnight);

        TextView current_date = (TextView) mainView.findViewById(R.id.current_date);
        TextView tempstatus = (TextView) mainView.findViewById(R.id.current_temp_en);


        TextView humidity = (TextView) mainView.findViewById(R.id.Humidityvalue);
        TextView dewpoint = (TextView) mainView.findViewById(R.id.dewpointvalue);


        TextView visibility = (TextView) mainView.findViewById(R.id.visibilityvalue);
        TextView uvindex = (TextView) mainView.findViewById(R.id.uvindexvalue);


        TextView wind = (TextView) mainView.findViewById(R.id.windstatus1);
        TextView sunrise = (TextView) mainView.findViewById(R.id.Sunrise);


        TextView sunset = (TextView) mainView.findViewById(R.id.Sunset);


        int currentDate = Calendar.getInstance().getTime().getDate();
        long currentTime = Calendar.getInstance().getTime().getTime();

        int current_temp = (int)(data.getMain().getTemp()-TO_CELSIUS);

        current_date.setText(calculateDate());
        temperature.setText(String.valueOf(current_temp) +"C");

        tempstatus.setText(String.valueOf(data.getWeather().get(0).getMain()));
        dayandnight.setText("Day : " + String.valueOf(data.getMain().getTemp_max()-TO_CELSIUS) + " C "
                +"\n"+ "Night : "+ String.valueOf(data.getMain().getTemp_min()-TO_CELSIUS) + " C");

        int resId = setImage(String.valueOf(tempstatus.getText()).toLowerCase());
        imageView = (ImageView) mainView.findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(resId);
        imageView.setImageDrawable(res);

        humidity.setText(String.valueOf(data.getMain().getHumidity()) + "%");
        dewpoint.setText(String.valueOf((int)(Integer.valueOf(current_temp) + 12)));
        uvindex.setText("--");
        wind.setText(String.valueOf((Math.round(data.getWind().
                getSpeed().floatValue()) * 3.6)*1.0d) +" km/hr"+ "\n"+
                String.valueOf(data.getMain().getPressure()) + " mBar");

        visibility.setText(String.valueOf(data.getVisiblity()/1000) + "km");


        sunrise.setText(String.valueOf(convert(data.getSys().getSunrise()))+"AM");
        sunset.setText(String.valueOf(convert(data.getSys().getSunset()))+"PM");

    }

    public String convert(long value){
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(value * 1000));
        return localTime;
    }

    public String calculateDate(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm");
        String strtime = timeformat.format(calendar.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
        String strDate =  dateFormat.format(calendar.getTime());
        Date date = new Date();
        date.getTime();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
        return String.valueOf(strDate +" , " +dayOfTheWeek + " , " + strtime);
    }

    public void GetData(){

        ServicesDownloader servicesDownloader = APIClient.getClient()
                .create(ServicesDownloader.class);

        Call<WeatherData> listCall = servicesDownloader.
                getWeatherData(constant.latitude,constant.longitude, constant.API_KEY);

        listCall.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                data=response.body();
                SettingupUI();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d("Fail",call.toString());
                Log.d("Faild",t.getMessage());
            }
        });
    }

}
