package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import es.ujaen.ejemplostema4.R;

public class Fragmentos extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragmentos);

	}

	public void show() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		Fragment fragment = fm.findFragmentById(R.id.fragment_viewer);

		if (fragment != null) {

			if (!fragment.isVisible()) {
				ft.show(fragment);
				ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.addToBackStack(null);
				ft.commit();
			}

		}

	}

	public void replace() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		Fragment fragment = fm.findFragmentById(R.id.fragment_container);

		if (fragment == null) {

			Barra barra = new Barra();
			ft.add(R.id.fragment_viewer, barra);
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();

		} else {
			Barra barra = new Barra();
			
			ft.replace(R.id.fragment_viewer, barra);
			ft.remove(fragment);
			// ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();

		}

	}

	public void hide() {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_viewer);

		FragmentTransaction ft = fm.beginTransaction();

		if (fragment != null) {

			ft.hide(fragment);
			ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_fragmentos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_hidePanel:
			hide();
			return true;
		case R.id.menu_showPanel:
			show();
			return true;
		case R.id.menu_replacePanel:
			replace();
			return true;
		}
		return false;
	}

}
