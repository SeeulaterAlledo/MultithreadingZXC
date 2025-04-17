import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

fun factorial(n: Int): Long {
    return if (n <= 1) 1 else n * factorial(n - 1)
}

fun main() {
    val executor = Executors.newFixedThreadPool(5)
    val tasks = List(10) { i ->
        Callable {
            val number = (5..15).random()
            val result = factorial(number)
            "Факториал числа $number = $result (Поток: ${Thread.currentThread().name})"
        }
    }

    val futures: List<Future<String>> = tasks.map { executor.submit(it) }

    futures.forEach { future ->
        println(future.get())
    }

    executor.shutdown()
}
