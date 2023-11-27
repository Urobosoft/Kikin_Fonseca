package com.example.crud_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
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

public class Cambios extends AppCompatActivity implements View.OnClickListener {
    EditText etClave, etNombre, etPromedio;
    Button btnBuscar, btnRegresar;
    Spinner carrera;
    RadioGroup turnito;
    RadioButton matu, vesp;
    ArrayAdapter adaptadito;
    String [] papulinces = {"Selecciona", "Máquinas SA", "Mecatrónica", "Sistemas Digitales", "Programación"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        etClave = findViewById(R.id.Clave);
        etNombre = findViewById(R.id.Nombre);
        etPromedio = findViewById(R.id.Promedio);
        btnBuscar = findViewById(R.id.btnConsultar);
        btnRegresar = findViewById(R.id.btnRegresar);
        carrera = findViewById(R.id.Carrera);
        turnito = findViewById(R.id.Turnos);
        matu = findViewById(R.id.Matutino);
        vesp = findViewById(R.id.Vespertino);
        adaptadito = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, papulinces);
        carrera.setAdapter(adaptadito);


        btnBuscar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);

        etNombre.setEnabled(false);
        etPromedio.setEnabled(false);
        carrera.setEnabled(false);
        turnito.setEnabled(false);
        matu.setEnabled(false);
        vesp.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        String cadenita = ((Button)v).getText().toString();
        if(cadenita.equals("Regresar")){
            finish();
        }else{
            if(cadenita.equals("Buscar")){
                Base admin = new Base(this, "administracion", null, 1);
                SQLiteDatabase basedatos = admin.getReadableDatabase();
                String cod = etClave.getText().toString();
                Cursor fila = basedatos.rawQuery("select nombre, promedio, carrera, turno from articulos where codigo = " + cod, null);
                if(fila.moveToFirst()){
                    etNombre.setEnabled(true);
                    etPromedio.setEnabled(true);
                    carrera.setEnabled(true);
                    turnito.setEnabled(true);
                    matu.setEnabled(true);
                    vesp.setEnabled(true);
                    String carreraSeleccionada = fila.getString(2);
                    int posicionCarrera = adaptadito.getPosition(carreraSeleccionada);
                    if (posicionCarrera >= 0) {
                        carrera.setSelection(posicionCarrera);
                    }
                    String turnoSeleccionado = fila.getString(3);
                    if ("Matutino".equals(turnoSeleccionado)) {
                        turnito.check(R.id.Matutino);
                    } else if ("Vespertino".equals(turnoSeleccionado)) {
                        turnito.check(R.id.Vespertino);
                    }
                    etNombre.setText(fila.getString(0));
                    etPromedio.setText(fila.getString(1));
                    btnBuscar.setText("Cambiar");
                }
            } else{
                if(cadenita.equals("Cambiar")){
                    Base admin = new Base(this, "administracion", null, 1);
                    SQLiteDatabase basedatos = admin.getReadableDatabase();
                    String cod = etClave.getText().toString();
                    String nom = etNombre.getText().toString();
                    String pro = etPromedio.getText().toString();
                    String carreraSeleccionada = carrera.getSelectedItem().toString();
                    int idTurnoSeleccionado = turnito.getCheckedRadioButtonId();
                    RadioButton radioButtonSeleccionado = findViewById(idTurnoSeleccionado);
                    String turnoSeleccionado = radioButtonSeleccionado.getText().toString();
                    if(!cod.isEmpty() && !nom.isEmpty()
                            && !pro.isEmpty() && !carreraSeleccionada.isEmpty() && !turnoSeleccionado.isEmpty()){
                        ContentValues registro = new ContentValues();
                        registro.put("codigo", cod);
                        registro.put("nombre", nom);
                        registro.put("carrera", carreraSeleccionada);
                        registro.put("promedio", pro);
                        registro.put("turno", turnoSeleccionado);
                        int cantidad = basedatos.update("articulos", registro,
                                "codigo=" + cod, null);
                        if(cantidad == 1){
                            Toast.makeText(getApplicationContext(),
                                    "Modificaciones realizadas uwu", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Tu mamá debe llenar los campos", Toast.LENGTH_LONG).show();
                        }
                        etNombre.setEnabled(false);
                        etPromedio.setEnabled(false);
                        carrera.setEnabled(false);
                        turnito.setEnabled(false);
                        matu.setEnabled(false);
                        vesp.setEnabled(false);

                        etClave.setText("");
                        etNombre.setText("");
                        etPromedio.setText("");
                        turnito.clearCheck();
                        carrera.setSelection(0);

                        btnBuscar.setText("Buscar");
                        basedatos.close();
                    }
                }
            }
        }
    }
}