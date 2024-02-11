package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {
    //en esta funcion debemos de registrar el resultado de la autenticacion
    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        // vemos que el resultado sea OK, ya que el usuario puede cancalar la autenticacion
        if (res.resultCode === RESULT_OK) {
            // el usuario no debe ser nulo
            if (res.idpResponse != null) {
                // Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }
    }
    fun seLogeo(
        res: IdpResponse
    ){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        //mostramos el boton de logout y ocultamos el de login
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        // cuando se logea por primera vez el usuario podemos registrar sus datos
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }
    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){
        /*
         usuario.email;
         usuario.phoneNumber;
         usuario.user.name;
         */
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                // Arreglo de PROVIDERS para logearse
                // EJ: Correo, Facebook, Twitter, Google,
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            // Construimos el intent de login
            val logearseIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            // RESPUESTA DEL INTENT DE LOGIN
            respuestaLoginAuthUi.launch(logearseIntent)
            // https://console.firebase.google.com/u/0/project/PROYECTO/authentication/settings
            // Authentication/Settings/UserActions/Email enumeration protection ( )
            // Unchecked!!
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }
        // Logica si destruye el aplicativo
        // la sesion se mantiene en el telefono si vamos a otra actividad y volvemos seguimos logeados
        // pero la logica de la interfaz no se mantiene por ello debemos volver a verificar si el usuario
        // esta logeado o no
        // aun si el usuario cierra la aplicacion y la vuelve a abrir seguira logeado
        // aun si el usuario apaga el telefono y lo vuelve a encender seguira logeado
        // aun si el usuario desinstala la aplicacion y la vuelve a instalar seguira logeado
        val usuario = FirebaseAuth.getInstance().currentUser
        if(usuario != null){
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
            val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogout.visibility = View.VISIBLE
            btnLogin.visibility = View.INVISIBLE
            tvBienvenido.text = usuario.displayName
        }
    }
    fun seDeslogeo(){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = "Bienvenido"
        //hacemos lo inverso a la funcion seLogeo en la parte de la interfaz
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        //cerramos la sesion con una funcion del SDK de Firebase
        FirebaseAuth.getInstance().signOut()
    }
}