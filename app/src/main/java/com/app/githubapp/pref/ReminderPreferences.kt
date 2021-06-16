package com.app.githubapp.pref

import android.content.Context
import com.app.githubapp.data.model.Reminder

class ReminderPreferences(context: Context) {
    companion object{
        const val PREF_NAME = "reminder_pref"
        private const val REMINDER = "isRemindeer"
    }

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.reminder)
        editor.apply()
    }

    fun getReminder(): Reminder{
        val model =Reminder()
        model.reminder = preference.getBoolean(REMINDER, false)
        return model
    }
}