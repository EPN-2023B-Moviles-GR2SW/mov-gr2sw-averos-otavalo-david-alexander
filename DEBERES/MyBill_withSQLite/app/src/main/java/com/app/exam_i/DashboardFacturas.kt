package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.app.exam_i.persistence.SQLite
import com.app.exam_i.model.Factura

class DashboardFacturas : AppCompatActivity() {
    val facturaDB = SQLite(this)
    val clienteDB = SQLite(this)
    var positionClienteSelected = -1
    var positionFacturaSelected = -1
    lateinit var adapter: ArrayAdapter<Factura>

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
        setContentView(R.layout.activity_dashboard_facturas)
        positionClienteSelected = intent.getIntExtra("positionClienteSelected", -1)

        val txtDirector = findViewById<TextView>(R.id.txtViewNombreCliente)
        txtDirector.text =
            "Factura pertenece a ${clienteDB.obtenerCliente()[positionClienteSelected].cedula}"
        val listView = findViewById<ListView>(R.id.lv_ListaFactura)


        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            clienteDB.obtenerCliente()[positionClienteSelected].facturas
        )
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val btnCrearPelicula = findViewById<TextView>(R.id.btn_CrearFactura)
        btnCrearPelicula.setOnClickListener {
            val intent = Intent(this, pantallaEditarFactura::class.java)
            intent.putExtra("positionFacturaSelected", -1)
            intent.putExtra("positionClienteSelected", positionClienteSelected)
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
        inflater.inflate(R.menu.menufactura, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        positionFacturaSelected = info.position
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarFactura -> {
                val intent = Intent(this, pantallaEditarFactura::class.java)
                intent.putExtra("positionFacturaSelected", positionFacturaSelected)
                intent.putExtra("positionClienteSelected", positionClienteSelected)
                callbackContenido.launch(intent)
                true
            }

            R.id.eliminarFactura -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Desear eliminar la factura?")
                builder.setPositiveButton("Sí") { dialog, which ->
                    clienteDB.obtenerCliente()[positionClienteSelected].facturas.removeAt(positionFacturaSelected)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Factura eliminada", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
                }
                builder.create().show()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}