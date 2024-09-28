package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserHome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        BottomNavigationView navigationView=findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        mobile=getIntent().getStringExtra( "Mobile" );
        loadfragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        switch(item.getItemId())
        {
            case R.id.homePage:
                fragment=new HomeFragment();
                break;
            case R.id.search:
                fragment=new SearchFragment();
                break;
            case R.id.postAHome:
                fragment=new PostFragment();
                break;
            case R.id.favourites:
                fragment=new AboutUsFragment();
                break;
            case R.id.profile:
                fragment=new ProfileFragment();
                break;
        }
        return loadfragment(fragment);
    }
    public boolean loadfragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace( R.id.fragment_container,fragment ).commit();
            Bundle bundle=new Bundle();
            bundle.putString( "Mobile",mobile );
            fragment.setArguments( bundle );
            return true;
        }
        return false;
    }
}