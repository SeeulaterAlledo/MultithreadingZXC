import kotlin.concurrent.thread

class BankAccount(initialBalance: Int) {
    var balance = initialBalance
        private set

    // Общий объект для синхронизации на каждый аккаунт
    private val lock = Any()

    fun transfer(to: BankAccount, amount: Int) {
        // Сначала определим порядок блокировок (по хешу)
        val first = if (this.hashCode() < to.hashCode()) this else to
        val second = if (this.hashCode() < to.hashCode()) to else this

        synchronized(first.lock) {
            synchronized(second.lock) {
                if (this.balance >= amount) {
                    this.balance -= amount
                    to.balance += amount
                }
            }
        }
    }

    fun safeGetBalance(): Int {
        synchronized(lock) {
            return balance
        }
    }
}

fun main() {
    val accounts = List(5) { BankAccount(1000) }

    val threads = List(10) {
        thread {
            repeat(1000) {
                val from = accounts.random()
                val to = accounts.random()
                val amount = (1..50).random()
                if (from !== to) {
                    from.transfer(to, amount)
                }
            }
        }
    }

    threads.forEach { it.join() }

    val total = accounts.sumOf { it.safeGetBalance() }
    println("Общий баланс после переводов: $total")
    accounts.forEachIndexed { index, acc ->
        println("Аккаунт $index: баланс = ${acc.safeGetBalance()}")
    }
}
