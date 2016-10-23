package de.reimerm.splintersweets

object Log {

    fun d(message: String, vararg data: Any) {
        println(String.format(message, *data))
    }

    fun e(message: String, vararg data: Any) {
        System.err.println(String.format(message, *data))
    }
}
