/*Universidad de Cundinamarca
Linea de Profundizacion III
Codigo Hospital
Nicolas Guzman Montaña
 */

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.system.exitProcess

// Clase base para Persona
open class Persona(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate,
    val direccion: String,
    val ciudadProcedencia: String,
)

// Clase para Paciente
class Paciente(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    val historiaClinica: String,
    val sexo: String,
    val grupoSanguineo: String,
    val alergias: List<String>,
) : Persona(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia) {
    fun mostrarInfo(): String {
        return "$nombre $apellido - Historia Clínica: $historiaClinica"
    }
}

// Clase base para Empleado
open class Empleado(
    val codigoEmpleado: String,
    val horasExtras: Int,
    val fechaIngreso: LocalDate,
    val area: String,
    val cargo: String,
)

// Clase para Empleado por Planilla
class EmpleadoPlanilla(
    codigoEmpleado: String,
    horasExtras: Int,
    fechaIngreso: LocalDate,
    area: String,
    cargo: String,
    val salarioMensual: Double,
    val porcentajeExtra: Double,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
) : Empleado(codigoEmpleado, horasExtras, fechaIngreso, area, cargo) {
    // Propiedades heredadas de Persona
    val nombre = nombre
    val apellido = apellido

    fun calcularSalario(): Double {
        val salarioExtra = (salarioMensual / 30) * horasExtras * (porcentajeExtra / 100)
        return salarioMensual + salarioExtra
    }

    fun mostrarInfo(): String {
        return "$codigoEmpleado - $nombre $apellido - Cargo: $cargo - Salario: ${calcularSalario()}"
    }
}

// Clase para Empleado Eventual
class EmpleadoEventual(
    codigoEmpleado: String,
    horasExtras: Int,
    fechaIngreso: LocalDate,
    area: String,
    cargo: String,
    val honorariosPorHora: Double,
    val horasTotales: Int,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
) : Empleado(codigoEmpleado, horasExtras, fechaIngreso, area, cargo) {
    // Propiedades heredadas de Persona
    val nombre = nombre
    val apellido = apellido

    fun calcularHonorarios(): Double {
        return (horasTotales * honorariosPorHora) + (horasExtras * honorariosPorHora)
    }

    fun mostrarInfo(): String {
        return "$codigoEmpleado - $nombre $apellido - Cargo: $cargo - Honorarios: ${calcularHonorarios()}"
    }
}

// Clase para Médico
class Medico(
    dni: String,
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    direccion: String,
    ciudadProcedencia: String,
    val especialidad: String,
    val servicio: String,
    val numeroConsultorio: String,
) : Persona(dni, nombre, apellido, fechaNacimiento, direccion, ciudadProcedencia) {
    fun mostrarInfo(): String {
        return "$nombre $apellido - Especialidad: $especialidad - Consultorio: $numeroConsultorio"
    }
}

// Clase para Cita Médica
class CitaMedica(
    val paciente: Paciente,
    val medico: Medico,
    val fecha: LocalDate,
    val hora: String,
)

// Clase principal del sistema
class SistemaHospital {
    private val pacientes = mutableListOf<Paciente>()
    private val empleados = mutableListOf<Empleado>()
    private val medicos = mutableListOf<Medico>()
    private val citas = mutableListOf<CitaMedica>()
    private val especialidades = listOf(
        "Cirujano", "Oftalmólogo", "Pediatra", "Cardiólogo", "Dermatólogo",
        "Psiquiatra", "Ginecólogo", "Neurólogo", "Traumatólogo", "Médico General"
    )

    // Función para registrar un paciente
    fun registrarPaciente() {
        println("Registrar Paciente")
        val dni = solicitarDNI()
        val nombre = solicitarDato("Nombre")
        val apellido = solicitarDato("Apellido")
        val fechaNacimiento = solicitarFecha("Fecha de Nacimiento (Formato: yyyy-MM-dd)")
        val direccion = solicitarDato("Dirección")
        val ciudadProcedencia = solicitarDato("Ciudad de Procedencia")
        val historiaClinica = solicitarDato("Número de Historia Clínica")
        val sexo = solicitarDato("Sexo")
        val grupoSanguineo = solicitarDato("Grupo Sanguíneo")
        val alergias = solicitarLista("Lista de Alergias (Escriba 'fin' para terminar)")

        val paciente = Paciente(
            dni,
            nombre,
            apellido,
            fechaNacimiento,
            direccion,
            ciudadProcedencia,
            historiaClinica,
            sexo,
            grupoSanguineo,
            alergias
        )
        pacientes.add(paciente)
        println("Paciente registrado exitosamente.")
    }

