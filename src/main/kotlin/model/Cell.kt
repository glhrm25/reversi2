package model

val COLUMNS = ('A' until 'A' + BOARD_SIZE).toList()
val ROWS = (1 .. BOARD_SIZE).toList()

class Cell (
    val row: Int,
    val column: Char,
) {
    override fun toString(): String = "$row" + "$column"
}

fun Cell.toBoardIndex(): Int = (row-1) * BOARD_SIZE + column.toIntColumn()

fun String.toBoardCell(): Cell {
    val r = this[0].digitToIntOrNull()
    val c = this[1]

    require(r != null && r in ROWS && c in COLUMNS) {"Invalid cell $this."}

    return Cell(r, c)
}

private fun Char.toIntColumn(): Int = this - 'A'