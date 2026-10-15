package com.example.registro_asistencia

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    //Declarar las variables
    private lateinit var tvMensaje: TextView
    private lateinit var etxtNombre: EditText
    private lateinit var etxtMatricula: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var pbCarga: ProgressBar

    private lateinit var btnTemaLYTO: Button

    private var oscuroLYTO = false

    private var contadorAsistentes = 0

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

    //Zona Oncreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        //conectar las vistas
        tvMensaje = findViewById(R.id.tvMensaje)
        etxtNombre = findViewById(R.id.etxtNombre)
        etxtMatricula = findViewById(R.id.etxtMatricula)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        pbCarga = findViewById(R.id.pbCarga)

        btnTemaLYTO = findViewById(R.id.btnTema)

        btnTemaLYTO.setOnClickListener {
            cambiarTemaLYTO()
        }

        //crear listeners
        btnRegistrar.setOnClickListener { registrar() }
        btnLimpiar.setOnClickListener { limpiar() }

    }

    //funciones
    private fun registrar(){
        val nombre = etxtNombre.text.toString().trim()
        val matricula = etxtMatricula.text.toString().trim()

        if (nombre.isEmpty()){
            Toast.makeText(this, getString(R.string.val_nombre_vacioLYTO), Toast.LENGTH_SHORT).show()
            return
        }

        else if(nombre.length < 3 ){
            Toast.makeText(this, getString(R.string.val_nombre_cortoLYTO), Toast.LENGTH_SHORT).show()
            return
        }

        if (matricula.isEmpty()){
            Toast.makeText(this, getString(R.string.val_matricula_vaciaLYTO), Toast.LENGTH_SHORT).show()
            return
        }
        else if(matricula.length < 7 ){
            Toast.makeText(this, getString(R.string.val_matricula_largoLYTO), Toast.LENGTH_SHORT).show()
            return
        }

        // 2. INCREMENTO
        contadorAsistentes++

        val registroCompleto = "$nombre $matricula"

        pbCarga.visibility = View.VISIBLE
        tvMensaje.text = ""

        // Deshabilitar los DOS botones antes de la carga
        btnRegistrar.isEnabled = false
        btnLimpiar.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            pbCarga.visibility = View.GONE

            // PUNTO EXTRA
            tvMensaje.text = getString(R.string.msg_registradoLYTO, nombre, matricula, contadorAsistentes)

            // CAMBIO DE TEXTO A "REGISTRAR OTRO"
            btnRegistrar.text = getString(R.string.btn_registrar_otroLYTO)

            // CORRECCIÓN: Reactivar los DOS botones al terminar
            btnRegistrar.isEnabled = true
            btnLimpiar.isEnabled = true
        }, 1500)
    }


    private fun limpiar() {
        etxtNombre.setText("")
        etxtMatricula.setText("")
        pbCarga.visibility = View.GONE
        tvMensaje.text = getString(R.string.msg_inicialLYTO)
        btnRegistrar.text = getString(R.string.btn_registrarLYTO)
        btnLimpiar.isEnabled = true
        etxtNombre.requestFocus()

    }

    private fun cambiarTemaLYTO() {
        oscuroLYTO = !oscuroLYTO

        if (oscuroLYTO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            btnTemaLYTO.text = getString(R.string.btn_tema_claroLYTO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            btnTemaLYTO.text = getString(R.string.btn_tema_oscuroLYTO)
        }
    }
}
