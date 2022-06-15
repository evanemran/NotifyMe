package com.example.notifyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import com.example.notifyme.adapters.DrawerAdapter;
import com.example.notifyme.fragments.MapFragment;
import com.example.notifyme.listeners.NavMenuClickListener;
import com.example.notifyme.models.NavMenu;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    PackageManager packageManager;
    DrawerLayout drawer;
    TextView version_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar);
        version_name = findViewById(R.id.version_name);

        loadFragment(new MapFragment());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.open_nav_drawer, R.string.close_nav_drawer
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavMenu();

        packageManager = getPackageManager();


        //changeOwnLocationButtonPosition();

    }

    private void setupNavMenu() {
        List<NavMenu> navMenus = new ArrayList<>();
        navMenus.add(NavMenu.HOME);
        navMenus.add(NavMenu.SETTINGS);
        navMenus.add(NavMenu.THEME);
        navMenus.add(NavMenu.ABOUT);
        navMenus.add(NavMenu.POLICY);
        RecyclerView recycler_nav = findViewById(R.id.recycler_nav);
        recycler_nav.setHasFixedSize(true);
        recycler_nav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, navMenus, navMenuClickListener);
        recycler_nav.setAdapter(drawerAdapter);

        try {
            PackageInfo pInfo =
                    getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version_name.setText("Version: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeOwnLocationButtonPosition() {
        /*View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 180, 180, 0);*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final NavMenuClickListener navMenuClickListener = new NavMenuClickListener() {
        @Override
        public void onNavClicked(NavMenu menu) {
            switch (menu.getStringId()){
                case R.string.home:
                    loadFragment(new MapFragment());
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Will be added soon!", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}