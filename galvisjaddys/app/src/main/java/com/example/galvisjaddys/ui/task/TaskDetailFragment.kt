package com.example.galvisjaddys.ui.task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.galvisjaddys.R
import com.example.galvisjaddys.ReminderReceiver
import com.example.galvisjaddys.data.task.Task
import com.example.galvisjaddys.data.task.TaskRepository
import java.util.Calendar

class TaskDetailFragment : Fragment() {

    private var taskId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val etTime = view.findViewById<EditText>(R.id.etTime) // ⏰ hora tarea
        val etReminderTime = view.findViewById<EditText>(R.id.etReminderTime) // 🔔 hora alarma
        val cbReminder = view.findViewById<CheckBox>(R.id.cbReminder)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // 🔥 LEER ID
        taskId = arguments?.getInt("taskId") ?: -1

        // 🔥 CARGAR DATOS
        if (taskId != -1) {
            val task = TaskRepository.getTasks().find { it.id == taskId }

            task?.let {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
                etTime.setText(it.time)
                etReminderTime.setText(it.reminderTime)
                cbReminder.isChecked = it.hasReminder
            }
        }

        btnSave.setOnClickListener {

            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val time = etTime.text.toString()
            val reminderTime = etReminderTime.text.toString()
            val reminder = cbReminder.isChecked

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese un título", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (reminder && reminderTime.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese hora del recordatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔥 CREAR TASK
            val task = Task(
                id = if (taskId == -1) {
                    (System.currentTimeMillis() % 100000).toInt()
                } else {
                    taskId
                },
                title = title,
                description = description,
                time = time,
                reminderTime = reminderTime,
                hasReminder = reminder
            )

            // 🔥 PERMISO
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }

            // 🔥 GUARDAR
            if (taskId == -1) {
                TaskRepository.addTask(task, requireContext())
            } else {
                TaskRepository.updateTask(task, requireContext())
            }

            // 🔔 USAR SOLO reminderTime (NO time)
            if (reminder) {
                try {

                    val calendar = Calendar.getInstance()

                    val parts = reminderTime.split(":")
                    val hour = parts[0].toInt()
                    val minute = parts[1].toInt()

                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)

                    if (calendar.timeInMillis <= System.currentTimeMillis()) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                    }

                    val intent = Intent(requireContext(), ReminderReceiver::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)

                    val pendingIntent = PendingIntent.getBroadcast(
                        requireContext(),
                        1,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    val alarmManager = requireContext()
                        .getSystemService(Context.ALARM_SERVICE) as AlarmManager

                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )

                    Toast.makeText(requireContext(), "Alarma a las $reminderTime", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Formato inválido (HH:mm)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            Toast.makeText(requireContext(), "Tarea guardada", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return view
    }
}