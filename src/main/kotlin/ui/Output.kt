package ui
import model.*

fun Game.show() {
    println("You are player $turn in game $name.") // Change $turn for the right player symbol
    println("  " + COLUMNS.joinToString(" "))
    board.chunked(BOARD_SIZE).forEachIndexed{ idx, row ->
        println("${idx+1} ${row.joinToString(" "){"$it"}}")
    }
    println("$SYMBOL1 =  ${board.count { c -> c == SYMBOL1 }} | $SYMBOL2 =  ${board.count { c -> c == SYMBOL2 }}")
    println("Turn: $turn")
} // Possibly put everything in one only println?