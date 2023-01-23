package com.example.weather_forecast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather_forecast.R;

import java.util.List;

/**
 * @PackageName: com.example.weather_forecast.adapter
 * @ClassName: DeleteCityListAdapter
 * @Author: winwa
 * @Date: 2023/1/23 10:23
 * @Description:
 **/
public class DeleteCityListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private List<String> mDeletedCities;

    public DeleteCityListAdapter(Context context, List<String> data, List<String> deletedCities) {
        mContext = context;
        mData = data;
        mDeletedCities = deletedCities;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_listview_delete_city, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String city = mData.get(i);
        holder.mCityTv.setText(city);
        holder.mPicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(city);
                mDeletedCities.add(city);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    class ViewHolder {
        TextView mCityTv;
        ImageView mPicIv;

        public ViewHolder(View view) {
            mCityTv = view.findViewById(R.id.lvitem_delete_city_tv);
            mPicIv = view.findViewById(R.id.lvitem_delete_pic_iv);
        }
    }
}
