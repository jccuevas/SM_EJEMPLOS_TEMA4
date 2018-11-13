package es.ujaen.ejemplostema4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import es.ujaen.ejemplostema4.utils.Record;
import es.ujaen.ejemplostema4.utils.SQLiteTableRecords;


public class FragmentoAlmacenamiento extends Fragment {

    protected SQLiteTableRecords database = null;
    EditText mEdit_t = null;
    EditText mEdit_n = null;
    EditText mEdit_f = null;
    TextView resultado = null;
    Button mBotonGrabar = null;
    Button mBotonCargar = null;
    Button mBotonGrabarEx = null;
    Button mBotonCargarEx = null;
    Button mBotonGrabarDB = null;
    Button mBotonCargarDB = null;

    protected View mFragmentView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new SQLiteTableRecords(getContext());
        database.open();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentView = inflater.inflate(R.layout.layout_fragment_almacenamiento, null);

        mEdit_t = (EditText) mFragmentView.findViewById(R.id.ficheros_editText_texto);
        mEdit_n = (EditText) mFragmentView.findViewById(R.id.ficheros_editText_value);
        mEdit_f = (EditText) mFragmentView.findViewById(R.id.ficheros_editText_filename);
        resultado = (TextView) mFragmentView.findViewById(R.id.ficheros_textView_result);

         mBotonGrabar = (Button) mFragmentView.findViewById(R.id.ficheros_button_save);
         mBotonCargar = (Button) mFragmentView.findViewById(R.id.ficheros_button_load);
         mBotonGrabarEx = (Button) mFragmentView.findViewById(R.id.ficheros_button_save_ex);
         mBotonCargarEx = (Button) mFragmentView.findViewById(R.id.ficheros_button_load_Ex);
         mBotonGrabarDB = (Button) mFragmentView.findViewById(R.id.ficheros_button_save_db);
         mBotonCargarDB = (Button) mFragmentView.findViewById(R.id.ficheros_button_load_db);

        mBotonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        mBotonCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        mBotonGrabarEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExternal();
            }
        });
        mBotonCargarEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadExternal();
            }
        });
        mBotonGrabarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDatabase();
            }
        });
        mBotonCargarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatabase();
            }
        });
        return mFragmentView;

    }

    /**
     * Graba en un fichero interno los datos leidos de
     */
    public void save() {
        int value;

        String tag = mEdit_t.getEditableText().toString();
        String value_s = mEdit_n.getEditableText().toString();
        String filename = mEdit_f.getEditableText().toString();

        if (value_s != null)
            try {
                value = Integer.valueOf(value_s);
            } catch (NumberFormatException es) {
                value = 0;
            }
        else
            value = 0;

        try {
            FileOutputStream os = getContext().openFileOutput(filename, Context.MODE_PRIVATE);

			/* Escritura sin serializacion*/
            //DataOutputStream dos = new DataOutputStream(os);
            //dos.writeUTF(texto);
            //dos.writeInt(n);
            //dos.flush();
            //dos.close();

			/*Escritura con serializacion*/
            Record data = new Record(tag, value);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(data);
            oos.flush();
            oos.close();
            /*Fin escritura co serializacion*/

            os.close();
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_saved),
                    Toast.LENGTH_SHORT).show();

        } catch (IOException ex) {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_error),
                    Toast.LENGTH_SHORT).show();

        }

    }

    public void saveExternal() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            String texto = mEdit_t.getEditableText().toString();
            String numero = mEdit_n.getEditableText().toString();
            String filename = mEdit_f.getEditableText().toString();

            int n;

            if (numero != null) {
                try {
                    n = Integer.valueOf(numero);
                } catch (NumberFormatException es) {
                    n = 0;
                }
            }else
                n = 0;

            try {
                File path = getContext().getExternalFilesDir(null);

                if (path != null) {
                    File file = new File(path, filename);

                    FileOutputStream os = new FileOutputStream(file, true);// el
                    // parametro
                    // true
                    // indica
                    // que
                    // es
                    // para
                    // anadir
                    DataOutputStream dos = new DataOutputStream(os);
                    dos.writeUTF(texto);
                    dos.writeInt(n);
                    dos.close();
                    os.close();

                    Toast.makeText(getContext(),
                            getResources().getString(R.string.toast_saved),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException ex) {
                Snackbar.make(mFragmentView, getResources().getString(R.string.toast_error)+" "+ex.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Toast.makeText(getContext(),
//                        getResources().getString(R.string.toast_error)+" "+ex.getMessage(),
//                        Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_error_nomedia),
                    Toast.LENGTH_SHORT).show();
    }

    public void load() {

        String texto = "";

        try {
            String filename = mEdit_f.getEditableText().toString();
            FileInputStream is = getContext().openFileInput(filename);
	
			/*Lectura con serializacion */
            ObjectInputStream ois = new ObjectInputStream(is);
            Record data = null;
            try {
                boolean end = false;
                while (!end) {
                    try {

                        data = (Record) ois.readObject();
                        texto = texto + data.toString() + "\r\n";
                    } catch (EOFException ex) {
                        end = true;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            is.close();
            resultado.setText(texto);
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_loaded),
                    Toast.LENGTH_SHORT).show();

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_error)+" "+ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void loadExternal() {

        String texto = "";

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            try {
                String filename = mEdit_f.getEditableText().toString();

                File path = getContext().getExternalFilesDir(null);

                if (path != null) {
                    File file = new File(path, filename);

                    FileInputStream os = new FileInputStream(file);
                    DataInputStream dos = new DataInputStream(os);
                    int n = dos.available();
                    texto = "Leidos (" + n + " bytes)\r\n";
                    while (dos.available() > 0) {
                        texto = texto + " clave: " + dos.readUTF() + " valor:"
                                + dos.readInt() + "\r\n";
                    }

                    resultado.setText(texto);

                    dos.close();
                    os.close();

                    Toast.makeText(getContext(),
                            getResources().getString(R.string.toast_loaded),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException ex) {
                Toast.makeText(getContext(),
                        getResources().getString(R.string.toast_error),
                        Toast.LENGTH_SHORT).show();
            }

        } else
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_error_nomedia),
                    Toast.LENGTH_SHORT).show();
    }

    public void saveDatabase() {
        if (database != null) {
            database.addRecord(getData());
        }
    }

    public void loadDatabase() {

        List<Record> comentarios = database.getAllRecords();
        String texto = "";
        for (Record c : comentarios) {
            texto = texto + "\r\n" + c.getTag() + " valor=" + c.getValue();
        }
        resultado.setText(texto);
    }

    public Record getData() {

        int n = 0;

        String texto = mEdit_t.getEditableText().toString();
        String numero = mEdit_n.getEditableText().toString();

        if (numero != null)
            try {

                n = Integer.valueOf(numero);
            } catch (NumberFormatException es) {
                n = 0;
            }
        else
            n = 0;

        return new Record(texto, n);
    }

    @Override
    public void onDestroyView() {
        if (this.database != null)
            this.database.close();
        super.onDestroyView();
    }
}
