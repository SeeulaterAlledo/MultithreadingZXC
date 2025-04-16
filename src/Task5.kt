import java.util.concurrent.CyclicBarrier
import kotlin.concurrent.thread

fun main() {
    val barrier = CyclicBarrier(5) {
        println("🔔 Все потоки завершили первую фазу. Переход ко второй фазе.")
    }

    val threads = List(5) { i ->
        thread {
            println("Поток $i выполняет первую часть работы.")
            Thread.sleep((1000..3000).random().toLong())
            println("Поток $i ожидает других на барьере...")
            barrier.await()

            println("Поток $i выполняет вторую часть работы.")
        }
    }

    threads.forEach { it.join() }
}
