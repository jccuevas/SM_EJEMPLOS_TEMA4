package es.ujaen.ejemplostema4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.ujaen.ejemplostema4.view.Lienzo;

public class FragmentoCustomView extends Fragment {
    private View mFragmentView = null;
	private ImageView logoandroid = null;
	private ImageView logoandroidscale = null;
	private TextView  textscale=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        mFragmentView= inflater.inflate(R.layout.layout_fragment_customview, container, false);



		Lienzo lienzo = (Lienzo) mFragmentView.findViewById(R.id.custom_drawable_lienzo);
//		lienzo.setOnTouchListener(lienzo);




        return  mFragmentView;
	}


}
