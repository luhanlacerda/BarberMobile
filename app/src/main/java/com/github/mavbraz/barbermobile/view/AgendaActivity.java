package com.github.mavbraz.barbermobile.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.mavbraz.barbermobile.R;

public class AgendaActivity extends AppCompatActivity implements ProximoFragment.OnFragmentInteractionListener,
        AnteriorFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        AgendaPagerAdapter pagerAdapter = new AgendaPagerAdapter(this, getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
