package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.app.exam_i.persistence.SQLite
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import java.text.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class pantallaEditarCliente : AppCompatActivity() {

    lateinit var cedula: String
    var numeroAfiliado: Int = 0
    var altura: Double = 0.0
    var sexo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_editar_cliente)
        val clienteBD = SQLite(this)
        val positionClienteSelected = intent.getIntExtra("positionClienteSelected", -1)

        val inputCedulaCliente = findViewById<EditText>(R.id.txtPlainCedula)
        val inputNumeroAfiliado = findViewById<EditText>(R.id.txtPlainNumAfiliado)
        val inputFechaNacimientoCliente = findViewById<EditText>(R.id.txtPlainCumpleanos)
        val inputAlturaCliente = findViewById<EditText>(R.id.txtPlainAltura)
        val inputRBTMACHO = findViewById<RadioButton>(R.id.rbtMacho)
        val inputRBTHEMBRA = findViewById<RadioButton>(R.id.rbtHembra)
        val btnCrear = findViewById<Button>(R.id.btnCrearCliente)
        val btnActualizar = findViewById<Button>(R.id.btnActualizarCliente)

        if (positionClienteSelected != -1) {
            // Editar
            btnCrear.visibility = View.GONE
            btnActualizar.visibility = View.VISIBLE

            val cliente = clienteBD.obtenerCliente()[positionClienteSelected]
            inputCedulaCliente.setText(cliente.cedula)
            inputNumeroAfiliado.setText(cliente.numeroAfiliado.toString())
            inputFechaNacimientoCliente.setText(cliente.fechaCumpleanos.toString())
            inputAlturaCliente.setText(cliente.altura.toString())

            if (cliente.sexo) {
                inputRBTMACHO.isChecked = true
            } else {
                inputRBTHEMBRA.isChecked = true
            }

            btnActualizar.setOnClickListener {

                try {
                    // Parseo seguro de la fecha
                    var fechaCumpleanos = parseFecha(inputFechaNacimientoCliente.text.toString())
                    cedula = inputCedulaCliente.text.toString()
                    numeroAfiliado = inputNumeroAfiliado.text.toString().toInt()
                    altura = inputAlturaCliente.text.toString().toDouble()
                    sexo = inputRBTMACHO.isChecked

                    cliente.cedula = cedula
                    cliente.numeroAfiliado = numeroAfiliado
                    cliente.altura = altura
                    cliente.fechaCumpleanos = fechaCumpleanos
                    cliente.sexo = sexo

                    clienteBD.actualizarCliente(cliente)
                    Toast.makeText(this, "Cliente actualizado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                } catch (e: ParseException) {
                    Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Crear
            btnCrear.visibility = View.VISIBLE
            btnActualizar.visibility = View.GONE
            btnCrear.setOnClickListener {
                try {
                    // Parseo seguro de la fecha
                    var fechaCumpleanos = parseFecha(inputFechaNacimientoCliente.text.toString())
                    cedula = inputCedulaCliente.text.toString()
                    numeroAfiliado = inputNumeroAfiliado.text.toString().toInt()
                    altura = inputAlturaCliente.text.toString().toDouble()
                    sexo = inputRBTMACHO.isChecked

                    clienteBD.crearCliente(cedula, numeroAfiliado, altura, fechaCumpleanos, sexo)
                    Toast.makeText(this, "Cliente creado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                } catch (e: ParseException) {
                    Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun parseFecha(fecha: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(fecha, formatter)
    }
}