package com.example.menu;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import es.ujaen.ejemplostema4.FragmentoAlmacenamiento;
import es.ujaen.ejemplostema4.FragmentoInfo;
import es.ujaen.ejemplostema4.R;

public class Menus extends AppCompatActivity implements OnKeyListener {
	public static final String FRAGMENT_DETAILS = "detailsfragment";
	private FragmentManager mManager = null;
	private int posTouched = -1;
	// ArrayAdapter<String> adapter=null;
	MenuListAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menu);

		mManager = getSupportFragmentManager();
		Fragment f = mManager.findFragmentByTag(FRAGMENT_DETAILS);
		if (f == null) {
			FragmentTransaction ft = mManager.beginTransaction();
			FragmentoInfo finfo = new FragmentoInfo();
			ft.add(R.id.fragment_mainmenu_description, finfo, FRAGMENT_DETAILS);
			ft.commit();
		}

		// Inicializaci?n del list view del MEN? PRINCIPAL
		String[] entradasmenu = this.getResources().getStringArray(
				R.array.menuprincipal);

		// adapter = new ArrayAdapter<String>(this,R.layout.lisview_row_menu,
		// R.id.label,entradasmenu);
		adapter = new MenuListAdapter(this, entradasmenu);
		final ListView lista = (ListView) findViewById(R.id.lista);
		lista.setAdapter(adapter);

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				posTouched = position;

				return false;
			}

		});

		this.registerForContextMenu(lista);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_help:
			// Creamos una nueva Activity
			Intent newactivity_help = new Intent(this, Help.class);
			startActivity(newactivity_help);
			return true;
		case R.id.menu_settings:

			return true;
		}

		return false;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contextmenu_ver:
			Toast.makeText(this, "Elemento tocado: " + posTouched,
					Toast.LENGTH_SHORT).show();
			break;

		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onContextMenuClosed(Menu menu) {
		super.onContextMenuClosed(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_lista, menu);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		return false;
	}

	/**
	 * Adaptador para el men? de inicio
	 * @author Juan Carlos
	 *
	 */
	public class MenuListAdapter extends BaseAdapter {

		private final Context context;
		private String[] values;

	

		public static final int ROW_BACKGORUND_ALPHA = 50;
		public static final int ROW_SELECTED_ALPHA = 80;

		public MenuListAdapter(Context context, String[] values) {
			super();
			this.context = context;
			this.values = values;

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.lisview_row_menu,
					parent, false);

			rowView.setClickable(true);
			rowView.setFocusable(true);

			TextView text = (TextView) rowView.findViewById(R.id.label);

			text.setText(values[position]);

		

			rowView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						rowView.setBackgroundDrawable(context.getResources()
								.getDrawable(R.drawable.botonlista_blanco));
					}
					// else
					// {
					// rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_white));
					//
					// }

				}
			});

			rowView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						Intent newactivity_ficheros = new Intent(
								getApplicationContext(), FragmentoAlmacenamiento.class);
						startActivity(newactivity_ficheros);
						break;
					case 1:
						Intent newactivity_fragments = new Intent(
								getApplicationContext(), Fragmentos.class);
						startActivity(newactivity_fragments);
						break;
					case 2:
						Intent newactivity_fragmentsdin = new Intent(
								getApplicationContext(),
								FragmentosDinamicos.class);
						startActivity(newactivity_fragmentsdin);
						break;
					case 3:
						Intent newactivity = new Intent(
								getApplicationContext(), Graficos.class);

						startActivity(newactivity);
						break;
					case 4:

						break;
					case 5:


						break;
					case 6:

						break;
					default:
						finish();
					}

				}
			});

			return rowView;
		}

		@Override
		public int getCount() {
			if (this.values != null) {
				return values.length;
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {

			if (this.values != null)

				if (position < values.length)
					return values[position];

			return null;
		}

		@Override
		public long getItemId(int arg0) {
			if (this.values != null)
				if (arg0 < values.length)
					return values[arg0].length();

			return -1;
		}

		//
		// @Override
		// public void onNewCreature() {
		// this.values=this.board.sortCreaturesByInitiative(false, 20);
		// this.notifyDataSetChanged();
		//
		//
		// }
		//
		// @Override
		// public void onCreatureDeleted() {
		// this.values=this.board.sortCreaturesByInitiative(false, 20);
		// this.notifyDataSetChanged();
		//
		// }
		//

	}

}
