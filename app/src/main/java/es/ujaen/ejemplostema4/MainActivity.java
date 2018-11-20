package es.ujaen.ejemplostema4;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final String FRAGMENTO_DETALLES = "detalles";

    public static final int MENU_CONTEXTUAL_AYUDA = 1;

    FragmentManager mFM = null;
    private View mLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.content_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mFM = getSupportFragmentManager();


        Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
        if (f == null) showHelpFragment();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpFragment();

                Snackbar.make(view, getString(R.string.help_title), Snackbar.LENGTH_LONG).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showHelpFragment() {
        FragmentTransaction ft = mFM.beginTransaction();
        FragmentoInfo info = new FragmentoInfo();
        Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
        if (f != null) {
            ft.remove(f);
            ft.replace(R.id.fragmento_lista, info);
        } else {
            ft.add(R.id.fragmento_lista, info, "INFO");
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Se controla el evento de pulsación de la tecla de volver, haciendo que si está abierto el
     * menú lateral se cierre, y si ya está cerrado se actúe por defecto
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Ejemplo de creación de un submenú
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        boolean result = super.onCreateOptionsMenu(menu);
        SubMenu fileMenu = menu.addSubMenu("File");
        fileMenu.add("new");
        fileMenu.add("open");
        fileMenu.add("save");
        return result;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_help) {
            showHelpFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_CONTEXTUAL_AYUDA, 0, R.string.menu_help);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CONTEXTUAL_AYUDA:
                showHelpFragment();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction ft = mFM.beginTransaction();
        Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
        switch (item.getItemId()) {
            case R.id.nav_about:
                FragmentoAcercade fragmentoAcercade = FragmentoAcercade.newInstance("uno", "dos");
                fragmentoAcercade.show(mFM, "acercade");
                break;
            case R.id.nav_graficos:

                FragmentoGraficos graficos = new FragmentoGraficos();
                if (f != null) {
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, graficos);
                } else {
                    ft.add(R.id.fragmento_lista, graficos, "graficos");
                }
                ft.commit();
                break;
            case R.id.nav_customview:
                FragmentoCustomView customView = new FragmentoCustomView();
                if (f != null) {
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, customView);
                } else {
                    ft.add(R.id.fragmento_lista, customView, "customView");
                }
                ft.commit();
                break;
            case R.id.nav_animaciones:
                FragmentoAnimaciones animaciones = new FragmentoAnimaciones();
                if (f != null) {
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, animaciones);
                } else {
                    ft.add(R.id.fragmento_lista, animaciones, "animaciones");
                }
                ft.commit();
                break;
            case R.id.nav_audio:
                FragmentoMusic music = new FragmentoMusic();
                if (f != null) {
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, music);
                } else {
                    ft.add(R.id.fragmento_lista, music, "music");
                }
                ft.commit();
//                Intent music = new Intent(this, MusicActivity.class);
//                startActivity(music);
                break;
            case R.id.nav_recordaudio:
                FragmentoRecordAudio recordaudio = new FragmentoRecordAudio();
                if (f != null) {
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, recordaudio);
                } else {
                    ft.add(R.id.fragmento_lista, recordaudio, "recordaudio");
                }
                ft.commit();

//                Intent record = new Intent(this, RecordAudioActivity.class);
  //              startActivity(record);
                //  showAudioFragment();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
