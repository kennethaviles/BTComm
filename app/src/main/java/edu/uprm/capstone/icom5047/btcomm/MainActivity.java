package edu.uprm.capstone.icom5047.btcomm;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
//import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import edu.uprm.capstone.icom5047.drawer.ProfileFragment;
import edu.uprm.capstone.icom5047.drawer.SettingsFragment;
import edu.uprm.capstone.icom5047.drawer.TopFragment2;

public class MainActivity extends AppCompatActivity implements DeviceListFragment.OnFragmentInteractionListener{

    public static int REQUEST_BLUETOOTH = 1;

    private BluetoothAdapter blueAdapter;
    private DeviceListFragment mDeviceListFragment;

    private ConnectManager connectManager;

    private String[] titles;
    private ListView drawerList;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ShareActionProvider shareActionProvider;

    private int currentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        blueAdapter = BluetoothAdapter.getDefaultAdapter();
        //Phone does not support Bluetooth so let the user know and exit
        if(blueAdapter == null){
            new AlertDialog.Builder(this)
                    .setTitle("Not Compatible")
                    .setMessage("Your smartphone does not support Bluetooth Communication")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(!blueAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        mDeviceListFragment = DeviceListFragment.newInstance(blueAdapter);

        fragmentManager.beginTransaction().replace(R.id.container, mDeviceListFragment).commit();

        /*
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //populate list
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.simple_list_item_activated_1, titles));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if(savedInstanceState != null){
            currentPos = savedInstanceState.getInt("position");
            setActionBarTitle(currentPos);
        }
        else{
            selectItem(0);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener(){
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragManager = getFragmentManager();
                        Fragment frag = fragManager.findFragmentByTag("visible_fragment");
                        if(frag instanceof TopFragment2){
                            currentPos = 0;
                        }
                        if(frag instanceof ProfileFragment){
                            currentPos = 1;
                        }
                        if(frag instanceof SettingsFragment){
                            currentPos = 2;
                        }
                        setActionBarTitle(currentPos);
                        drawerList.setItemChecked(currentPos, true);
                    }

                }
        );
        */

    }

//    public boolean onPrepareOptionsMenu(Menu menu){
//        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
//        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if(drawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onFragmentInteraction(String id) {
        connectManager = new ConnectManager(id);
        Intent intent = new Intent(this, SendAndGetDataActivity.class);
        intent.putExtra("device_name", id);
        startActivity(intent);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, DelayedMessageService.class);
        intent.putExtra("message", getResources().getString(R.string.button_response));
        startService(intent);
    }

//    private class DrawerItemClickListener implements ListView.OnItemClickListener{
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    private void selectItem(int position){
//        Fragment fragment;
//        currentPos = position;
//
//        switch (position){
//            case 1:
//                fragment = new ProfileFragment();
//                break;
//            case 2:
//                fragment = new SettingsFragment();
//                break;
//            default:
//                fragment = new TopFragment2();
//        }
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.content_frame, fragment);
//        ft.addToBackStack(null);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.commit();
//
//        setActionBarTitle(position);
//
//        //Close the drawer
//        //DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawer(drawerList);
//    }

//    private void setActionBarTitle(int position) {
//        String title;
//        if(position == 0){
//            title = getResources().getString(R.string.app_name);
//        }
//        else{
//            title = titles[position];
//        }
//        getActionBar().setTitle(title);
//    }

//    public void onPostCreate(Bundle savedInstanceState){
//        super.onPostCreate(savedInstanceState);
//        //sync
//        drawerToggle.syncState();
//    }

//    public void onConfigurationChanged(Configuration newConfig){
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    public void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        outState.putInt("position", currentPos);
//    }
}
