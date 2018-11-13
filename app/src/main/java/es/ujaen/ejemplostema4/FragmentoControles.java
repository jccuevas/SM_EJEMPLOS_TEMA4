package es.ujaen.ejemplostema4;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentoControles.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentoControles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoControles extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    final static public String[] datos = {"Verde", "Azul", "Rojo"};


    private OnFragmentInteractionListener mListener;

    public FragmentoControles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoControles.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoControles newInstance(String param1, String param2) {
        FragmentoControles fragment = new FragmentoControles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate(R.layout.layout_fragment_controles, container, false);


        final ImageButton iboton = (ImageButton) vista.findViewById(R.id.fragment_controles_imageButton);
        Button boton = (Button) vista.findViewById(R.id.fragment_controles_button);
        final TextView texto = (TextView) vista.findViewById(R.id.fragment_controles_textocentral);
        RadioGroup radiog = (RadioGroup) vista.findViewById(R.id.fragment_controles_gruporb);
        final EditText edittext = (EditText) vista.findViewById(R.id.fragment_controles_editText);
        final CheckBox checkBox = (CheckBox) vista.findViewById(R.id.fragment_controles_checkbox);


        iboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto.setText(getString(R.string.controles_pulsado));
                texto.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        texto.setText(getString(R.string.controles));
                    }
                }, 3000);
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texto.setText(getString(R.string.controles_pulsado));
                texto.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        texto.setText(getString(R.string.controles));
                    }
                }, 3000);
            }
        });

        final ToggleButton tboton = (ToggleButton) vista.findViewById(R.id.fragment_controles_toggleButton);
        tboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tboton.isChecked()) {
                    iboton.setBackgroundResource(R.drawable.ic_bombilla_on);
                } else {
                    iboton.setBackgroundResource(R.drawable.ic_bombilla_off);
                }
            }
        });

        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        Toast.makeText(getContext(), getString(R.string.controles_radio1_opcion1), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton2:
                        Toast.makeText(getContext(), getString(R.string.controles_radio2_opcion2), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, datos);
        Spinner spinner = (Spinner) vista.findViewById(R.id.fragment_controles_spinner);
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        vista.findViewById(R.id.controles_root).setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                        break;
                    case 1:
                        vista.findViewById(R.id.controles_root).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                        break;
                    case 2:
                        vista.findViewById(R.id.controles_root).setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Creamos un nuevo objeto de tipo Editable
                String texto = edittext.getEditableText().toString();
                Editable str = edittext.getEditableText();//Editable.Factory.getInstance().newEditable("Esto es negrita.");

                if (texto.length() > 0) {
                    if (buttonView.isChecked()) {
                        //Marcamos cono fuente negrita la palabra "simulacro"
                        str.setSpan(new StyleSpan(Typeface.BOLD), 0, texto.length(), Spannable.SPAN_COMPOSING);
                    } else {
                        str.setSpan(new StyleSpan(Typeface.NORMAL), 0, texto.length(), Spannable.SPAN_COMPOSING);
                    }
                }
            }
        });

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    checkBox.setChecked(false);
                }
                return false;
            }
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook methodp into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
