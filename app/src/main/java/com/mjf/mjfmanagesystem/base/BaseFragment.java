package com.mjf.mjfmanagesystem.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;

/**
 * Created by lxt on 2017/5/10.
 */
public class BaseFragment extends Fragment {
    protected Context mContext;
    protected RecodeGpsListSQLHelper mHelper;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mHelper = new RecodeGpsListSQLHelper(mContext);
    }
}