    // Función para registrar un empleado
    fun registrarEmpleado() {
        println("Registrar Empleado")
        val codigoEmpleado = solicitarDato("Código de Empleado")
        val horasExtras = Random.nextInt(1, 11) // Generar horas extras aleatorias entre 1 y 10
        val fechaIngreso = solicitarFecha("Fecha de Ingreso (Formato: yyyy-MM-dd)")
        val area = solicitarDato("Área")
        val cargo = solicitarDato("Cargo")
        val nombre = solicitarDato("Nombre")
        val apellido = solicitarDato("Apellido")
        val fechaNacimiento = solicitarFecha("Fecha de Nacimiento (Formato: yyyy-MM-dd)")

        val tipoEmpleado = solicitarDato("Tipo de Empleado (1: Planilla, 2: Eventual)")

        when (tipoEmpleado) {
            "1" -> {
                val salarioMensual = solicitarNumero("Salario Mensual").toDouble()
                val porcentajeExtra = solicitarNumero("Porcentaje Adicional por Hora Extra").toDouble()
                val empleado = EmpleadoPlanilla(
                    codigoEmpleado,
                    horasExtras,
                    fechaIngreso,
                    area,
                    cargo,
                    salarioMensual,
                    porcentajeExtra,
                    nombre,
                    apellido,
                    fechaNacimiento,
                    area,
                    "Ciudad"
                )
                empleados.add(empleado)
                println("Empleado por Planilla registrado exitosamente.")
            }

            "2" -> {
                val honorariosPorHora = solicitarNumero("Honorarios por Hora").toDouble()
                val horasTotales = solicitarNumero("Número de Horas Totales (Normales + Extras)").toInt()
                val empleado = EmpleadoEventual(
                    codigoEmpleado,
                    horasExtras,
                    fechaIngreso,
                    area,
                    cargo,
                    honorariosPorHora,
                    horasTotales,
                    nombre,
                    apellido,
                    fechaNacimiento,
                    area,
                    "Ciudad"
                )
                empleados.add(empleado)
                println("Empleado Eventual registrado exitosamente.")
            }

            else -> println("Tipo de empleado no válido.")
        }
    }

    // Función para registrar un médico
    fun registrarMedico() {
        println("Registrar Médico")
        val dni = solicitarDNI()
        val nombre = solicitarDato("Nombre")
        val apellido = solicitarDato("Apellido")
        val fechaNacimiento = solicitarFecha("Fecha de Nacimiento (Formato: yyyy-MM-dd)")
        val direccion = solicitarDato("Dirección")
        val ciudadProcedencia = solicitarDato("Ciudad de Procedencia")
        val especialidad = seleccionarEspecialidad()
        val servicio = solicitarDato("Servicio")
        val numeroConsultorio = solicitarDato("Número de Consultorio")

        val medico = Medico(
            dni,
            nombre,
            apellido,
            fechaNacimiento,
            direccion,
            ciudadProcedencia,
            especialidad,
            servicio,
            numeroConsultorio
        )
        medicos.add(medico)
        println("Médico registrado exitosamente.")
    }

    // Función para registrar una cita médica
    fun registrarCita() {
        println("Registrar Cita Médica")
        listarPacientes()
        println("Seleccione el ID del paciente:")
        val idPaciente = readLine()?.toIntOrNull()
        val paciente = pacientes.getOrNull(idPaciente ?: 0)

        if (paciente != null) {
            listarMedicos()
            println("Seleccione el ID del médico:")
            val idMedico = readLine()?.toIntOrNull()
            val medico = medicos.getOrNull(idMedico ?: 0)

            if (medico != null) {
                val fecha = solicitarFecha("Fecha de la Cita (Formato: yyyy-MM-dd)")
                val hora = solicitarDato("Hora de la Cita (Formato: HH:mm)")
                val cita = CitaMedica(paciente, medico, fecha, hora)
                citas.add(cita)
                println("Cita médica registrada exitosamente.")
            } else {
                println("Médico no encontrado.")
            }
        } else {
            println("Paciente no encontrado.")
        }
    }

