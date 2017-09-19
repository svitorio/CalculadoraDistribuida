package com.example.desenvolverdor.calculator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.desenvolverdor.calculator.Server;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by desenvolverdor on 03/05/17.
 */

public class DatabaseControl{

    private SQLiteDatabase db;
    private CreateDatabase database;

    public DatabaseControl(Context context) {
            database = new CreateDatabase(context);
    }
    public String insertData(String name, String host){
        ///int birthday
        ContentValues valores = new ContentValues();
        long resultado;

        db = database.getWritableDatabase();
        valores.put(CreateDatabase.NAME_USER, name);
        valores.put(CreateDatabase.HOST, host);
      //  valores.put(CreateDatabase.DATE_BIRTHDAY, birthday);

        System.out.println("Salvou::  "+name+"\nHost::  "+host);
        resultado = db.insert(CreateDatabase.TABLE, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else {
            //insertDataBaseExterno(name, email, phone);
            return "Registro Inserido com sucesso";
        }
    }

  /*  public void insertDataBaseExterno(String name, String email,long phone){

        final AcessoRest ar = new AcessoRest();
        String chamadaWs = "http://10.180.42.37:8080/FazendaWebService/webresources/Fazenda/Usario/inserir/"+name+"/"+email+"/"+phone;
        ar.chamadaGet(chamadaWs);
    }*/

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {database.ID,database.HOST,database.NAME_USER};
        db = database.getReadableDatabase();
        cursor = db.query(database.TABLE, campos, null, null, null, null, null, null);


       if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    /*public String (){                                                                                                                                                                                                                                                                                                                                                                           listarPostgres(){
        AcessoRest ar = new AcessoRest();
        String chamadaWS = "http://10.180" +
                "42.37:8080/FazendaWebService/webresources/Fazenda/Usario/list";
        Log.v("asd",ar.chamadaGet(chamadaWS));
        return ar.chamadaGet(chamadaWS);

    }*/
    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos =  {database.ID,database.HOST,database.NAME_USER};
        String where = CreateDatabase.ID + "=" + id;
        db = database.getReadableDatabase();
        cursor = db.query(CreateDatabase.TABLE,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public List<String> getServer(){

        List<String> turmas = new ArrayList<>();
        String[] campos =  {database.NAME_USER,database.HOST,};

        db = database.getReadableDatabase();
        Cursor cursor = db.query(database.TABLE, campos, null, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                String nome = cursor.getString(0);
                //String host = cursor.getString(1);
                //Server turma = new Server(nome,host);
                turmas.add(nome);
            }while(cursor.moveToNext());
        }
        return turmas;
    }
    public ArrayList<Server> getServerCom(){

        ArrayList<Server> turmas = new ArrayList<>();
        String[] campos =  {database.NAME_USER,database.HOST};

        db = database.getReadableDatabase();
        Cursor cursor = db.query(database.TABLE, campos, null, null, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                String nome = cursor.getString(0);
                String host = cursor.getString(1);
                Server turma = new Server(nome,host);
                turmas.add(turma);
            }while(cursor.moveToNext());
        }
        return turmas;
    }
    public void alteraRegistro(int id, String name, String host){
        ContentValues valores;
        String where;

        db = database.getWritableDatabase();

        where = CreateDatabase.ID + "=" + id;

        valores = new ContentValues();
        valores.put(CreateDatabase.NAME_USER, name);
        valores.put(CreateDatabase.HOST, host);

        db.update(CreateDatabase.TABLE,valores,where,null);
        db.close();
    }
    public void deletaRegistro(int id){
        String where = CreateDatabase.ID + "=" + id;
        db = database.getReadableDatabase();
        db.delete(CreateDatabase.TABLE,where,null);
        db.close();
    }
}
