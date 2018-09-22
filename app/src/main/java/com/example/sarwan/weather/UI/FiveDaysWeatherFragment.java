package com.example.sarwan.weather.UI;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sarwan.weather.Constants.constant;
import com.example.sarwan.weather.MainActivity;
import com.example.sarwan.weather.R;
import com.example.sarwan.weather.model.FilteredData;
import com.example.sarwan.weather.model.WeatherData;
import com.example.sarwan.weather.model.WeatherDetailedData;
import com.example.sarwan.weather.network.APIClient;
import com.example.sarwan.weather.network.ServicesDownloader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sarwan.weather.Constants.constant.API_KEY;

public class FiveDaysWeatherFragment extends Fragment {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    public List<WeatherData> data;
    public double TO_CELSIUS=273.15;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_five_days,
                container, false);
        progressDialog = new ProgressDialog(rootView.getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        GetDetailedData();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean checkDate(String current,String next){
        if(!(current.equalsIgnoreCase(next))){
            return true;
        }
        else {
            return false;
        }
    }


    private List<List<String>> calculate(List<String> var,WeatherDetailedData data) {

        List<List<String>> toReturnList = new ArrayList<>();
        for(int calculate=0;calculate<var.size();calculate++){
            List<String> list = new ArrayList<>();
            List<String> finalList = new ArrayList<>();
            for (int i = 0; i < data.getList().size(); i++) {
                String key = (data.getList().get(i).getDt_txt());
                if (var.get(calculate).equalsIgnoreCase("max")) {
                    list.add(String.valueOf(Math.round(data.getList().get(i).getMain().getTemp_max() - TO_CELSIUS)));
                }
                else if (var.get(calculate).equalsIgnoreCase("min")) {
                    list.add(String.valueOf(Math.round(data.getList().get(i).getMain().getTemp_min() - TO_CELSIUS)));
                }
                else if (var.get(calculate).equalsIgnoreCase("status")) {
                    list.add(String.valueOf(data.getList().get(i).getWeather().get(0).getMain()));
                }
                else {
                    list.add(String.valueOf(key.substring(0,key.indexOf(' '))));
                }
                String next_key = null;
                if (i == data.getList().size() - 1) {
                    next_key = (data.getList().get(i).getDt_txt());
                } else {
                    next_key = (data.getList().get(i + 1).getDt_txt());
                }

                if (checkDate((key).substring(0, key.indexOf(' ')),
                        (next_key).substring(0, next_key.indexOf(' ')))) {

                    if(var.get(calculate).equalsIgnoreCase("max"))
                    {finalList.add((Collections.max(list)));}
                    else if(var.get(calculate).equalsIgnoreCase("min"))
                    {finalList.add((Collections.min(list)));}
                    else if(var.get(calculate).equalsIgnoreCase("status")){
                        finalList.add((Collections.max(list)));
                    }
                    else {
                        String date = Collections.max(list);
                        finalList.add((Collections.max(list)));
                    }
                    list.clear();
                }
            }
            toReturnList.add(finalList);
        }
        return toReturnList;
    }
    public void GetDetailedData() {

        ServicesDownloader servicesDownloader = APIClient.getClient()
                .create(ServicesDownloader.class);

        Call<WeatherDetailedData> listCall = servicesDownloader.
                getdetailedWeatherData(constant.latitude,constant.longitude,API_KEY);

        listCall.enqueue(new Callback<WeatherDetailedData>() {
            @Override
            public void onResponse(Call<WeatherDetailedData> call, Response<WeatherDetailedData> response) {
                List<String> toCalculate = new ArrayList<>();
                List<List<String>> Result = new ArrayList<>();
                toCalculate.add("max");
                toCalculate.add("min");
                toCalculate.add("status");
                toCalculate.add("date");
                Result = calculate(toCalculate,response.body());
                progressDialog.dismiss();
                generateData(filteringData(Result));
            }

            @Override
            public void onFailure(Call<WeatherDetailedData> call, Throwable t) {
                Log.d("Failed",t.getMessage());
            }
        });
    }


    public void generateData(List<FilteredData> list){
        recyclerView = (RecyclerView)  rootView.findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this.getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private List<FilteredData> filteringData(List<List<String>> result) {
        List<FilteredData> filteredData = new ArrayList<>();
        for(int j=0;j<result.size();j++){
            filteredData.add(new FilteredData(result.get(3).get(j),result.get(2).get(j),result.get(1).get(j),result.get(0).get(j)));
        }
        filteredData.add(new FilteredData("2018-06-25","Clouds","29","35"));
        filteredData.add(new FilteredData("2018-06-26","Sunny","30","36"));

        for(int i=0;i<filteredData.size();i++){
            Log.d("date",String.valueOf(filteredData.get(i).getDate()));
            Log.d("max",String.valueOf(filteredData.get(i).getMax_temp()));
            Log.d("min",String.valueOf(filteredData.get(i).getMin_temp()));
            Log.d("status",String.valueOf(filteredData.get(i).getStatus()));
        }
        return filteredData;
    }
}
