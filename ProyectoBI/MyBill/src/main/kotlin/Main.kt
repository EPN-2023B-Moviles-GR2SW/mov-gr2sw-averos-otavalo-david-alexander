import model.Cliente
import model.Factura
import java.time.LocalDate
import java.util.*

fun main(){
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Menú Principal:")
        println("1. Operaciones con Clientes")
        println("2. Operaciones con Facturas")
        println("3. Salir")

        print("Selecciona una opción: ")
        when (scanner.nextInt()) {
            1 -> menuClientes(scanner)
            2 -> menuFacturas(scanner)
            3 -> {
                println("Saliendo del programa.")
                scanner.close()
                return
            }
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}
//-------------------------------- MENU CLIENTE --------------------------------------------
fun menuClientes(scanner: Scanner) {
    while (true) {
        println("\nMenú Clientes:")
        println("1. Crear Cliente")
        println("2. Mostrar Todos los Clientes")
        println("3. Buscar Cliente por Cédula")
        println("4. Actualizar Cliente")
        println("5. Eliminar Cliente")
        println("6. Volver al Menú Principal")

        print("Selecciona una opción: ")

        when (scanner.nextInt()) {
            1 -> crearCliente(scanner)
            2 -> mostrarTodosLosClientes()
            3 -> buscarClientePorCedula(scanner)
            4 -> actualizarCliente(scanner)
            5 -> eliminarCliente(scanner)
            6 -> return
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }


    }
}
//--------------------------------------------CRUD Cliente --------------------------------------------
fun crearCliente(scanner: Scanner) {
    // Obtener los datos del nuevo cliente
    print("Ingrese la cédula del cliente: ")
    val cedula = scanner.next()
    print("Ingrese la numero de afiliado del cliente: ")
    val  numeroAfiliado= scanner.next().toInt()
    print("Ingrese la altura del cliente: ")
    val  altura= scanner.next().toDouble()
    print("Ingrese la fecha de cumpleaños (en formato yyyy-MM-dd): ")
    val fechaCumpleanosStr = scanner.next()
    // Intenta convertir la cadena a un objeto LocalDate
    val fechaCumpleanos: LocalDate
    try {
        fechaCumpleanos = LocalDate.parse(fechaCumpleanosStr)
    } catch (e: Exception) {
        println("Error: Formato de fecha incorrecto.")
        return
    }
    print("Ingrese el sexo del cliente (True = Masculino o False = Femenino): ")
    val  sexo= scanner.next().toBoolean()

    // Crear el objeto Cliente
    val nuevoCliente = Cliente(
        cedula = cedula,
        numeroAfiliado = numeroAfiliado,
        altura = altura,
        fechaCumpleanos = fechaCumpleanos,
        sexo = sexo
    )
    // Llamar al método para crear el cliente
    Cliente.createCliente(nuevoCliente)
}

fun mostrarTodosLosClientes() {
    // Obtener la lista de todos los clientes
    val listaClientes = Cliente.getAllClientes()

    // Imprimir la información de cada cliente
    for (cliente in listaClientes) {
        println("---------------------------------------------")
        println("Cédula: ${cliente.cedula}")
        // Imprimir el resto de la información del cliente
        println("Número de Afiliado: ${cliente.numeroAfiliado}")
        println("Altura: ${cliente.altura}")
        println("Fecha de Cumpleaños: ${cliente.fechaCumpleanos}")
        println("Sexo: ${if (cliente.sexo) "Masculino" else "Femenino"}")
    }
}

fun buscarClientePorCedula(scanner: Scanner) {
    println("---------------------------------------------")
    // Solicitar la cédula del cliente a buscar
    print("Ingrese la cédula del cliente a buscar: ")
    val cedula = scanner.next()

    // Buscar el cliente por su cédula
    val clienteEncontrado = Cliente.readClienteByCedula(cedula)

    if (clienteEncontrado.cedula.isNotEmpty()) {
        println("Cliente encontrado:")
    } else {
        println("Cliente con cédula $cedula no encontrado.")
    }
    // Imprimir la información del cliente si se encontró
    if (clienteEncontrado.cedula != "") {
        println("Información del Cliente:")
        println("Cédula: ${clienteEncontrado.cedula}")
        // Imprimir el resto de la información del cliente
        println("Número de Afiliado: ${clienteEncontrado.numeroAfiliado}")
        println("Altura: ${clienteEncontrado.altura}")
        println("Fecha de Cumpleaños: ${clienteEncontrado.fechaCumpleanos}")
        println("Sexo: ${if (clienteEncontrado.sexo) "Masculino" else "Femenino"}")

        // Imprimir las facturas del cliente
        println("**********************************************")
        println("Facturas del Cliente:")
        // Obtener la lista de todas las facturas
        val listaFacturas = Factura.readFacturasByCedula(cedula)

        // Imprimir la información de cada cliente
        for (factura in listaFacturas) {
            println("---------------------------------------------")
            println("Cédula: ${factura.cedulaCliente}")
            // Imprimir el resto de la información del cliente
            println("Fecha de Emision: ${factura.fechaEmision}")
            println("Número de Factura: ${factura.numeroFactura}")
            println("¿Pagó?: ${if (factura.esPagada) "Chi" else "No"}")
            println(" Total a Pagar: ${factura.totalAPagar}")
        }
    } else {
        println("No se encontró un cliente con la cédula especificada.")
    }
}

fun actualizarCliente(scanner: Scanner) {

    // Solicitar la cédula del cliente a actualizar
    print("Ingrese la cédula del cliente a actualizar: ")
    val cedula = scanner.next()

    // Buscar el cliente por su cédula
    val clienteExistente = Cliente.readClienteByCedula(cedula)

    // Si el cliente existe, permitir actualizar la información
    if (clienteExistente.cedula != "") {
        // Solicitar la nueva información del cliente
        print("Ingrese el nuevo número de afiliado: ")
        val nuevoNumeroAfiliado = scanner.nextInt()
        // Crear el objeto Cliente actualizado
        val clienteActualizado = Cliente(
            cedula = cedula,
            numeroAfiliado = nuevoNumeroAfiliado,
            altura = clienteExistente.altura, // Mantener la altura existente
            fechaCumpleanos = clienteExistente.fechaCumpleanos, // Mantener la fecha de cumpleaños existente
            sexo = clienteExistente.sexo // Mantener el sexo existente
        )
        // Llamar al método para actualizar el cliente
        Cliente.updateCliente(clienteActualizado)
        println("Cliente actualizado exitosamente.")
    } else {
        println("No se encontró un cliente con la cédula especificada.")
    }
}
fun eliminarCliente(scanner: Scanner) {
    // Solicitar la cédula del cliente a eliminar
    print("Ingrese la cédula del cliente a eliminar: ")
    val cedula = scanner.next()

    // Obtener la lista de facturas asociadas al cliente
    val facturasAsociadas = Factura.getAllFacturas().filter { it.cedulaCliente == cedula }

    // Eliminar todas las facturas asociadas al cliente
    for (factura in facturasAsociadas) {
        Factura.deleteFactura(factura.numeroFactura)
    }

    // Llamar al método para eliminar el cliente
    Cliente.deleteCliente(cedula)
    println("Cliente y sus facturas asociadas eliminados exitosamente.")
}

//-------------------------------- MENU FACTURA --------------------------------------------
fun menuFacturas(scanner: Scanner) {
    while (true) {
        println("\nMenú Facturas:")
        println("1. Crear Factura")
        println("2. Mostrar Facturas por Cédula de Cliente")
        println("3. Actualizar Estado de Pago de Factura")
        println("4. Eliminar Factura por Número")
        println("5. Volver al Menú Principal")

        print("Selecciona una opción: ")

        when (scanner.nextInt()) {
            1 -> crearFactura(scanner)
            2 -> mostrarFacturasPorCedulaCliente(scanner)
            3 -> actualizarEstadoDePago(scanner)
            4 -> eliminarFacturaPorNumero(scanner)
            5 -> return
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}
//-------------------------------------------- CRUD Factura --------------------------------------------
fun crearFactura(scanner: Scanner) {
    // Obtener los datos de la nueva factura
    print("Ingrese la cédula del cliente: ")
    val cedulaCliente = scanner.next()

    // Verificar si la cédula del cliente existe
    val clienteExistente = Cliente.readClienteByCedula(cedulaCliente)

    if (clienteExistente.cedula != "") {
        // Cédula válida, continuar con la creación de la factura
        print("Ingrese la fecha de emisión de la factura (en formato yyyy-MM-dd): ")
        val fechaEmisionStr = scanner.next()
        // Intenta convertir la cadena a un objeto LocalDate
        val fechaEmision: LocalDate
        try {
            fechaEmision = LocalDate.parse(fechaEmisionStr)
        } catch (e: Exception) {
            println("Error: Formato de fecha incorrecto.")
            return
        }

        print("Ingrese el número de factura: ")
        val numeroFactura = scanner.nextInt()

        print("La factura está pagada (true/false): ")
        val esPagada = scanner.nextBoolean()

        print("Ingrese el total a pagar de la factura: ")
        val  totalAPagar= scanner.next().toDouble()

        // Crear el objeto model.Factura
        val nuevaFactura = Factura(
            cedulaCliente = cedulaCliente,
            fechaEmision = fechaEmision,
            numeroFactura = numeroFactura,
            esPagada = esPagada,
            totalAPagar = totalAPagar
        )

        // Llamar al método para crear la factura
        Factura.createFactura(nuevaFactura)
        println("Factura creada exitosamente.")
    } else {
        println("La cédula del cliente no existe. La factura no se ha creado.")
    }
}
fun mostrarFacturasPorCedulaCliente(scanner: Scanner){
    // Obtener los datos de la nueva factura
    print("Ingrese la cédula del cliente: ")
    val cedulaCliente = scanner.next()

    // Verificar si la cédula del cliente existe
    val clienteExistente = Cliente.readClienteByCedula(cedulaCliente)

    if (clienteExistente.cedula != "") {
        println("Facturas del Cliente:")
        // Obtener la lista de todas las facturas
        val listaFacturas = Factura.readFacturasByCedula(cedulaCliente)

        // Imprimir la información de cada cliente
        for (factura in listaFacturas) {
            println("---------------------------------------------")
            println("Cédula: ${factura.cedulaCliente}")
            // Imprimir el resto de la información del cliente
            println("Fecha de Emision: ${factura.fechaEmision}")
            println("Número de Factura: ${factura.numeroFactura}")
            println("¿Pagó?: ${if (factura.esPagada) "Chi" else "No"}")
            println(" Total a Pagar: ${factura.totalAPagar}")
        }
    } else {
        println("La cédula del cliente no existe. La factura no se ha encontrado.")
    }
    println("---------------------------------------------")
}
fun actualizarEstadoDePago(scanner: Scanner){
    // Obtener los datos de la nueva factura
    print("Ingrese la cédula del cliente: ")
    val cedulaCliente = scanner.next()
    // Verificar si la cédula del cliente existe
    val clienteExistente = Cliente.readClienteByCedula(cedulaCliente)
    print("Ingrese el numero de la Factura a Modificar: ")
    val numeroFacturaActualizar = scanner.nextInt()
    val facturaExistente = Factura.getAllFacturas().find {
        it.cedulaCliente == cedulaCliente && it.numeroFactura == numeroFacturaActualizar }

    if (facturaExistente != null && clienteExistente.cedula != "") {
        println("Factura encontrada:")
        println("¿La factura está pagada? ${facturaExistente.esPagada}")
        print("¿Modificar el estado de pago? (true/false): ")
        val nuevoEstadoPago = scanner.nextBoolean()

        // Actualizar el estado de pago de la factura
        facturaExistente.esPagada = nuevoEstadoPago

        // Actualizar la factura en el archivo
        Factura.updateFactura(facturaExistente)

        println("Factura actualizada exitosamente.")
    } else {
        println("La cédula del cliente no existe. La factura no se ha encontrado.")
    }
    println("---------------------------------------------")
}
fun eliminarFacturaPorNumero(scanner: Scanner){
    // Obtener los datos de la nueva factura
    print("Ingrese la cédula del cliente: ")
    val cedulaCliente = scanner.next()
    // Verificar si la cédula del cliente existe
    val clienteExistente = Cliente.readClienteByCedula(cedulaCliente)
    print("Ingrese el numero de la Factura a Modificar: ")
    val numeroFacturaActualizar = scanner.nextInt()
    val facturaExistente = Factura.getAllFacturas().find {
        it.cedulaCliente == cedulaCliente && it.numeroFactura == numeroFacturaActualizar }

    if (facturaExistente != null && clienteExistente.cedula != "") {
        // Eliminar la factura en el archivo
        Factura.deleteFactura(numeroFacturaActualizar)
        println("Factura Eliminada exitosamente.")
    } else {
        println("La cédula del cliente no existe. La factura no se ha encontrado.")
    }
    println("---------------------------------------------")
}