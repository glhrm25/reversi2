package model

class Cell (
    val row: Int,
    val column: Char,
)

fun Cell.toBoardIndex(): Int = (row-1) * BOARD_SIZE + column.toIntColumn()

private fun Char.toIntColumn(): Int = this - 'A'