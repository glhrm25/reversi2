package user_interface
import model.*
import model.Player.*

const val BLACK_SYMBOL = '#'
const val WHITE_SYMBOL = '@'
const val EMPTY_SYMBOL = '.'
const val TARGETS_SYMBOL = '?'

fun Game.show() {
    println("You are player ${turn.symbol()} in game $name.") // Change first $turn.symbol for the right player symbol when multiplayer
    println("  " + COLUMNS.joinToString(" "))
    board.chunked(BOARD_SIZE).forEachIndexed{ idx, row ->
        println("${idx+1} ${row.joinToString(" "){"${it.symbol()}"}}")
    }
    println("$BLACK_SYMBOL =  ${board.count { c -> c == BLACK }} | $WHITE_SYMBOL =  ${board.count { c -> c == WHITE }}")
    println("Turn: ${turn.symbol()}")
} // Possibly put everything into one only println?

fun Player?.symbol(): Char =
    when (this) {
        BLACK -> BLACK_SYMBOL
        WHITE -> WHITE_SYMBOL
        else -> EMPTY_SYMBOL
    }