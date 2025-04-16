import kotlin.concurrent.Volatile

object TimerExample {
    // Флаг для управления таймером
    @Volatile
    private var running = true

    @JvmStatic
    fun main(args: Array<String>) {
        // Поток таймера

        val timerThread = Thread(Runnable {
            var seconds = 0
            while (running) {
                println("Прошло секунд: $seconds")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    println("Таймер прерван.")
                    return@Runnable
                }
                seconds++
            }
            println("⏹ Таймер остановлен.")
        })

        // Поток-останавливающий
        val stopperThread = Thread {
            try {
                Thread.sleep(10000) // Ждём 10 секунд
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            running = false
            println("🛑 Останавливаем таймер...")
        }

        // Запуск потоков
        timerThread.start()
        stopperThread.start()

        // Ждём завершения
        try {
            timerThread.join()
            stopperThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}