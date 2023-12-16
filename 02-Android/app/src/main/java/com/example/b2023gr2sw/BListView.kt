package com.example.b2023gr2sw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    var posicionItemSelecionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        //capturaremos el list view de la pantalla
        val listView = findViewById<ListView>(R.id.lv_list_view)
        //ahora tenemos un adaptador
        //los adaptadores luego los utilizaremos para nuestras interfaces
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1,arreglo)
        //se iguala el que creamos a ListView para que funciones de manera correcta
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener{
            anadirEntrenador(adaptador)
        }
    }
    fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>){
        arreglo.add(BEntrenador(1,"David","uapo"))
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llamamos a las opciones del manu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar->{
                mostrarSnackbar("${posicionItemSelecionado}")
                return true
            }
            R.id.mi_eliminar->{
                mostrarSnackbar("${posicionItemSelecionado}")
                //abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }
    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.lv_list_view),
            texto, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
}