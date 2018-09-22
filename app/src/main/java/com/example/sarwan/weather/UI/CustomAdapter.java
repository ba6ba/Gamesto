package com.example.sarwan.weather.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarwan.weather.R;
import com.example.sarwan.weather.model.FilteredData;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private Context mContext;
    private List<FilteredData> mList;
    private ImageView imageView;

    public CustomAdapter(Context mContext, List<FilteredData> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.custom_row, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {

        customViewHolder.date.setText(mList.get(i).getDate());
        customViewHolder.max.setText(mList.get(i).getMax_temp()+"C");
        customViewHolder.min.setText(mList.get(i).getMin_temp()+"C");
        customViewHolder.status.setText(mList.get(i).getStatus());
        customViewHolder.icon.setImageResource(setImage(mList.get(i).getStatus()));
    }

    private int setImage(String value){
        String uri = "@drawable/"+value.toLowerCase();  // where myresource (without the extension) is the file

        int resource = mContext.getResources().getIdentifier(uri,
                "drawable", mContext.getPackageName());

        return resource;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public View view;

        TextView max;
        TextView min;
        TextView date;
        TextView status;
        ImageView icon;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            date = view.findViewById(R.id.date);
            max = view.findViewById(R.id.max);
            min = view.findViewById(R.id.min);
            status = view.findViewById(R.id.status);
            icon = view.findViewById(R.id.coverImage);

        }
    }
}
