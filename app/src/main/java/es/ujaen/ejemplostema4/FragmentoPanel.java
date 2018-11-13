package es.ujaen.ejemplostema4;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import es.ujaen.ejemplostema4.data.Car;
import es.ujaen.ejemplostema4.data.CarListAdapter;

public class FragmentoPanel extends Fragment {

    TextView mPublicar = null;
    ListView mLista=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View fragment = inflater.inflate(R.layout.layout_fragment_panel, null);

        setHasOptionsMenu(true);//Para comunicar que el panel quiere recibir los eventos de la barra de accion

        mLista = (ListView) fragment.findViewById(R.id.listView1);
        mPublicar =(TextView) fragment.findViewById(R.id.panel_textView_centro);

        //Inicializacion del adaptador
        List<Car> cars = new ArrayList<Car>();

        Car c1 = new Car("SL500", "Mercedes", 500, 1);
        Car c2 = new Car("Z4", "BMW", 450, 2);
        Car c3 = new Car("R8", "Audi", 525, 2);

        cars.add(c1);
        cars.add(c2);
        cars.add(c3);


        CarListAdapter nla = new CarListAdapter(this.getActivity(), cars);

        mLista.setAdapter(nla);

        return fragment;

    }

    public void publica(CharSequence text) {
        if (mPublicar != null) {
            mPublicar.setText(text);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflater) {
        menuinflater.inflate(R.menu.menu_fragment_panel, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_pulsaFragmentPanel:
                Toast.makeText(getActivity(), getResources().getString(R.string.fragmentPanelMenuPulsa), Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
