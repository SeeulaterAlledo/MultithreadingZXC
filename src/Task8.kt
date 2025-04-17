import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import kotlin.concurrent.thread

fun main() {
    val queue: BlockingQueue<String> = ArrayBlockingQueue(10)

    val producer = thread {
        for (i in 1..20) {
            val item = "Продукт $i"
            println("🛠 Производитель создаёт: $item")
            queue.put(item)
            Thread.sleep(300)
        }
        queue.put("END")
    }

    val consumer = thread {
        while (true) {
            val item = queue.take()
            if (item == "END") break
            println("✅ Потребитель обрабатывает: $item")
            Thread.sleep(500)
        }
    }

    producer.join()
    consumer.join()
    println("Производственная линия завершена.")
}
