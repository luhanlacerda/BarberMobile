package com.github.mavbraz.barbermobile.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.mavbraz.barbermobile.R;

import java.util.Locale;

public class AgendaPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;

    public AgendaPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        this.titles = ctx.getResources().getStringArray(R.array.titles);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AnteriorFragment.newInstance("a", "b");
            case 1:
                return ProximoFragment.newInstance("a", "b");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale locale = Locale.getDefault();
        return this.titles[position].toUpperCase(locale);
    }
}


