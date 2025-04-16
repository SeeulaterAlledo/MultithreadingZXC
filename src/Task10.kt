import java.util.concurrent.locks.ReentrantLock

internal class Fork {
    private val lock = ReentrantLock()

    fun pickUp(): Boolean {
        return lock.tryLock() // Не блокируем поток, если вилка занята
    }

    fun putDown() {
        lock.unlock()
    }
}

internal class Philosopher(private val id: Int, private val leftFork: Fork?, private val rightFork: Fork?) : Thread() {
    @Throws(InterruptedException::class)
    private fun think() {
        println("Философ $id думает.")
        sleep((Math.random() * 1000).toInt().toLong())
    }

    @Throws(InterruptedException::class)
    private fun eat() {
        println("Философ $id ест.")
        sleep((Math.random() * 1000).toInt().toLong())
    }

    override fun run() {
        try {
            while (true) {
                think()

                if (leftFork!!.pickUp()) {
                    if (rightFork!!.pickUp()) {
                        eat()
                        rightFork.putDown()
                    }
                    leftFork.putDown()
                }

                // Немного подождать, чтобы другие потоки тоже могли захватить вилки
                sleep((Math.random() * 500).toInt().toLong())
            }
        } catch (e: InterruptedException) {
            println("Философ $id прерван.")
        }
    }
}

object DiningPhilosophers {
    @JvmStatic
    fun main(args: Array<String>) {
        val numPhilosophers = 5
        val forks = arrayOfNulls<Fork>(numPhilosophers)
        val philosophers = arrayOfNulls<Philosopher>(numPhilosophers)

        // Инициализация вилок
        for (i in 0..<numPhilosophers) {
            forks[i] = Fork()
        }

        // Инициализация философов
        for (i in 0..<numPhilosophers) {
            val leftFork = forks[i]
            val rightFork = forks[(i + 1) % numPhilosophers]

            // Для последнего философа поменять порядок вилок, чтобы избежать deadlock
            if (i == numPhilosophers - 1) {
                philosophers[i] = Philosopher(i, rightFork, leftFork)
            } else {
                philosophers[i] = Philosopher(i, leftFork, rightFork)
            }

            philosophers[i]!!.start()
        }
    }
}