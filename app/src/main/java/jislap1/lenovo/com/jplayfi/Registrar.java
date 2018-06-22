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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jislap1.lenovo.com.jplayfi.Objetos.FirebaseReferences;
import jislap1.lenovo.com.jplayfi.Objetos.Usuario;

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    EditText TextEmail, TextNombres, TextContraseña, TextConContraseña;
    Button BotonRegistrar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        TextEmail = (EditText) findViewById(R.id.edtEmail);
        TextNombres = (EditText) findViewById(R.id.edtNombres);
        TextContraseña = (EditText) findViewById(R.id.edtContraseña);
        TextConContraseña = (EditText) findViewById(R.id.edtConContraseña);
        BotonRegistrar = (Button) findViewById(R.id.btnRegistrar);
        progressDialog = new ProgressDialog(this);
        BotonRegistrar.setOnClickListener(this);

    }

    private void registrarUsuarios(){
        final String email = TextEmail.getText().toString().trim();
        final String nombres = TextNombres.getText().toString().trim();
        final String contraseña  = TextContraseña.getText().toString().trim();
        final String concontraseña  = TextConContraseña.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(nombres)){
            Toast.makeText(this,"Debe ingresar su Nombre Completo",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(contraseña)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(concontraseña)){
            Toast.makeText(this,"Vuelva a Ingresar su Contraseña en Confirmar Contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        if(contraseña.equals(concontraseña)){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if success
                    if (task.isSuccessful()) {
                        Toast.makeText(Registrar.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference rRef = database.getReference(FirebaseReferences.USUARIO_REFERENCE);
                        Usuario usuario = new Usuario(email, nombres, contraseña);
                        rRef.push().setValue(usuario);
                        Intent o = new Intent(getApplication(), MainActivity.class);
                        startActivity(o);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {//SI SE PRESENTA DUPLICIDAD DE EMAIL
                            Toast.makeText(Registrar.this, "Este email ya se encuentra registrado, intente con otro", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Registrar.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                        }
                    }
                    progressDialog.dismiss();
                }
            });


        }else{
            Toast.makeText(this, "No se ha podido Confirmar su Contraseña", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        registrarUsuarios();
    }
}
