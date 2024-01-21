package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.app.exam_i.model.Cliente
import com.app.exam_i.model.Data
import android.app.AlertDialog
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast


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
}