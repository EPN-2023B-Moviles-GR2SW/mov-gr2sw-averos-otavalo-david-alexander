package com.app.alarmyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.alarmyapp.model.Alarma
import com.app.alarmyapp.R

class AlarmaAdapter(private val alarmaList:List<Alarma>): RecyclerView.Adapter<AlarmaViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlarmaViewHolder(layoutInflater.inflate(R.layout.item_alarma, parent, false))
    }

    override fun onBindViewHolder(holder: AlarmaViewHolder, position: Int) {
        val item = alarmaList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return alarmaList.size
    }

}