package com.geo.ablemove.data.util

fun getFirstLettersName(name: String): String {
    return name.trim()
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }
        .joinToString("") { it.first().uppercase() }
}