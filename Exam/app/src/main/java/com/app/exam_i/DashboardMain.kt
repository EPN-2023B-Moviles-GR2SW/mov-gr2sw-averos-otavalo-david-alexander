package com.app.exam_i

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.exam_i.model.Cliente
import com.app.exam_i.model.Data
import java.time.LocalDate


class DashboardMain : AppCompatActivity() {
    val data = Data.datos
    var positionClienteSelected = -1
    lateinit var adapter: ArrayAdapter<Cliente>

    val callbackContenido =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === RESULT_OK) {
                if (result.data != null) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_main)

        val listView = findViewById<ListView>(R.id.lv_ListaCliente)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val btnCrearCliente = findViewById<Button>(R.id.btn_CrearCliente)
        btnCrearCliente.setOnClickListener {
            val intent = Intent(this, pantallaEditarCliente::class.java)
            intent.putExtra("positionDirectorSelected", -1)
            callbackContenido.launch(intent)
        }
        registerForContextMenu(listView)

        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore
            .setOnClickListener {
                irActividad(IFirestore::class.java)
            }
    }
    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        // Enviar parametros (solamente variables primitivas)
        intentExplicito.putExtra("cedula", "1784467329")
        intentExplicito.putExtra("numeroAfiliado", 23424)
        intentExplicito.putExtra("altura", 1.60)
        intentExplicito.putExtra("fechaCumpleanos", "2004-07-08")
        intentExplicito.putExtra("sexo", false)


        intentExplicito.putExtra("cliente",
            Cliente(
                "1784467329",
                23424,
                1.60,
                LocalDate.of(2004, 7, 8),
                false)
        )

        callbackContenidoIntentExplicito.launch(intentExplicito)
    }
    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Logica Negocio
                    val data = result.data
                    mostrarSnackbar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }
    fun mostrarSnackbar(texto:String){
        Toast.makeText(
            this,
            texto,
            Toast.LENGTH_LONG
        ).show()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menucliente, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        positionClienteSelected = info.position
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarCliente -> {
                val intent = Intent(this, pantallaEditarCliente::class.java)
                intent.putExtra("positionClienteSelected", positionClienteSelected)
                callbackContenido.launch(intent)
                true
            }

            R.id.eliminarCliente -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Desear eliminar al cliente?")
                builder.setPositiveButton("Sí") { dialog, which ->
                    data.removeAt(positionClienteSelected)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        applicationContext,
                        "Cliente eliminado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        "Operación cancelada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.create().show()
                true
            }

            R.id.verFactura -> {
                val intent = Intent(this, DashboardFacturas::class.java)
                intent.putExtra("positionClienteSelected", positionClienteSelected)
                startActivity(intent)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}