package com.devsgazar.gazar.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.devsgazar.gazar.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeWithAccountActivity extends AppCompatActivity {

    private FirebaseAuth BaseAuth;
    private FirebaseUser BaseUser;
    private DrawerLayout MenuDrawer;
    private NavigationView MenuNav;
    private Toolbar ActionBar;
    private MaterialButton BtnUserSet;
    private ActionBarDrawerToggle DrawerToggle;
    private HomeFragment Home;
    private ProfileFragment Profile;
    private CategoriesFragment Categories;
    private HomeWithoutAccountFragment HomeWoutAcc;
    private Intent Into;
    private Context Con = HomeWithAccountActivity.this;

    public void Init() {
        BaseAuth = FirebaseAuth.getInstance();
        MenuDrawer = findViewById(R.id.DrawerLayout);
        MenuNav = findViewById(R.id.NavigationView);
        ActionBar = findViewById(R.id.Toolbar);
        BtnUserSet = findViewById(R.id.BtnUserSettings);
        Home = new HomeFragment();
        Profile = new ProfileFragment();
        Categories = new CategoriesFragment();
        HomeWoutAcc = new HomeWithoutAccountFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_with_account);

        Init();
        AccessControl();
        ClickEvents();
        NavMenu();
    }

    private void AccessControl() {
        BaseUser = BaseAuth.getCurrentUser();
        if (BaseUser != null) {
            SetFragment(Home);
        } else {
            SetFragment(HomeWoutAcc);
            MenuNav.getMenu().findItem(R.id.Exit).setVisible(false);
        }
    }

    public void ClickEvents() {
        BtnUserSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUser != null) {
                    SetFragment(Profile);
                } else {
                    Toast.makeText(Con, "Profilinizin Oluşması İçin Giriş Yapmalısınız!", Toast.LENGTH_LONG).show();
                    Into = new Intent(Con, LoginActivity.class);
                    finish();
                    startActivity(Into);
                }
            }
        });
    }

    public void NavMenu() {
        DrawerToggle = new ActionBarDrawerToggle(this, MenuDrawer, ActionBar, R.string.NavMenuOpen, R.string.NavMenuClose);
        MenuDrawer.addDrawerListener(DrawerToggle);
        DrawerToggle.syncState();

        MenuNav.getMenu().findItem(R.id.HomeFragment).setChecked(true);

        MenuNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.HomeFragment:
                        AccessControl();
                        break;
                    case R.id.AllCategoriesFragment:
                        SetFragment(Categories);
                        break;
                    case R.id.Exit:
                        BaseAuth.signOut();
                        Into = new Intent(Con, LoginActivity.class);
                        finish();
                        startActivity(Into);
                        Toast.makeText(Con, "Çıkış İşlemi Başarıyla Gerçekleştirildi.", Toast.LENGTH_SHORT).show();

                }
                MenuDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void SetFragment(Fragment Frame) {
        FragmentTransaction Transaction = getSupportFragmentManager().beginTransaction();
        Transaction.replace(R.id.FrameLayout, Frame);
        Transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (MenuDrawer.isDrawerOpen(GravityCompat.START))
            MenuDrawer.closeDrawer(GravityCompat.START);
    }
}