import java.util.*

object MultiThreadedSort {
    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val array = intArrayOf(9, 2, 7, 1, 5, 8, 4, 3, 6, 0)
        val numThreads = 2

        val partSize = array.size / numThreads
        val sortedParts = Collections.synchronizedList(ArrayList<IntArray>())
        val threads: MutableList<Thread> = ArrayList()

        for (i in 0..<numThreads) {
            val start = i * partSize
            val end = if (i == numThreads - 1) array.size else (i + 1) * partSize

            val subArray = Arrays.copyOfRange(array, start, end)

            val thread = Thread {
                Arrays.sort(subArray)
                sortedParts.add(subArray)
            }

            threads.add(thread)
            thread.start()
        }


        for (thread in threads) {
            thread.join()
        }

        val merged = mergeAndSort(sortedParts)
        println("Отсортированный массив: " + merged.contentToString())
    }


    fun mergeAndSort(sortedParts: List<IntArray>): IntArray {
        val mergedList: MutableList<Int> = ArrayList()
        for (part in sortedParts) {
            for (num in part) {
                mergedList.add(num)
            }
        }
        Collections.sort(mergedList)

        // Переводим в массив
        val result = IntArray(mergedList.size)
        for (i in mergedList.indices) {
            result[i] = mergedList[i]
        }
        return result
    }
}