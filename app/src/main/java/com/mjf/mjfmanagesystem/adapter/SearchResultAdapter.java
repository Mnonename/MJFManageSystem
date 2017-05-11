package com.mjf.mjfmanagesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseRecycleAdapter;
import com.mjf.mjfmanagesystem.entity.UserInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lxt on 2017/5/11.
 */
public class SearchResultAdapter extends BaseRecycleAdapter<UserInfo> {



    public SearchResultAdapter(Context context, List<UserInfo> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.adapter_search_result, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserInfo  item = getItem(position);
        ViewHolder vh = (ViewHolder) holder;
        ((ViewHolder) holder).tvName.setText(item.username);
        ((ViewHolder) holder).tvPhone.setText(item.phone);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name) TextView tvName;
        @InjectView(R.id.tv_phone) TextView tvPhone;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
