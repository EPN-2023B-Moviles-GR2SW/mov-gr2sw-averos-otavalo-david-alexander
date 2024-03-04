package com.app.exam_i.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class Cliente (
    var cedula: String?,
    var numeroAfiliado: Int,
    var altura: Double,
    var fechaCumpleanos: LocalDate,
    var sexo: Boolean,
    var facturas: MutableList<Factura> = mutableListOf()
): Parcelable {
    constructor(parcel: Parcel) : this(
        //en esta parte podemos utilziar solo las funciones de parcelable
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        LocalDate.parse(parcel.readString()),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun toString(): String {
        if(sexo){
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Masculino\n"
        }else{
            return "Cédula = $cedula\nNúmero de Afiliado = $numeroAfiliado\nAltura = $altura\nFecha de Cumpleaños = $fechaCumpleanos\nSexo = Femenino\n"
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cedula)
        parcel.writeInt(numeroAfiliado)
        parcel.writeDouble(altura)
        parcel.writeByte(if (sexo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }
}
