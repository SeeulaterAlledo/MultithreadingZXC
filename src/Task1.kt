import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

var counter = 0
val lock = ReentrantLock()

fun main() {
    val threads = List(5) {
        thread {
            repeat(1000) {
                lock.lock()
                try {
                    counter++
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    threads.forEach { it.join() }

    println("Итоговое значение счётчика: $counter")
}
