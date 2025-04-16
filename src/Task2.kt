import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

fun main() {
    val numberList = ConcurrentLinkedQueue<Int>()
    val threads = List(10) {
        thread {
            for (i in 1..100) {
                numberList.add(i)
            }
        }
    }

    threads.forEach { it.join() }

    println("Размер списка: ${numberList.size}")
    println("Пример содержимого (первые 20 элементов): ${numberList.take(20)}")
}
