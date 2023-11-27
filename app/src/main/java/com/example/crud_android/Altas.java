package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Altas extends AppCompatActivity implements View.OnClickListener {
    EditText clave, nombre, promedio;
    Spinner carrera;
    RadioGroup turnito;
    RadioButton matu, vesp;
    Button regresar, añadir;
    ArrayAdapter adaptadito;
    String [] papulinces = {"Selecciona", "Máquinas SA", "Mecatrónica", "Sistemas Digitales", "Programación"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        clave = findViewById(R.id.Clave);
        nombre = findViewById(R.id.Nombre);
        promedio = findViewById(R.id.Promedio);
        turnito = findViewById(R.id.Turnos);
        matu = findViewById(R.id.Matutino);
        vesp = findViewById(R.id.Vespertino);
        regresar = findViewById(R.id.btnRegresarMenu);
        añadir = findViewById(R.id.btnAgregarProducto);
        carrera = findViewById(R.id.Carrera);
        adaptadito = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, papulinces);
        carrera.setAdapter(adaptadito);

        regresar.setOnClickListener(this);
        añadir.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String cadenita = button.getText().toString();
        if(cadenita.equals("Agregar")){
            Base admin = new Base(this, "administracion",null,1);
            SQLiteDatabase basedatos = admin.getWritableDatabase();
            String cod = clave.getText().toString();
            String nom = nombre.getText().toString();
            String pro = promedio.getText().toString();
            String carreraSeleccionada = carrera.getSelectedItem().toString();
            int idTurnoSeleccionado = turnito.getCheckedRadioButtonId();
            RadioButton radioButtonSeleccionado = findViewById(idTurnoSeleccionado);
            String turnoSeleccionado = radioButtonSeleccionado.getText().toString();


            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("nombre", nom);
            registro.put("carrera", carreraSeleccionada);
            registro.put("promedio", pro);
            registro.put("turno", turnoSeleccionado);

            basedatos.insert("articulos", null,registro);
            basedatos.close();
            Toast.makeText(this, "dado de alta",Toast.LENGTH_SHORT).show();
            limpieza();
        }else{
            if(cadenita.equals("Regresar")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Pipipi",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void limpieza(){
        clave.setText("");
        promedio.setText("");
        nombre.setText("");
        turnito.clearCheck();
        carrera.setSelection(0);
    }
}