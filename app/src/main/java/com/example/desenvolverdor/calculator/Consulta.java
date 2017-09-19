package com.example.desenvolverdor.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desenvolverdor.calculator.Database.CreateDatabase;
import com.example.desenvolverdor.calculator.Database.DatabaseControl;


/**
 * Created by desenvolverdor on 03/05/17.
 */

public class Consulta extends Activity {
    private ListView lista;
    TextView test1;
    //final Context context = this;

    DatabaseControl crud;
    Cursor cursor;
    static String codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta);
        //
        gerarlist();
        /*test1 = (TextView)findViewById(R.id.nome);
        try {
            JSONObject j = new JSONObject();
            test1.setText(j.getString("nome"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                             cursor.moveToPosition(position);
                                             codigo = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.ID));
                                             cursor = crud.carregaDadoById(Integer.parseInt(codigo));

                                             //Preparing views
                                             LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                             final View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.rootParent));
                                             //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                                             final EditText nameEt = (EditText) layout.findViewById(R.id.nome);
                                             final EditText urlEt = (EditText) layout.findViewById(R.id.host);

                                             if (cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.NAME_USER)).equals("Local")) {
                                                 gerarlist();
                                                 Toast.makeText(Consulta.this, "Impossivel Alterar Servidor Local", Toast.LENGTH_SHORT).show();
                                             } else {
                                                 nameEt.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.NAME_USER)));
                                                 urlEt.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.HOST)));
                                                 alter(layout, nameEt, urlEt,Integer.parseInt(codigo));
                                             }
                                         }
                                     });
            //Building dialog

                /*final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.setView(R.id.layout);
                }
                dialog.setTitle("Title...");

                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Android custom dialog example!");
                final EditText edtnome = (EditText)dialog.findViewById(R.id.nome);
                final EditText edthost = (EditText)dialog.findViewById(R.id.host);


                edtnome.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.NAME_USER)));
                edthost.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.HOST)));

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                */
                //dialog.show();

                //editEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.HOST)));


    }
    public void gerarlist(){
        crud = new DatabaseControl(getBaseContext());
        cursor = crud.carregaDados();
        lista = (ListView)findViewById(R.id.listView);
        list();
    }
    public void alter(View layout, final EditText name2, final EditText host, final int cod){
        AlertDialog.Builder builder = new AlertDialog.Builder(Consulta.this);
        builder.setTitle("Altera o Servidor:");
        builder.setView(layout);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();

        Button sendButton = (Button) layout.findViewById(R.id.dialogButtonOK);
        Button cancelButton = (Button) layout.findViewById(R.id.dialogButtonCancelar);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name2.getText().toString();
                String imgUrl = host.getText().toString();
                if (name.isEmpty() || imgUrl.isEmpty()) {
                    gerarlist();
                    Toast.makeText(Consulta.this, "Ação Invalida!", Toast.LENGTH_SHORT).show();
                } else {
                    crud.alteraRegistro(cod, name, imgUrl);
                    gerarlist();
                }
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarlist();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void list() {

        DatabaseControl crud2 = new DatabaseControl(getBaseContext());
        Cursor cursor2 = crud2.carregaDados();

        String[] nomeCampos = new String[] {CreateDatabase.ID,CreateDatabase.HOST,CreateDatabase.NAME_USER};
        int[] idViews = new int[] {R.id.idid, R.id.idHost, R.id.nome};
        // String s = crud.listarPostgres();
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.estilizar_listview_usuario,cursor2,nomeCampos,idViews, 0);
        lista.setAdapter(adaptador);
    }
}