    // Función para listar pacientes
    fun listarPacientes() {
        println("--- Listado de Pacientes ---")
        for ((index, paciente) in pacientes.withIndex()) {
            println("$index: ${paciente.mostrarInfo()}")
        }
    }

    // Función para listar empleados
    fun listarEmpleados() {
        println("--- Listado de Empleados ---")
        for ((index, empleado) in empleados.withIndex()) {
            when (empleado) {
                is EmpleadoPlanilla -> println("$index: ${empleado.mostrarInfo()}")
                is EmpleadoEventual -> println("$index: ${empleado.mostrarInfo()}")
            }
        }
    }

    // Función para listar médicos
    fun listarMedicos() {
        println("--- Listado de Médicos ---")
        for ((index, medico) in medicos.withIndex()) {
            println("$index: ${medico.mostrarInfo()}")
        }
    }

    // Función para listar citas médicas
    fun listarCitas() {
        println("--- Listado de Citas Médicas ---")
        for ((index, cita) in citas.withIndex()) {
            println("$index: Paciente: ${cita.paciente.nombre} ${cita.paciente.apellido}, Médico: ${cita.medico.nombre} ${cita.medico.apellido}, Fecha: ${cita.fecha}, Hora: ${cita.hora}")
        }
    }

    // Función para solicitar DNI
    private fun solicitarDNI(): String {
        var dni: String
        do {
            dni = solicitarDato("DNI (8 dígitos)")
            if (!validarDNI(dni)) {
                println("DNI inválido. Debe tener 8 dígitos.")
            }
        } while (!validarDNI(dni))
        return dni
    }

    // Función para validar el DNI
    private fun validarDNI(dni: String): Boolean {
        return dni.matches(Regex("\\d{8}")) // Debe tener 8 dígitos
    }

    // Función para solicitar una fecha
    private fun solicitarFecha(mensaje: String): LocalDate {
        var fecha: LocalDate
        while (true) {
            println(mensaje)
            val fechaStr = readLine() ?: ""
            fecha = try {
                LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            } catch (e: Exception) {
                println("Fecha inválida. Use el formato yyyy-MM-dd.")
                continue
            }
            break // Salir del bucle si la fecha es válida
        }
        return fecha
    }

    // Función para solicitar datos
    private fun solicitarDato(mensaje: String): String {
        println(mensaje)
        return readLine() ?: ""
    }

    // Función para solicitar un número
    private fun solicitarNumero(mensaje: String): Int {
        println(mensaje)
        return readLine()?.toIntOrNull() ?: 0
    }

    // Función para solicitar una lista
    private fun solicitarLista(mensaje: String): List<String> {
        val lista = mutableListOf<String>()
        println(mensaje)
        while (true) {
            val item = readLine() ?: ""
            if (item.lowercase() == "fin") break
            lista.add(item)
        }
        return lista
    }

    // Función para seleccionar especialidad
    private fun seleccionarEspecialidad(): String {
        println("Seleccione una Especialidad:")
        especialidades.forEachIndexed { index, especialidad -> println("$index: $especialidad") }
        val seleccion = solicitarNumero("Seleccione el número de la especialidad")
        return especialidades.getOrNull(seleccion) ?: "Médico General" // Retorna médico general si no se encuentra
    }
}

// Función principal
fun main() {
    val sistema = SistemaHospital()
    while (true) {
        println("\n--- Sistema de Registro Hospitalario ---")
        println("1. Registrar Paciente")
        println("2. Registrar Empleado")
        println("3. Registrar Médico")
        println("4. Registrar Cita Médica")
        println("5. Listar Pacientes")
        println("6. Listar Empleados")
        println("7. Listar Médicos")
        println("8. Listar Citas Médicas")
        println("9. Salir")
        print("Seleccione una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> sistema.registrarPaciente()
            2 -> sistema.registrarEmpleado()
            3 -> sistema.registrarMedico()
            4 -> sistema.registrarCita()
            5 -> sistema.listarPacientes()
            6 -> sistema.listarEmpleados()
            7 -> sistema.listarMedicos()
            8 -> sistema.listarCitas()
            9 -> exitProcess(0)
            else -> println("Opción no válida.")
        }
    }
}
