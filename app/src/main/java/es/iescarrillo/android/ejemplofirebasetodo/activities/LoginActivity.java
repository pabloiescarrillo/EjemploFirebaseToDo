package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.android.ejemplofirebasetodo.R;

public class LoginActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla
    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp, btnGoogle;

    // TODO: declaración variables del Intent

    // TODO: declaración servicios a usar en la Activity

    // Variables de configuración de Google SignIn
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    // Constantes
    private static final int REQ_CODE_GOOGLE_SIGN_IN = 2;

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadComponents();

        /* TODO: evento del botón de Login => comprobar email y pass, en caso correcto enviar
        a la Activity de Main, en caso contrario mostrar mensaje de error.
        No olvides comprobar que los campos sean blancos
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().isBlank()){
                    Toast.makeText(LoginActivity.this, R.string.email_blank, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etPassword.getText().toString().isBlank()){
                    Toast.makeText(LoginActivity.this, R.string.password_blank, Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth instanceAuth = FirebaseAuth.getInstance();
                instanceAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // TODO: evento del botón de SignUp => enviar a la Activity de inserción indicando que el editMode es falso
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InsertOrEditPersonActivity.class);
                intent.putExtra("editMode", false);
                startActivity(intent);
                finish();
            }
        });

        // TODO: evento del botón de SignUpGoogle => llamar al intent de Google
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, REQ_CODE_GOOGLE_SIGN_IN);
            }
        });
    }

    // TODO: registrar el usuario en el módulo de FirebaseAuth y en caso de que ya exista mostrar la MainActivity
    // en caso contrario, mostrar la pantalla de registro con los datos precargados
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE_GOOGLE_SIGN_IN){
            try {
                // Guardamos datos de la cuenta de Google seleccionada
                GoogleSignInAccount googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
                AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                FirebaseAuth instanceAuth = FirebaseAuth.getInstance();
                instanceAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser = instanceAuth.getCurrentUser();

                            /* Vamos a comprobar si existe una persona en el módulo de
                            * RealTimeDatabase con el UID del currentUser
                            * en caso de que no exista mostraremos la pantalla de registro con los
                            * datos precargados con los que hemos obtenido de su cuenta de Google
                            * en caso de que si exita mostraremos la MainActivity
                            */
                            FirebaseDatabase.getInstance().getReference("persons")
                                    .orderByChild("uid").equalTo(currentUser.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            // Tenemos comprobar si el snapshot contine algún valor
                                            if(snapshot.exists()){
                                                // El usuario ya está logueado
                                                Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else { // No ha encontrado ninguna persona con el UID
                                                Intent intent = new Intent(LoginActivity.this, InsertOrEditPersonActivity.class);
                                                intent.putExtra("editMode", false);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                        } else {
                            Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (Exception e){
                Log.e("LoginActivity - Error", e.getMessage().toString());
            }

        }
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGoogle = findViewById(R.id.btnGoogle);

        // Configuramos las opciones de registro
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        // Inicializar variable del cliente web, necesitamos el contexto y las opciones
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
    }
}