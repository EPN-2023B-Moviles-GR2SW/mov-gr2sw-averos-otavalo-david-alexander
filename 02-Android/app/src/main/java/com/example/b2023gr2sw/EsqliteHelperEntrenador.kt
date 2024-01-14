package com.example.b2023gr2sw

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador (
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles", // nombre BDD este archivo guardara la informacion
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        // vemos que es una variable val nombre = """
        // pero ahora vemos que tiene 3 """ que significa que seran solo String
        // aqui pondremos toda la estructura de SQL aqui ponemos todos los comandos
        val scriptSQLCrearTablaEntrenador =
            """
               CREATE TABLE ENTRENADOR(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               descripcion VARCHAR(50)
               ) 
            """.trimIndent()
        //ejecutamos aqui el Script
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    //aqui ponemos la logica al momento de edtiar la base de datos
    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {}
    fun crearEntrenador(
        //aqui estan los atributos de nuestra tabla
        nombre: String,
        descripcion: String
    ): Boolean {
        //base de datos de ESCRITURA
        val basedatosEscritura = writableDatabase
        //aqui guardaremos los datos del registro
        val valoresAGuardar = ContentValues()
        //aqui mandamos los datos que se llenaran de forma incremental
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        // a la base de datos de escritura usaremos el .insert
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }
    //Para eliminar necesitamos una id como parametro
    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // Nombre tabla
                //aui buscamos en la tabla entrenador  con un id al cual debe ser igual
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }
    // este es una mezcla entre eliminar y crear
    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        id:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarEntrenadorPorID(id: Int): BEntrenador{
        // usaremos la base de datos de lectura
        val baseDatosLectura = readableDatabase
        // mandamos una consulta SQL de la tabla entrenador
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        //traemos el identificador donde sea igualal que hallamos mandado de parametro
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        // logica busqueda
        // aqui iteraremos
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        //este es nuestra forma de saber si el entrenador se lleno o se envia uno vacio
        val usuarioEncontrado = BEntrenador(0, "" , "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id != null){
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        // Cerramos todo
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}