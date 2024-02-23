package com.app.alarmyapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.alarmyapp.adapter.AlarmaAdapter
import com.app.alarmyapp.persistence.AlarmyBD
import com.app.alarmyapp.ui.theme.AlarmyAppTheme
import com.app.alarmyapp.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlarmy)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AlarmaAdapter(AlarmyBD.alarmasList)
        recyclerView.addItemDecoration(decoration)
    }
}
