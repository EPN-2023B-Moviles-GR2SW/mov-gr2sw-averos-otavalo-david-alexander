package model
import persistence.GestorArchivos
import java.time.LocalDate
data class Cliente (
    val cedula: String,
    var numeroAfiliado: Int,
    var altura: Double,
    val fechaCumpleanos: LocalDate,
    var sexo: Boolean
){
companion object{
    private val gestorArchivos =
        GestorArchivos("src/main/kotlin/persistence/ArchivosClientes.txt")

    //--------------------------------- GET & SET de ARCHIVOS ------------------------------------
    fun getAllClientes(): MutableList<Cliente>{
        val clienteData = gestorArchivos.readData()
        val clienteList = mutableListOf<Cliente>()
        for (line in clienteData) {
            val clientesPropieties = line.split(",")
            if (clientesPropieties.size == 5) { // Verificar que haya al menos 5 elementos
                val cliente = Cliente(
                    clientesPropieties[0],
                    clientesPropieties[1].toInt(),
                    clientesPropieties[2].toDouble(),
                    LocalDate.parse(clientesPropieties[3]),
                    clientesPropieties[4].toBoolean()
                )
                clienteList.add(cliente)
            }
        }
        return clienteList
    }
    private fun saveAllClientes(clienteList: List<Cliente>) {
        val clienteData = clienteList.map { cliente ->
            "${cliente.cedula},${cliente.numeroAfiliado},${cliente.altura},${cliente.fechaCumpleanos},${cliente.sexo}"
        }
        gestorArchivos.writeData(clienteData)
    }
    //--------------------------------- CRUD de CLIENTES ------------------------------------
    fun createCliente(cliente: Cliente) {
        // Obtener la lista actual de clientes
        val clienteList = getAllClientes()

        // Verificar si la cédula ya existe
        if (clienteList.any { it.cedula == cliente.cedula }) {
            println("Error: La cédula ya existe. No se puede agregar el cliente.")
            return
        }
        // Verificar que la cédula tenga 10 dígitos positivos
        if (cliente.cedula.length != 10 ) {
            println("Error: La cédula debe tener 10 dígitos positivos.")
            return
        }
        // Verificar que la altura sea positiva
        if (cliente.altura <= 0) {
            println("Error: La altura debe ser un número positivo.")
            return
        }
        // Verificar que el número de afiliado tenga 5 dígitos positivos
        if (cliente.numeroAfiliado.toString().length != 5 || cliente.numeroAfiliado <= 0) {
            println("Error: El número de afiliado debe tener 5 dígitos positivos.")
            return
        }
        // Agregar el cliente a la lista y guardar la lista actualizada
        clienteList.add(cliente)
        saveAllClientes(clienteList)
        println("Cliente agregado exitosamente.")
    }
    fun readClienteByCedula(cedula: String): Cliente {
        val clienteList = getAllClientes()
        val clienteEncontrado = clienteList.find { it.cedula == cedula }
        return clienteEncontrado ?: Cliente("", 0, 0.0, LocalDate.now(), false)
    }
    fun updateCliente(cliente: Cliente) {
        val clienteList = getAllClientes()
        val existingCliente = clienteList.find { it.cedula == cliente.cedula }
        if (existingCliente != null) {
            existingCliente.numeroAfiliado = cliente.numeroAfiliado
            existingCliente.altura = cliente.altura
            existingCliente.sexo = cliente.sexo
            saveAllClientes(clienteList)
        }
    }
    fun deleteCliente(cedula: String) {
        val clienteList = getAllClientes()
        val clienteElimnar = readClienteByCedula(cedula)
        clienteList.removeIf { it.cedula == clienteElimnar.cedula }
        saveAllClientes(clienteList)
    }
}
}

