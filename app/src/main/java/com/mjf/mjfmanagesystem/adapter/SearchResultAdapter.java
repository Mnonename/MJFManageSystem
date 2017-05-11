package com.mjf.mjfmanagesystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseRecycleAdapter;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.modules.DetailActivity;

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
        final UserInfo item = getItem(position);
        ViewHolder vh = (ViewHolder) holder;
        ((ViewHolder) holder).tvName.setText(item.username);
        if(item.phone == null){
            ((ViewHolder) holder).tvPhone.setText("电话号码：");
        }else{
            ((ViewHolder) holder).tvPhone.setText("电话号码："+item.phone);
        }
       if(item.business==null){
           ((ViewHolder) holder).tvBusiness.setText("业务套餐:");
       }else {
           ((ViewHolder) holder).tvBusiness.setText("业务套餐:"+item.business);
       }



        if("1".equals(item.isVip)){
            ((ViewHolder) holder).tvIsVip.setBackgroundResource(R.mipmap.detail_vip);
        }else{
            ((ViewHolder) holder).tvIsVip.setBackgroundResource(0);
        }
        if("1".equals(item.sex)){
            ((ViewHolder) holder).tvCreateTime.setText("男");
        }else{
            ((ViewHolder) holder).tvCreateTime.setText("女");
        }
        ((ViewHolder) holder).llMain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.newIntent(mContext,item);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.view_1) View view1;
        @InjectView(R.id.tv_name) TextView tvName;
        @InjectView(R.id.tv_is_vip) TextView tvIsVip;
        @InjectView(R.id.tv_business) TextView tvBusiness;
        @InjectView(R.id.tv_create_time) TextView tvCreateTime;
        @InjectView(R.id.tv_phone) TextView tvPhone;
        @InjectView(R.id.ll_main_1) LinearLayout llMain1;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
