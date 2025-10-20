package user_interface
import logic.validMoves
import model.*
import model.Player.*

const val BLACK_SYMBOL = '#'
const val WHITE_SYMBOL = '@'
const val EMPTY_SYMBOL = '.'
const val TARGETS_SYMBOL = '?'

fun Game.show() {
    //println("You are player ${turn.symbol()} in game $name.") // Change first $turn.symbol for the right player symbol when multiplayer
    println("  " + COLUMNS.joinToString(" "))
    val validMoves = validMoves()
    Position.values
        .map{
            if (state is Run && this.state.toggleTargets && it in validMoves) TARGETS_SYMBOL
            else board[it].symbol()
        }
        .chunked(BOARD_SIZE)
        .forEachIndexed { idx, row ->
            println("${idx+1} ${row.joinToString(" ")}")
        }

    println("$BLACK_SYMBOL =  ${board.count{ (_, Player) -> Player == BLACK}} | $WHITE_SYMBOL =  ${board.count { (_, Player) -> Player == WHITE}}")
    println("Turn: ${state}")
    //println(validMoves)
}

fun Player?.symbol(): Char =
    when (this) {
        BLACK -> BLACK_SYMBOL
        WHITE -> WHITE_SYMBOL
        else -> EMPTY_SYMBOL
    }