package com.mjf.mjfmanagesystem.modules;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.fragment.AddFragment;
import com.mjf.mjfmanagesystem.fragment.MyFragment;
import com.mjf.mjfmanagesystem.fragment.SelectFragment;
import com.mjf.mjfmanagesystem.view.CustomFragmentAdapter;
import com.mjf.mjfmanagesystem.view.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.view_bottom_line) View viewBottomLine;
    @InjectView(R.id.rb_select) RadioButton rbSelect;
    @InjectView(R.id.rb_add) RadioButton rbAdd;
    @InjectView(R.id.rb_modify) RadioButton rbModify;
    @InjectView(R.id.rb_delete) RadioButton rbDelete;
    @InjectView(R.id.rb_me) RadioButton rbMe;
    @InjectView(R.id.rg_main) RadioGroup rgMain;
    @InjectView(R.id.pager) NonSwipeableViewPager mPager;

    int[] values = {R.id.rb_select, R.id.rb_add, R.id.rb_me};
    RadioButton[] radioButtons = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        radioButtons = new RadioButton[]{rbSelect, rbAdd,  rbMe};
        try {

            List<Fragment> listFragment = new ArrayList<>();
            listFragment.add(new SelectFragment());
            listFragment.add(new AddFragment());
//            listFragment.add(new SelectFragment());
//            listFragment.add(new AddFragment());
            listFragment.add(new MyFragment());

            mPager.setOffscreenPageLimit(values.length);
            mPager.setOnPageChangeListener(new MyPagerChangeListener());
            mPager.setAdapter(new CustomFragmentAdapter(getSupportFragmentManager(), listFragment));
            rgMain.setOnCheckedChangeListener(new MyCheckedListener());
            rgMain.check(values[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class MyCheckedListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            for (int i = 0; i < values.length; i++) {
                if (checkedId == values[i]) {

                    //分类的时候，里面的内容也直接定位分类
//                    if (i == 1) {
//                        EventBus.getDefault().post(new NavEvent(NavEvent.FIRST));
//                    }

                    mPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
    }
    /**
     * ViewPager 滑动监听事件
     */
    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 滑动时，取消RadioGroup选中监听时间
            rgMain.setOnCheckedChangeListener(null);
            rgMain.check(values[position]);
            // 滑动完成后，重新绑定选中监听
            rgMain.setOnCheckedChangeListener(new MyCheckedListener());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
