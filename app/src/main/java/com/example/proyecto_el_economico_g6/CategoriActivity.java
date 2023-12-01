package com.example.proyecto_el_economico_g6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyecto_el_economico_g6.Config.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriActivity extends AppCompatActivity {

    public List<Categoria> listadecategoria = new ArrayList<Categoria>();
    ArrayAdapter<Categoria> arrayAdapterCategoria;
    EditText txtcategoria, txtdescripcion;
    Button btncrear, btnactualizar, btneliminar, btnregresar, btnLimpiarCate;
    ListView listViewcategorias;

    Categoria categoriaSelected;


    FirebaseFirestore mFirestore;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categori);


        txtcategoria = findViewById(R.id.txtcategoria);
        txtdescripcion = findViewById(R.id.txtdescripcion);
        btncrear = findViewById(R.id.btncrear);
        btnactualizar = findViewById(R.id.btnactualizar);
        btneliminar = findViewById(R.id.btneliminar);
        btnregresar = findViewById(R.id.btnregresar);
        btnLimpiarCate = findViewById(R.id.btnLimpiarCate);
        listViewcategorias = findViewById(R.id.listcategorias);

        progressDialog = new ProgressDialog(this);

        mFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //para persistencia de datos
       // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

        listarCategorias();

        //evento click en lista
        listViewcategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoriaSelected = (Categoria) parent.getItemAtPosition(position);
                txtcategoria.setText(categoriaSelected.getCategoria());
                txtdescripcion.setText(categoriaSelected.getDescripcion());
            }
        });

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCategoria();
            }
        });

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoriActivity.this, InicioActivity.class));
            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria c = new Categoria();
                c.setId(categoriaSelected.getId());
                c.setCategoria(txtcategoria.getText().toString().trim());
                c.setDescripcion(txtdescripcion.getText().toString().trim());
                databaseReference.child("Categoria").child(c.getId()).setValue(c);
                Toast.makeText(CategoriActivity.this,"Datos Actualizados!", Toast.LENGTH_LONG).show();
                limpiar();
            }
        });

        btnLimpiarCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });

    }

    private void listarCategorias() {
        databaseReference.child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listadecategoria.clear();
                for(DataSnapshot objSnapshot : snapshot.getChildren()){
                    Categoria c = objSnapshot.getValue(Categoria.class); //*retorna todo el objeto llamdao c*/
                    listadecategoria.add(c);

                    arrayAdapterCategoria = new ArrayAdapter<Categoria>( CategoriActivity.this, android.R.layout.simple_list_item_1,listadecategoria);
                    listViewcategorias.setAdapter(arrayAdapterCategoria);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void crearCategoria() {

        String categoria = txtcategoria.getText().toString();
        String descripcion = txtdescripcion.getText().toString();
        String foto = "";

        if (categoria.equals("")) {
            txtcategoria.setError("Debe escribir una categoria!");
        } else if (descripcion.equals("")) {
            txtdescripcion.setError("Ingrese una descripcion!");
        }else{
            progressDialog.setMessage("Registrando Categoria");
            progressDialog.setTitle("Registro");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Categoria c = new Categoria();
            c.setId(UUID.randomUUID().toString());
            c.setCategoria(categoria);
            c.setDescripcion(descripcion);
            c.setFoto(foto);

            databaseReference.child("Categoria").child(c.getId()).setValue(c);

            progressDialog.dismiss();

            Toast.makeText(CategoriActivity.this,"Registro Exitso!", Toast.LENGTH_SHORT).show();
            limpiar();

        }

    }

    private void limpiar() {
        txtcategoria.requestFocus();
        txtcategoria.setText("");
        txtdescripcion.setText("");
    }




}