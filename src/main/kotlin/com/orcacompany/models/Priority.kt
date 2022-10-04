package com.orcacompany.models

enum class Priority(val color: String, val squareNumber: Int) {
    I("lightcoral", 1),
    II("Green", 2),
    III("Orange", 3),
    IV("Red", 4);

    companion object {
        fun priorityFromInt(value: Int): Priority {
            return when (value) {
                1 -> I
                2 -> II
                3 -> III
                4 -> IV
                else -> IV
            }
        }
    }
}