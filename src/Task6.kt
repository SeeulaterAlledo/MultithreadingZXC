import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

fun main() {
    val semaphore = Semaphore(2)

    val threads = List(5) { i ->
        thread {
            println("Поток $i ждёт доступа к ресурсу...")
            semaphore.acquire()
            println("✅ Поток $i получил доступ к ресурсу.")
            Thread.sleep(2000)
            println("⛔ Поток $i освобождает ресурс.")
            semaphore.release()
        }
    }

    threads.forEach { it.join() }
}
