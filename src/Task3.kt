import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newFixedThreadPool(4)

    for (i in 1..20) {
        executor.execute {
            println("Задача $i выполняется в потоке ${Thread.currentThread().name}")
        }
    }

    executor.shutdown()
}
