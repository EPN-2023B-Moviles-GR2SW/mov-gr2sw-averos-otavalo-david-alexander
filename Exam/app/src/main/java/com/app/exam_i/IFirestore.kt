package com.app.exam_i

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.app.exam_i.model.Factura
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.Date

class IFirestore : AppCompatActivity() {
    var query: Query? = null
    //este es el arreglo que iremos modificando
    val arreglo: ArrayList<ICliente> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)
        // Configurando el list view
        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        // Botones
        // Crear Datos Prueba
        val botonDatosPrueba = findViewById<Button>(
            R.id.btn_fs_datos_prueba)
        botonDatosPrueba.setOnClickListener { crearDatosPrueba() }

        // Obtener documento
        val botonObtenerDocumento = findViewById<Button>(R.id.btn_fs_odoc)
        botonObtenerDocumento.setOnClickListener {
            consultarDocumento(adaptador)
        }

        // Crear datos
        val botonCrear = findViewById<Button>(R.id.btn_fs_crear)
        botonCrear.setOnClickListener { crearEjemplo() }


        // Boton Eliminar
        val botonFirebaseEliminar = findViewById<Button>(
            R.id.btn_fs_eliminar)
        botonFirebaseEliminar.setOnClickListener {
            eliminarRegistro() }
        // Empezar a paginar
        val botonFirebaseEmpezarPaginar = findViewById<Button>(
            R.id.btn_fs_epaginar)
        botonFirebaseEmpezarPaginar.setOnClickListener {
            query = null; consultarCiudades(adaptador);
        }
        // Paginar
        val botonFirebasePaginar = findViewById<Button>(
            R.id.btn_fs_paginar)
        botonFirebasePaginar.setOnClickListener {
            consultarCiudades(adaptador)
        }

        // Consultar indice compuesto
        val botonIndiceCompuesto = findViewById<Button>(
            R.id.btn_fs_ind_comp
        )
        botonIndiceCompuesto.setOnClickListener {
            consultarIndiceCompuesto(adaptador)
        }
    }
    fun anadirAArregloCliente(
        ciudad: QueryDocumentSnapshot
    ){
        // ciudad.id|
        val nuevoCliente = ICliente(
            ciudad.data.get("cedula").toString(),
            ciudad.data.get("numeroAfiliado").toString().toInt(),
            ciudad.data.get("altura").toString().toDouble(),
            ciudad.data.get("fechaCumpleanos").toString(),
            ciudad.data.get("sexo").toString().toBoolean()
        )
        arreglo.add(nuevoCliente)
    }
    fun crearEjemplo(){
        val db = Firebase.firestore
        val referenciaEjemploCliente = db
            .collection("ejemplo")

        val datosCliente = hashMapOf(
            "cedula" to "1784467329",
            "numeroAfiliado" to 23424,
            "altura" to 1.60,
            "fechaCumpleanos" to LocalDate.of(2004, 7, 8),
            "sexo" to false,
        )
        "facturas" to listOf(
            hashMapOf(
                "fecha" to LocalDate.of(2024, 11, 28),
                "numero" to 213,
                "pagado" to false,
                "valor" to 2384.03
            ),
            hashMapOf(
                "fecha" to LocalDate.of(2010, 7, 8),
                "numero" to 101,
                "pagado" to false,
                "valor" to 150.75
            )
        )

        // identificador quemado (crear/actualizar)
        referenciaEjemploCliente
            .document("12345678")
            .set(datosCliente)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
        // identificador quemado pero autogenerado con Date().time
        val identificador = Date().time
        referenciaEjemploCliente // (crear/actualizar)
            .document(identificador.toString())
            .set(datosCliente)
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
        // Sin IDENTIFICADOR (crear)
        referenciaEjemploCliente
            .add(datosCliente)
            .addOnCompleteListener {  }
            .addOnFailureListener {  }
    }
    fun crearDatosPrueba(){
        val db = Firebase.firestore
        // EJEMPLO DE DATA
        val cities = db.collection("clientes")
        val data1 = hashMapOf(
            "cedula" to "1784467329",
            "numeroAfiliado" to 23424,
            "altura" to 1.60,
            "fechaCumpleanos" to LocalDate.of(2004, 7, 8),
            "sexo" to false,
        )
        val data2 = hashMapOf(
            "cedula" to "1384376298",
            "numeroAfiliado" to 85743,
            "altura" to 1.75,
            "fechaCumpleanos" to LocalDate.of(2000, 9, 8),
            "sexo" to false,
        )
        val data3 = hashMapOf(
            "cedula" to "183485732",
            "numeroAfiliado" to 23424,
            "altura" to 1.60,
            "fechaCumpleanos" to LocalDate.of(2024, 3, 29),
            "sexo" to false,
        )
    }
    fun eliminarRegistro(){
        val db = Firebase.firestore
        val referenciaEjemploCliente = db
            .collection("ejemplo")

        referenciaEjemploCliente
            .document("12345678")
            .delete() // elimina
            .addOnCompleteListener { /* Si todo salio bien*/ }
            .addOnFailureListener { /* Si algo salio mal*/ }
    }
    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refClientes: Query
    ){
        // si los datos que nos llegan son mayores a 0, se guardara el ultimo documento y se hara una nueva consulta
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = refClientes
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun consultarCiudades(
        adaptador: ArrayAdapter<ICliente>
    ){
        //recordando a la variable global query, esta es la que se modifica dependiendo el cursos o no

        val db = Firebase.firestore
        val clientesRef = db.collection("clientes")
            .orderBy("cedula")
            .limit(1)
        var tarea: Task<QuerySnapshot>? = null
        //si es nula, se hace una consulta normal, si no, se hace una consulta con el cursor
        if (query == null) {
            tarea = clientesRef.get() // 1era vez
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        } else {
            // consulta de la consulta anterior empezando en el nuevo documento
            tarea = query!!.get()
        }
        //aqui cada que nos lleguen los datos, se guardara el query y se iran añadiendo al arreglo
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, clientesRef)
                    for (cliente in documentSnapshots) {
                        anadirAArregloCliente(cliente)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    // si hay fallos
                }
        }
    }
    fun consultarIndiceCompuesto(
        adaptador: ArrayAdapter<ICliente>
    ){
        val db = Firebase.firestore
        val clientesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        clientesRefUnico
            //tiene 2 operadores where, uno para el sexo y otro para la poblacion
            .whereEqualTo("sexo", false)
            .whereLessThanOrEqualTo("cedula", 4000000)
            .orderBy("cedula", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                for (cliente in it){
                    anadirAArregloCliente(cliente)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }
    fun consultarDocumento(
        adaptador: ArrayAdapter<ICliente>
    ){
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("clientes")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        citiesRefUnico
            .document("BJ")
            .get() // obtener 1 DOCUMENTO
            .addOnSuccessListener {
                // it=> ES UN OBJETO!
                arreglo
                    .add(
                        ICliente(
                            it.data?.get("cedula") as String,
                            it.data?.get("numeroAfiliado") as Int,
                            it.data?.get("altura") as Double,
                            it.data?.get("fechaCumpleanos") as String,
                            it.data?.get("sexo") as Boolean,
                            it.data?.get("facturas") as MutableList<Factura>
                        )
                    )
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }
    fun limpiarArreglo() {
        arreglo.clear()
    }
}