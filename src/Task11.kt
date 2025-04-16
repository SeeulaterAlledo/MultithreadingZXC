fun main() {
    val A = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )

    val B = arrayOf(
        intArrayOf(9, 8, 7),
        intArrayOf(6, 5, 4),
        intArrayOf(3, 2, 1)
    )

    val rowsA = A.size
    val colsB = B[0].size
    val colsA = A[0].size
    val result = Array(rowsA) { IntArray(colsB) }

    val threads = mutableListOf<Thread>()

    for (i in 0 until rowsA) {
        val thread = Thread {
            for (j in 0 until colsB) {
                var sum = 0
                for (k in 0 until colsA) {
                    sum += A[i][k] * B[k][j]
                }
                result[i][j] = sum
            }
        }
        threads.add(thread)
        thread.start()
    }

    // Ждём завершения всех потоков
    for (thread in threads) {
        thread.join()
    }

    // Печатаем результат
    println("Результат умножения матриц:")
    for (row in result) {
        println(row.joinToString(" "))
    }
}
