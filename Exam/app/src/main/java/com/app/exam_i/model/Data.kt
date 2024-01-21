package com.app.exam_i.model

import java.time.LocalDate

object Data {
    private val factura1 = Factura(
        "1784467329",
        LocalDate.of(2024, 11, 28),
        213,
        false,
        2384.03
    )
    private val factura2 = Factura(
        "1784467329",
        LocalDate.of(2010, 7, 8), 101,
        false,
        150.75
    )
    private val factura3 = Factura(
        "1384376298",
        LocalDate.of(2017, 6, 10),
        998,
        false,
        1674.03
    )
    private val factura4 = Factura(
        "1384376298",
        LocalDate.of(2016, 10, 14),
        345,
        false,
        3482.34
    )
    private val cliente1 = Cliente(
        "1784467329",
        23424,
        1.60,
        LocalDate.of(2004, 7, 8),
        false,
        mutableListOf(factura1, factura2)
    )
    private val cliente2 = Cliente(
        "1384376298",
        85743,
        1.75,
        LocalDate.of(2000, 9, 8),
        false,
        mutableListOf(factura3, factura4)
    )

    val datos = mutableListOf(cliente1, cliente2)
}