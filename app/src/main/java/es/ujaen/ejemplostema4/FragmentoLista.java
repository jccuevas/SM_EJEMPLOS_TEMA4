package es.ujaen.ejemplostema4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FragmentoLista extends ListFragment {

    boolean mDualPane = true;
    int mCurCheckPosition = 0;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Rellena la lista con estos valores
        String[] values = new String[]{"uno", "dos","tres"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        registerForContextMenu(getListView());


    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (getActivity().findViewById(R.id.fragmento_detalles) != null) {
            FragmentoPanel panel = (FragmentoPanel) getFragmentManager().findFragmentById(R.id.fragmento_detalles);
            if (panel == null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                panel = new FragmentoPanel();
                ft.add(R.id.fragmento_detalles,panel,MainActivity.FRAGMENTO_DETALLES);
                ft.addToBackStack(MainActivity.FRAGMENTO_DETALLES);
                ft.commit();
            }
            panel.publica("Pulsado " + (position+1));
        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment old=getFragmentManager().findFragmentById(R.id.fragmento_lista);
            FragmentoPanel panel = new FragmentoPanel();
            ft.remove(old);
            ft.add(R.id.fragmento_lista,panel,MainActivity.FRAGMENTO_DETALLES);
            ft.addToBackStack(MainActivity.FRAGMENTO_DETALLES);
            ft.commit();
            panel.publica("Pulsado " + position);
        }
    }


}
