package com.github.mavbraz.barbermobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mavbraz.barbermobile.R;
import com.github.mavbraz.barbermobile.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    private ArrayList<String> mTitles = new ArrayList<>();
    private SharedPreferencesManager mSharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        setTitle(mTitles.get(0));
        mMenuAdapter.setViewSelected(0, true);
        goToFragment(new MainFragment(), false, MainFragment.TAG, false);

        mSharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());
        String email = mSharedPreferencesManager.getEmail();
        if (email != null) {
            TextView txtHeader = mViewHolder.mDuoMenuView.getHeaderView().findViewById(R.id.header_text);
            txtHeader.setText(email);
        } else {
            this.onFooterClicked();
        }
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        mSharedPreferencesManager.removeToken();
        mSharedPreferencesManager.removeEmail();

        startActivity(new Intent(this, LoginActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private boolean goToFragment(Fragment fragment, boolean replace, String tag, boolean addToBackStack) {
        if (getSupportFragmentManager().findFragmentByTag(tag) != null &&
                getSupportFragmentManager().findFragmentByTag(tag).isVisible()) {
            return false;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        if (replace) {
            transaction.replace(R.id.container, fragment, tag);
        } else {
            transaction.add(R.id.container, fragment, tag);
        }

        transaction.commit();

        return true;
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        boolean wasAdded = false;

        // Navigate to the right fragment
        switch (position) {
            case 0:
                wasAdded = goToFragment(new MainFragment(), true, MainFragment.TAG, true);
                break;
            case 1:
                wasAdded = goToFragment(AgendaFragment.newInstance(), true, AgendaFragment.TAG, true);
                break;
            case 2:
                wasAdded = goToFragment(new SolicitarServicoFragment(), true, SolicitarServicoFragment.TAG, true);
                break;
        }

        if (wasAdded) {
            // Set the toolbar title
            setTitle(mTitles.get(position));

            // Set the right options selected
            mMenuAdapter.setViewSelected(position, true);

            // Close the drawer
            mViewHolder.mDuoDrawerLayout.closeDrawer();
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount == 0) {
            moveTaskToBack(true);
        } else {
            setTitle(mTitles.get(backStackCount - 1));
            mMenuAdapter.setViewSelected(backStackCount - 1, true);
            super.onBackPressed();
        }
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
        }
    }
}