package com.app.githubapp.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.githubapp.data.model.Reminder
import com.app.githubapp.databinding.ActivitySettingBinding
import com.app.githubapp.pref.ReminderPreferences
import com.app.githubapp.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val reminderPreferences = ReminderPreferences(this)
        binding.switch1.isChecked = reminderPreferences.getReminder().reminder

        alarmReceiver = AlarmReceiver()

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(this, "Repeating Alarm", "05:00", "Github Reminder")
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreferences = ReminderPreferences(this)
        reminder = Reminder()
        reminder.reminder = state
        reminderPreferences.setReminder(reminder)
    }
}