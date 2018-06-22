package jislap1.lenovo.com.jplayfi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jislap1.lenovo.com.jplayfi.Objetos.FirebaseReferences;
import jislap1.lenovo.com.jplayfi.Objetos.Usuario;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText TexEmail, TexContraseña;
    TextView TexRecuperar, TexRegistrate;
    Button BotonIniciarSesion;
    private ProgressDialog progressDialog1;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TexEmail = (EditText) findViewById(R.id.edtEmail1);
        TexContraseña = (EditText) findViewById(R.id.edtContraseña1);
        TexRecuperar = (TextView) findViewById(R.id.txtRecuperar);
        TexRegistrate = (TextView) findViewById(R.id.txtRegistrate);
        BotonIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        progressDialog1 = new ProgressDialog(this);
        BotonIniciarSesion.setOnClickListener(this);
        TexRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registrar.class);
                startActivity(i);
            }
        });
    }

    private void loguearUsuarios() {
        String email = TexEmail.getText().toString().trim();
        String contraseña  = TexContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog1.setMessage("Iniciando Sesión...");
        progressDialog1.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Bienvenido: " + TexEmail.getText(), Toast.LENGTH_LONG).show();
                    Intent o = new Intent(getApplication(), MenuPrincipal.class);
                    startActivity(o);
                }else {
                    Toast.makeText(MainActivity.this, "Email o Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                }
                progressDialog1.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        loguearUsuarios();
    }
}
