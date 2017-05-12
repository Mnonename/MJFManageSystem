package com.mjf.mjfmanagesystem.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseRecycleAdapter;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lxt on 2017/5/11.
 */
public class SearchResultAdapter extends BaseRecycleAdapter<UserInfo> {

    public interface OnRecyclerViewListener {
        void onItemClick(int position, View v);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    boolean isLongClick = false;
    public SearchResultAdapter(Context context, List<UserInfo> data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View convertView;
        convertView = mInflater.inflate(R.layout.adapter_search_result, null);
        holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final UserInfo item = getItem(position);
        ViewHolder vh = (ViewHolder) holder;
        ((ViewHolder) holder).tvName.setText(item.username);
        if (item.phone == null) {
            ((ViewHolder) holder).tvPhone.setText("电话号码：");
        } else {
            ((ViewHolder) holder).tvPhone.setText("电话号码：" + item.phone);
        }
        if (item.business == null) {
            ((ViewHolder) holder).tvBusiness.setText("业务套餐:");
        } else {
            ((ViewHolder) holder).tvBusiness.setText("业务套餐：" + item.business);
        }

        if ("1".equals(item.isVip)) {
            ((ViewHolder) holder).tvIsVip.setBackgroundResource(R.mipmap.detail_vip);
        } else {
            ((ViewHolder) holder).tvIsVip.setBackgroundResource(0);
        }
        if ("1".equals(item.sex)) {
            ((ViewHolder) holder).tvCreateTime.setText("男");
        } else {
            ((ViewHolder) holder).tvCreateTime.setText("女");
        }
//        ((ViewHolder) holder).llMain1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ModifyActivity.newIntent(mContext, item);
//            }
//        });
//        ((ViewHolder) holder).llMain1.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                showDialog(item,position);
//                return true;
//            }
//        });
    }

    private void showDialog(final UserInfo userInfo, final int position) {
        new AlertDialog.Builder(mContext).setTitle("提示")//设置对话框标题
                .setMessage("是否确认删除？")//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        remove(position);
                        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(mContext);
                        mHelper.deleteUser(userInfo.ID);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
            }

        }).show();//在按键响应事件中显示此对话框
    }

     class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener {
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
            llMain1.setOnClickListener(this);
            llMain1.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(getPosition(), v);
            }
        }
         @Override
         public boolean onLongClick(View v) {
             if (null != onRecyclerViewListener) {
                 return onRecyclerViewListener.onItemLongClick(getPosition());
             }
             return false;
         }
    }
}
