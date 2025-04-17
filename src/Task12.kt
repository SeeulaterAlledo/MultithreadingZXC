import kotlin.concurrent.Volatile

object TimerExample {
    @Volatile
    private var running = true

    @JvmStatic
    fun main(args: Array<String>) {
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

        val stopperThread = Thread {
            try {
                Thread.sleep(10000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            running = false
            println("🛑 Останавливаем таймер...")
        }

        timerThread.start()
        stopperThread.start()

        try {
            timerThread.join()
            stopperThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}