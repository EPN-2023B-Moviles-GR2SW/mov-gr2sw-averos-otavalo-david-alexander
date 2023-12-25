package model

import persistence.GestorArchivos
import java.time.LocalDate

data class Factura(
    val cedulaCliente: String,
    val fechaEmision: LocalDate,
    val numeroFactura: Int,
    var esPagada: Boolean,
    val totalAPagar: Double
){
    companion object {
        private val gestorArchivos =
            GestorArchivos("src/main/kotlin/persistence/ArchivosFacturas.txt")

        //--------------------------------- GET & SET de ARCHIVOS ------------------------------------
        fun getAllFacturas(): MutableList<Factura> {
            val facturaData = gestorArchivos.readData()
            val facturaList = mutableListOf<Factura>()
            for (line in facturaData) {
                val facturaProperties = line.split(",")
                if (facturaProperties.size == 5) { // Verificar que haya al menos 5 elementos
                    val factura = Factura(
                        facturaProperties[0],
                        LocalDate.parse(facturaProperties[1]),
                        facturaProperties[2].toInt(),
                        facturaProperties[3].toBoolean(),
                        facturaProperties[4].toDouble()
                    )
                    facturaList.add(factura)
                }
            }
            return facturaList
        }

        private fun saveAllFacturas(facturaList: List<Factura>) {
            val facturaData = facturaList.map { factura ->
                "${factura.cedulaCliente},${factura.fechaEmision},${factura.numeroFactura}," +
                        "${factura.esPagada},${factura.totalAPagar}"
            }
            gestorArchivos.writeData(facturaData)
        }

        //--------------------------------- CRUD de FACTURAS ------------------------------------
        fun createFactura(factura: Factura) {
            val facturaList = getAllFacturas()

            // Verificar que la cédula esté ligada a un cliente existente
            val clienteExistente = Cliente.readClienteByCedula(factura.cedulaCliente)

            if (clienteExistente.cedula != "") {
                // Verificar que el número de factura cumple con el rango especificado
                if (factura.numeroFactura in 1..999) {
                    // Verificar que el total a pagar sea positivo
                    if (factura.totalAPagar > 0) {
                        // Agregar la factura solo si todas las validaciones son exitosas
                        facturaList.add(factura)
                        saveAllFacturas(facturaList)
                        println("Factura creada exitosamente.")
                    } else {
                        println("Error: El total a pagar debe ser un valor positivo.")
                    }
                } else {
                    println("Error: El número de factura debe estar en el rango de 1 a 999.")
                }
            } else {
                println("Error: La cédula no está ligada a un cliente existente.")
            }
        }

        fun readFacturasByCedula(cedulaCliente: String): List<Factura> {
            val facturaList = getAllFacturas()
            return facturaList.filter { it.cedulaCliente == cedulaCliente }
        }

        fun updateFactura(factura: Factura) {
            val facturaList = getAllFacturas()
            val existingFactura = facturaList.find { it.numeroFactura == factura.numeroFactura }
            if (existingFactura != null) {
                existingFactura.esPagada = factura.esPagada
                saveAllFacturas(facturaList)
            }
        }

        fun deleteFactura(numeroFactura: Int) {
            val facturaList = getAllFacturas()
            facturaList.removeIf { it.numeroFactura == numeroFactura }
            saveAllFacturas(facturaList)
        }
    }
}