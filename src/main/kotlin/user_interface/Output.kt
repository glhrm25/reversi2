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
    Cell.values
        .map{board[it]}
        .chunked(BOARD_SIZE)
        .forEachIndexed { idx, row ->
            println("${idx+1} ${row.joinToString(" "){"${it.symbol()}"}}")
        }

    println("$BLACK_SYMBOL =  ${board.count{ (Cell, Player) -> Player == BLACK}} | $WHITE_SYMBOL =  ${board.count { (Cell, Player) -> Player == WHITE}}")
    println("Turn: ${turn.symbol()}")
}

fun Player?.symbol(): Char =
    when (this) {
        BLACK -> BLACK_SYMBOL
        WHITE -> WHITE_SYMBOL
        TARGET -> TARGETS_SYMBOL
        else -> EMPTY_SYMBOL
    }