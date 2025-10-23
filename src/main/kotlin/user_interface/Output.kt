package user_interface
import logic.validMoves
import model.*
import model.Color.*

const val BLACK_SYMBOL = '#'
const val WHITE_SYMBOL = '@'
const val EMPTY_SYMBOL = '.'
const val TARGETS_SYMBOL = '?'

fun Game.show() {
    if (this.name == null && state is Run) println("You are player ${state.turn.symbol()} in local game.")
    else println("You are player ${pl.color.symbol()} in game $name.")

    println("  " + COLUMNS.joinToString(" "))
    val validMoves = validMoves()
    Position.values
        .map{
            if (this.pl.toggleTargets && it in validMoves) TARGETS_SYMBOL
            else board[it].symbol()
        }
        .chunked(BOARD_SIZE)
        .forEachIndexed { idx, row ->
            println("${idx+1} ${row.joinToString(" ")}")
        }

    println("$BLACK_SYMBOL =  ${board.count{ (_, player) -> player == BLACK}} | $WHITE_SYMBOL =  ${board.count { (_, player) -> player == WHITE}}")
    when (state) {
        is Run -> println("Turn: ${state.turn.symbol()}")
        is Win -> println("Winner: ${state.winner.symbol()}")
        Draw -> println("Draw")
    }
}

fun Color?.symbol(): Char =
    when (this) {
        BLACK -> BLACK_SYMBOL
        WHITE -> WHITE_SYMBOL
        else -> EMPTY_SYMBOL
    }