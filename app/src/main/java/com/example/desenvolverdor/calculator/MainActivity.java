package com.example.desenvolverdor.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.example.desenvolverdor.calculator.Database.DatabaseControl;

import java.util.ArrayList;
import java.util.List;

import static com.example.desenvolverdor.calculator.R.id.parent;

public class MainActivity extends Activity implements  OnItemSelectedListener{

    private static final String TAG = "MainActivity";
    int[] a = new int[3];
    EditText tela;
   // EditText tela2,tela3;
    //String a;
    String impr ="";
    int i = 0;
    String[] pala;
   // private static String subject = "SomSub"; // Queue Name.You can
    EditText nameEt, urlEt;
    Spinner spinner;
    String server = "";
    final DatabaseControl db = new DatabaseControl(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tela = (EditText) findViewById(R.id.edttela);
        ArrayList<Server> l = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        l = db.getServerCom();
        //for(int i=0;i<l.size();i++)
         //   System.out.println("Nome:"+l.get(i).getName()+"\nHost:"+l.get(i).getHost());
        String variavel = db.insertData("Local","localhost");
        loadSpinnerdata();

        findViewById(R.id.btnhelp).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Consulta.class);
                startActivity(intent);
            }
        });
        /*
                
        try {
            arrayServer = db.getServer();
            ArrayAdapter<Server> adpter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayServer);
            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setAdapter(adpter);

        }catch (SQLException e){
            Log.d(TAG,"uhakdshkjd");
        }
        nome = spinner.getSelectedItem().toString();
          spinner.setOnItemClickListener(new AdapterView.OnItemSelectedListener(){

        public void O

         }
        //        spinner.setAdapter((SpinnerAdapter) cursor);

        //final String address = (""+adress);
       // tela2 = (EditText) findViewById(R.id.edtteste);
        //tela3 = (EditText) findViewById(R.id.edtteste1);
        */
        findViewById(R.id.addserver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog , (ViewGroup) findViewById(R.id.rootParent));
                //layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.

                nameEt = (EditText) layout.findViewById(R.id.nome);
                urlEt = (EditText) layout.findViewById(R.id.host);
                urlEt.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

               final Intent intent= getIntent();
                // custom dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Novo Server:");
                builder.setView(layout);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();

                Button sendButton = (Button) layout.findViewById(R.id.dialogButtonOK);
                Button cancelButton = (Button) layout.findViewById(R.id.dialogButtonCancelar);
                sendButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        System.out.println("Antes de Salvar::  "+nameEt.getText().toString()+"\n");
                        String name = nameEt.getText().toString();
                        String host = urlEt.getText().toString();

                        String resul = db.insertData(name,host);
                        Toast.makeText(MainActivity.this,resul,Toast.LENGTH_SHORT).show();
                        loadSpinnerdata();
                        dialog.dismiss();

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        findViewById(R.id.btnmult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impr+="*";
               // impr="";
                tela.setText(impr);
          //      oper=3;
            //    va=1;

            }
        });
        findViewById(R.id.btnsoma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impr+="+";
                //tela3.setText(impr);
                //impr="";
                tela.setText(impr);
              //  va=1;
               // oper=1;
            }
        });
        findViewById(R.id.btndiv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impr+="/";
                //tela3.setText(impr);
                //impr="";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btnsub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impr+="-";
                //tela3.setText(impr);
                //impr="";
                //impr="";
                tela.setText(impr);
                //va=1;
                //oper=2;
            }
        });
        findViewById(R.id.btnigual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               tela.setText(enviar(impr));
                impr= tela.getText().toString();

            }
        });
       /* findViewById(R.id.btnlimpar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                impr="";
                tela.setText(impr);
                Toast.makeText(getApplicationContext(),"Tela Limpa",Toast.LENGTH_SHORT).show();
            }
        });*/

       findViewById(R.id.btnlimparletra).setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View v){
                limpartela();
                return false;
            }
        });
        findViewById(R.id.btnlimparletra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(impr=="")
                    Toast.makeText(getApplicationContext(),"Operação Invalida",Toast.LENGTH_SHORT).show();
                else
                    liparletra(impr);

            }
        });
        //---------------------------Numeros Romanos-----------------------------------------
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "1";
                tela.setText(impr);

            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "2";
                tela.setText(impr);

            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "3";
                tela.setText(impr);

            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "4";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "5";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "6";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "7";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "8";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "9";
                tela.setText(impr);
            }
        });
        findViewById(R.id.btn0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impr += "0";
                tela.setText(impr);
            }
        });


    }


    private void loadSpinnerdata() {

        DatabaseControl db = new DatabaseControl(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getServer();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private String enviar(String a) {
        int qtd = a.length();
        pala = new String[qtd];
        String ip="";

        if(a.equals("")){
            Toast.makeText(this,"Digite Algum Numero!",Toast.LENGTH_SHORT).show();
            return "";
        }
        else {
            ArrayList<Server> l = new ArrayList<>();
            DatabaseControl db = new DatabaseControl(this);
            l = db.getServerCom();
            for (int i = 0; i < l.size(); i++) {
                if (server.equals(l.get(i).getName()))
                    ip = l.get(i).getHost();
            }

            //cont =0;
            String[] valor = new String[3];
            valor[0] = "";
            valor[2] = "";
            int j = 0;
            Log.v(TAG, "Aquiiiiiiiiiiiii: " + ip + "    :: " + a);
            //Recebe os caracteres da palavra em um vetor de string
            for (int i = 0; i < qtd; i++) {
                pala[i] = Character.toString(a.charAt(i));
                if (pala[i].equals("+") || pala[i].equals("-") || pala[i].equals("*")) {
                    valor[1] = pala[i];
                    j = 2;
                } else if (pala[i].equals("/")) {
                    valor[1] = "div";
                    j = 2;
                } else
                    valor[j] += pala[i];
            }
            if (ip.equals("localhost")) {
                return somalocal(valor[0], valor[1], valor[2]);
            } else {
                Log.d(TAG, "Oper:" + valor[0] + valor[1] + valor[2]);
                AcessoRest ar = new AcessoRest();
                return ar.chamadaGet("http://" + ip + ":8080/FazendaWebService/webresources/Fazenda/Usario/Calculo/" + valor[0] + "/" + valor[1] + "/" + valor[2]);
            }
        }
    }

    private String somalocal(String s, String s1, String s2) {
        if(s1.equals("+"))
            return Double.parseDouble(s)+Double.parseDouble(s2)+"";
        if(s1.equals("-"))
            return Double.parseDouble(s)-Double.parseDouble(s2)+"";
        if(s1.equals("*"))
            return Double.parseDouble(s)*Double.parseDouble(s2)+"";
        else
            return Double.parseDouble(s)/Double.parseDouble(s2)+"";
    }

    private void liparletra(String a) {
        impr="";
        Log log = null;
        int qtd = a.length()-1;
        pala = new String[qtd];
        for(i=0;i<qtd;i++ ) {
            log.v(TAG, a.charAt(i) + " ");
            pala[i] = Character.toString(a.charAt(i));
        }
        for (i=0;i<qtd;i++)
            impr+=pala[i];

        tela.setText(impr);

    }
    private void limpartela(){
        impr="";
        tela.setText(impr);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        server = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Servidor Selecionado: " + server,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
