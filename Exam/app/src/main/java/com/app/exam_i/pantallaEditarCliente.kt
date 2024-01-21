package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.app.exam_i.model.Data
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.app.exam_i.model.Cliente
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
            btnCrear.isEnabled = false
            btnActualizar.isEnabled = true

            inputCedulaCliente.setText(Data.datos[positionClienteSelected].cedula.toString())
            inputNumeroAfiliado.setText(Data.datos[positionClienteSelected].numeroAfiliado.toString())
            inputAlturaCliente.setText(Data.datos[positionClienteSelected].altura.toString())
            inputFechaNacimientoCliente.setText(Data.datos[positionClienteSelected].fechaCumpleanos.toString())

            if (Data.datos[positionClienteSelected].sexo) {
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


                    Data.datos[positionClienteSelected].cedula = cedula
                    Data.datos[positionClienteSelected].numeroAfiliado = numeroAfiliado
                    Data.datos[positionClienteSelected].altura = altura
                    Data.datos[positionClienteSelected].fechaCumpleanos = fechaCumpleanos
                    Data.datos[positionClienteSelected].sexo = sexo
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
            btnCrear.isEnabled = true
            btnActualizar.isEnabled = false
            btnCrear.setOnClickListener {
                try {
                    // Parseo seguro de la fecha
                    var fechaCumpleanos = parseFecha(inputFechaNacimientoCliente.text.toString())
                    cedula = inputCedulaCliente.text.toString()
                    numeroAfiliado = inputNumeroAfiliado.text.toString().toInt()
                    altura = inputAlturaCliente.text.toString().toDouble()
                    sexo = inputRBTMACHO.isChecked

                    Data.datos.add(
                        Cliente(
                            cedula,
                            numeroAfiliado,
                            altura,
                            fechaCumpleanos,
                            sexo
                        )
                    )
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