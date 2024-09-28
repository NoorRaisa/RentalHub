package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrgHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_home);
        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        loadfragment(new OrgHomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        switch(item.getItemId())
        {
            case R.id.homePage2:
                fragment=new OrgHomeFragment();
                break;

            case R.id.postAHome2:
                fragment=new OrgPostFragment();
                break;

            case R.id.profile2:
                fragment=new OrgProfileFragment();
                break;
        }
        return loadfragment(fragment);
    }
    public boolean loadfragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}