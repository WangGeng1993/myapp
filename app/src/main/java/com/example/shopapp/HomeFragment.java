package com.example.shopapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    //ViewPager控件
    private ViewPager viewPager ;
    //RadioGroup控件
    private RadioGroup radioGroup ;
    //类型为Fragment的动态数组
    private ArrayList<Fragment> fList= new ArrayList<Fragment>() ;


    private long startTime=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

    }
}
