package com.example.rah.soccerprototype.primaryview;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rah.soccerprototype.primaryview.fragments.HomeScreen;
import com.example.rah.soccerprototype.primaryview.fragments.Lost;
import com.example.rah.soccerprototype.primaryview.fragments.Profile;
import com.example.rah.soccerprototype.R;
import com.example.rah.soccerprototype.login.WelcomeScreen;

/**
 * The Main Activity is to hold (1) the navigation drawer on the left, (2) the nav bar on the top,
 * and (3) control the swapping of the fragment/view that is shown in the center.
 */
public class MainActivity extends AppCompatActivity {
    private String[] mNavDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Set the Nav Drawer Titles in the newly created values/arrays.xml file
        mNavDrawerTitles = getResources().getStringArray(R.array.navDrawerTitles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mNavDrawerTitles));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                //R.drawable.ic_menu_black_48dp,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mTitle);
                    Log.d("soccerLogger", "set mTitle: " + mTitle );
                } else {
                    Log.d("soccerLogger", "nullAction bar" );
                }
            }


            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mDrawerTitle);
                    Log.d("soccerLogger", "set mTitle: " + mTitle );
                } else {
                    Log.d("soccerLogger", "nullAction bar" );
                }
            }
        };



        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            Log.d("soccerLogger", "set stuff");
        } else {
            Log.d("soccerLogger", "tesddt");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent welcomeScreenIntent = new Intent(this, WelcomeScreen.class);
                welcomeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(welcomeScreenIntent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {

        //Create a new fragment from the below
        Log.d("soccerLogger", "selectItemNumber: " + position );
        Fragment fragment;
        Bundle args = new Bundle();
        switch (position)
        {
            //Profile View
            case 0:
                Log.d("soccerLogger", "Profile(): " + position );
                fragment = new Profile();
                break;
            case 1:
                Log.d("soccerLogger", "HomeScreen(): " + position );
                fragment = new HomeScreen();
                break;
            default:
                Log.d("soccerLogger", "Lost(): " + position );
                fragment = new Lost();
                break;
        }

        //The param is the position of the nav drawer that was clicked so the fragment can
        //set the name of the nav bar
        args.putInt(HomeScreen.ARG_PARAM1, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavDrawerTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        Log.d("soccerLogger", "setTitle: " + title );
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

}
