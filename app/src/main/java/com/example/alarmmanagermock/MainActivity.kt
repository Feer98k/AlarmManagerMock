package com.example.alarmmanagermock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmmanagermock.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var alarmManager: AlarmManager
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        binding.btnDispararAlarm.setOnClickListener {
            val intent = Intent(context, Receiver::class.java)

            val pendingIntentInit = PendingIntent.getBroadcast(context, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, returnDateInMilis(19,51),pendingIntentInit)
            val pendingIntentFinish = PendingIntent.getBroadcast(context, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, returnDateInMilis(19,53) ,pendingIntentFinish)

            Log.d("TAG", "onCreate: " + Date().toString())
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
private fun returnDateInMilis(hour: Int, min: Int): Long {
    val instance = Calendar.getInstance()
    val day = instance.get(Calendar.DAY_OF_MONTH)
    val month = instance.get(Calendar.MONTH)+1
    val year = instance.get(Calendar.YEAR)
    val hour = hour
    val min = min
    val myDate = "$year/$month/$day $hour:$min:00"
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    val date = simpleDateFormat.parse(myDate)
    Log.d("TAG", "returnDateInMilis: ${date.time}")
    return date.time
}

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "receive: " + Date().toString())

    }

}