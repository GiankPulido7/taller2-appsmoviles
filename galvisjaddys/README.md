##  Taller 3 - Aplicación To-Do

###  ¿Qué se implementó?

 En este taller se desarrolló una aplicación de lista de tareas (To-Do App) en Android Studio utilizando:

- Una sola Activity como contenedor principal.
- Múltiples Fragments:
    - Lista de tareas.
    - Detalle/creación/edición de tareas.
- Navegación entre fragments mediante Navigation Component.
- Persistencia de datos en memoria mediante un repositorio (`TaskRepository`).
- Implementación de RecyclerView para mostrar las tareas.
- Funcionalidad para:
    - Crear tareas.
    - Editar tareas existentes.
    - Marcar tareas como completadas.
- Separación de:
    - Hora de la tarea.
    - Hora del recordatorio.


###  Sistema de recordatorios

Se implementó un sistema de recordatorios utilizando:

 **Notificaciones locales**

- Se utiliza un `BroadcastReceiver` (`ReminderReceiver`) para manejar los eventos de alarma.
- Se programa la notificación mediante `AlarmManager`.
- El usuario puede activar o desactivar el recordatorio con un CheckBox.
- La notificación muestra:
    - Título de la tarea.
    - Descripción.


###  Funcionalidades adicionales

- Validación del formato de hora (HH:mm).
- Interfaz organizada con `ConstraintLayout`.
- Separación entre lógica de datos y UI.


###  Evidencias

Las capturas de pantalla se encuentran en la carpeta:

**docs/**

Incluyen:
- Lista de tareas.
- Pantalla de detalle.
- Notificación en ejecución.