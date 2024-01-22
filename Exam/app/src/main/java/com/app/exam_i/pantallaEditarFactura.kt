package com.app.exam_i

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.app.exam_i.model.Data
import com.app.exam_i.model.Factura
import java.text.ParseException
import java.time.LocalDate

class pantallaEditarFactura : AppCompatActivity() {
    lateinit var cedulaCliente: String
    lateinit var fechaEmision: LocalDate
    var numeroFactura: Int = 0
    var esPagada: Boolean = false
    var totalAPagar: Double = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_editar_factura)

        val positionFacturaSelected = intent.getIntExtra("positionFacturaSelected", -1)
        val positionClienteSelected = intent.getIntExtra("positionClienteSelected", -1)

        val inputCedulaCliente = findViewById<EditText>(R.id.txtPlainCedulaFactura)
        val inputFechaEmision = findViewById<EditText>(R.id.txtPlainEmsion)
        val inputNumeroFactura = findViewById<EditText>(R.id.txtPlainNumFactura)
        val inputTotalPagar = findViewById<EditText>(R.id.txtPlainTotalPago)
        val inputSIPagada = findViewById<RadioButton>(R.id.rbtPago)
        val inputNOPagada = findViewById<RadioButton>(R.id.rbtNOPago)
        val btnCrear = findViewById<Button>(R.id.btn_CrearFactura)
        val btnActualizar = findViewById<Button>(R.id.btnActualizarFactura)

        if (positionFacturaSelected != -1){
            // Editar
            btnCrear.visibility = View.GONE
            btnActualizar.visibility = View.VISIBLE

            inputCedulaCliente.setText(Data.datos[positionClienteSelected].facturas[positionFacturaSelected].cedulaCliente)
            inputFechaEmision.setText(Data.datos[positionClienteSelected].facturas[positionFacturaSelected].fechaEmision.toString())
            inputNumeroFactura.setText(Data.datos[positionClienteSelected].facturas[positionFacturaSelected].numeroFactura.toString())
            inputTotalPagar.setText(Data.datos[positionClienteSelected].facturas[positionFacturaSelected].totalAPagar.toString())

            if (Data.datos[positionClienteSelected].facturas[positionFacturaSelected].esPagada){
                inputSIPagada.isChecked = true
            }else{
                inputNOPagada.isChecked = true
            }

            btnActualizar.setOnClickListener {
                try {
                    cedulaCliente = inputCedulaCliente.text.toString()
                    fechaEmision = LocalDate.parse(inputFechaEmision.text.toString())
                    numeroFactura = inputNumeroFactura.text.toString().toInt()
                    esPagada = inputSIPagada.isChecked
                    totalAPagar = inputTotalPagar.text.toString().toDouble()

                    val factura = Factura(cedulaCliente, fechaEmision, numeroFactura, esPagada, totalAPagar)
                    Data.datos[positionClienteSelected].facturas[positionFacturaSelected] = factura
                    Toast.makeText(this, "Factura Actualizada", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()

                } catch (e: ParseException) {
                    Toast.makeText(this, "Error en el formato de la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al actualizar la factura", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            // Crear
            btnCrear.visibility = View.VISIBLE
            btnActualizar.visibility = View.GONE

            btnCrear.setOnClickListener {
                try {
                    cedulaCliente = inputCedulaCliente.text.toString()
                    fechaEmision = LocalDate.parse(inputFechaEmision.text.toString())
                    numeroFactura = inputNumeroFactura.text.toString().toInt()
                    esPagada = inputSIPagada.isChecked
                    totalAPagar = inputTotalPagar.text.toString().toDouble()

                    val factura = Factura(cedulaCliente, fechaEmision, numeroFactura, esPagada, totalAPagar)
                    Data.datos[positionClienteSelected].facturas.add(factura)
                    Toast.makeText(this, "Factura Creada", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK, Intent())
                    finish()

                } catch (e: ParseException) {
                    Toast.makeText(this, "Error en el formato de la fecha", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al crear la factura", Toast.LENGTH_SHORT).show()
                }
            }
        }






        }
}