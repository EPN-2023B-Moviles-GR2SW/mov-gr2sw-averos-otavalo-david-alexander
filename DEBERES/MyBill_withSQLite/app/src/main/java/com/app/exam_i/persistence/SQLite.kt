package com.app.exam_i.persistence

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.exam_i.model.Cliente
import com.app.exam_i.model.Factura
import java.time.LocalDate

class SQLite(context: Context?): SQLiteOpenHelper(context, NOMBRE_BASE_DATOS,
    null,
    VERSION_BASE_DATOS
) {

    companion object {
        const val NOMBRE_BASE_DATOS = "deber2.db"
        const val VERSION_BASE_DATOS = 1
        val SCRIPT_CREATE_TABLE_CLIENTE =
            """CREATE TABLE cliente(
                cedula TEXT PRIMARY KEY, 
                numeroAfiliado INTEGER, 
                altura REAL,
                fechaCumpleanos TEXT, 
                sexo INTEGER)
            """.trimIndent()

        val SCRIPT_CREATE_TABLE_FACTURA =
            """
            CREATE TABLE factura(
                cedulaCliente TEXT, 
                fechaEmision TEXT, 
                numeroFactura INTEGER, 
                esPagada TEXT, 
                totalAPagar REAL, 
                FOREIGN KEY(cedulaCliente) REFERENCES cliente(cedula))
            """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SCRIPT_CREATE_TABLE_CLIENTE)
        db?.execSQL(SCRIPT_CREATE_TABLE_FACTURA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cliente")
        db?.execSQL("DROP TABLE IF EXISTS factura")
        onCreate(db)
    }

    // ---------- CRUD CLIENTE ----------
    fun crearCliente(cedula: String, numeroAfiliado: Int, altura: Double, fechaCumpleanos: LocalDate, sexo: Boolean): Boolean {
        val db = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("cedula", cedula)
        valoresAGuardar.put("numeroAfiliado", numeroAfiliado)
        valoresAGuardar.put("altura", altura)
        valoresAGuardar.put("fechaCumpleanos", fechaCumpleanos.toString())
        valoresAGuardar.put("sexo", sexo)
        val resultado = db.insert("cliente", null, valoresAGuardar)
        db.close()
        return if (resultado.toInt() == -1) false else true
    }

    fun eliminarCliente(cedula: String): Boolean {
        val db = writableDatabase
        val resultado = db.delete("cliente", "cedula = ?", arrayOf(cedula))
        db.close()
        return if (resultado.toInt() == -1) false else true
    }

    fun actualizarCliente(cliente: Cliente){
        val values = ContentValues().apply {
            put("cedula", cliente.cedula)
            put("numeroAfiliado", cliente.numeroAfiliado)
            put("altura", cliente.altura)
            put("fechaCumpleanos", cliente.fechaCumpleanos.toString())
            put("sexo", cliente.sexo)
        }
        val db = writableDatabase
        db.update("cliente", values, "cedula = ?", arrayOf(cliente.cedula))
    }

    fun leerClientePorID(cedula: String): Cliente {
        val db = readableDatabase
        val scriptLeerCliente =
            """
            SELECT * FROM cliente WHERE cedula = ?
            """.trimIndent()
        val parametrosLeerCliente = arrayOf(cedula)
        val resultadoLectura = db.rawQuery(scriptLeerCliente, parametrosLeerCliente)
        val clienteExiste = resultadoLectura.moveToFirst()
        val cliente = Cliente("", 0, 0.0, LocalDate.of(2024,2,29), false)
        val arreglo = arrayOf<Cliente>()
        if (clienteExiste) {
            do{
                val id = resultadoLectura.getString(0)
                val numeroAfiliado = resultadoLectura.getInt(1)
                val altura = resultadoLectura.getDouble(2)
                val fechaCumpleanos = LocalDate.parse(resultadoLectura.getString(3))
                val sexo = resultadoLectura.getInt(4) == 1
                if (id != null){
                    cliente.cedula = id
                    cliente.numeroAfiliado = numeroAfiliado
                    cliente.altura = altura
                    cliente.fechaCumpleanos = fechaCumpleanos
                    cliente.sexo = sexo
                }

            }while (resultadoLectura.moveToNext())
        }
        resultadoLectura.close()
        db.close()
        return cliente
    }

    @SuppressLint("Range")
    fun obtenerCliente(): ArrayList<Cliente> {
        val db = writableDatabase
        val listaClientes = mutableListOf<Cliente>()
        val cursor = db.query("cliente", null, null, null, null, null, null)

        while(cursor.moveToNext()) {
            val cedula = cursor.getString(0)
            val numeroAfiliado = cursor.getInt(1)
            val altura = cursor.getDouble(2)
            val fechaCumpleanos = LocalDate.parse(cursor.getString(3))
            val sexo = cursor.getInt(4) == 1
            val cliente = Cliente(cedula, numeroAfiliado, altura, fechaCumpleanos, sexo)
            listaClientes.add(cliente)
        }
        cursor.close()
        return listaClientes as ArrayList<Cliente>
    }

    // ---------- CRUD FACTURA ----------
    fun crearFactura(cedulaCliente: String, fechaEmision: String, numeroFactura: Int, esPagada: Boolean, totalAPagar: Double): Boolean {
        val db = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("cedulaCliente", cedulaCliente)
        valoresAGuardar.put("fechaEmision", fechaEmision)
        valoresAGuardar.put("numeroFactura", numeroFactura)
        valoresAGuardar.put("esPagada", esPagada)
        valoresAGuardar.put("totalAPagar", totalAPagar)
        val resultado = db.insert("factura", null, valoresAGuardar)
        db.close()
        return if (resultado.toInt() == -1) false else true
    }

    fun eliminarFactura(numeroFactura: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete("factura", "numeroFactura = ?", arrayOf(numeroFactura.toString()))
        db.close()
        return if (resultado.toInt() == -1) false else true
    }

    fun actualizarFactura(factura: Factura) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("cedulaCliente", factura.cedulaCliente)
            put("fechaEmision", factura.fechaEmision.toString())
            put("numeroFactura", factura.numeroFactura)
            put("esPagada", factura.esPagada)
            put("totalAPagar", factura.totalAPagar)
        }
        db.update("factura", values, "numeroFactura = ?", arrayOf(factura.numeroFactura.toString()))

    }

    fun leerFacturaPorID(numeroFactura: Int): Factura {
        val db = readableDatabase
        val scriptLeerFactura =
            """
            SELECT * FROM factura WHERE numeroFactura = ?
            """.trimIndent()
        val parametrosLeerFactura = arrayOf(numeroFactura.toString())
        val resultadoLectura = db.rawQuery(scriptLeerFactura, parametrosLeerFactura)
        val facturaExiste = resultadoLectura.moveToFirst()
        val factura = Factura("", LocalDate.of(2024,2,29), 0, false, 0.0)
        if (facturaExiste) {
            do{
                val cedulaCliente = resultadoLectura.getString(0)
                val fechaEmision = LocalDate.parse(resultadoLectura.getString(1))
                val numeroFactura = resultadoLectura.getInt(2)
                val esPagada = resultadoLectura.getInt(3) == 1
                val totalAPagar = resultadoLectura.getDouble(4)
                if (cedulaCliente != null){
                    factura.cedulaCliente = cedulaCliente
                    factura.fechaEmision = fechaEmision
                    factura.numeroFactura = numeroFactura
                    factura.esPagada = esPagada
                    factura.totalAPagar = totalAPagar
                }
            }while (resultadoLectura.moveToNext())
        }
        resultadoLectura.close()
        db.close()
        return factura
    }
}