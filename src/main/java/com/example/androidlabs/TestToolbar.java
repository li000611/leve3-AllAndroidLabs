package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;



public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"You clicked on Printer",Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(this,"You clicked on Download",Toast.LENGTH_LONG).show();
                break;
            case R.id.item3:
                Toast.makeText(this,"You clicked on here",Toast.LENGTH_LONG).show();
                 break;
            }
            return true;
    }

 @Override
    public boolean onNavigationItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.id_chat:
                Intent chatIntent = new Intent (this,ChatRoomActivity.class);
                startActivity(chatIntent);
                break;
            case R.id.id_weather:
                Intent weatherIntent = new Intent(this,WeatherForecast.class);
                startActivity(weatherIntent);
                break;
            case R.id.id_login:
                Intent loginIntent = new Intent(this,ProfileActivity.class);
                setResult(500);
                finish();
                break;
        }
        return false;
    }
}
