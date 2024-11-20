package com.doublelift.snoozeloov2_nove24project.presentaion.alarmStart

//fun setAlarm(context: Context, hour: Int, minute: Int) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//        if (!alarmManager.canScheduleExactAlarms()) {
//            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
//            context.startActivity(intent)
//            return
//        }
//    }
//
//    val calendar = Calendar.getInstance().apply {
//        set(Calendar.HOUR_OF_DAY, hour)
//        set(Calendar.MINUTE, minute)
//        set(Calendar.SECOND, 0)
//        set(Calendar.MILLISECOND, 0)
//    }
//
//    val intent = Intent(context, AlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
//    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//}
//
//fun cancelAlarm(context: Context) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val intent = Intent(context, AlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//    alarmManager.cancel(pendingIntent)
//}