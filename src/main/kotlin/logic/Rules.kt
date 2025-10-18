package logic
import model.*

private val directions = listOf(
    1, -1,  // Horizontal
    BOARD_SIZE, -BOARD_SIZE, // Vertical
    BOARD_SIZE + 1, -BOARD_SIZE - 1, // backslash
    BOARD_SIZE - 1, -BOARD_SIZE + 1, // slash
)

fun Game.validMoves(): List<Position> {

    require(this.state is Run)
    val turn = state.turn

    val list = mutableSetOf<Position>()
    val opponent = turn.otherPlayer

    for (p in board) {
        if (p.value == opponent) {
            for (d in directions) {
                val pd = p.key.index + d

                if (pd in 0 until BOARD_CELLS && board[Position(pd)] == null) {
                    if (this.turnMoves(Position(pd)).isNotEmpty()) {
                        list.add(Position(pd))
                    }
                }
            }
        }
    }
    return list.toList()
}

fun Game.turnMoves(move: Position): List<Pair<Position, Player>> {

    require(this.state is Run)
    val turn = state.turn

    val list = mutableSetOf<MutableList<Int>>()
    val opponent = turn.otherPlayer

    val idx = move.index
    for (d in directions) {
        val pd = idx + d

        if (pd in 0 until BOARD_CELLS && board[Position(pd)] == opponent) {
            val l = mutableListOf<Int>()
            l.add(pd)
            var i = pd
            while (i in 0 until BOARD_CELLS){

                if (d == 1 || d == -1) {
                    if (!sameRow(i, i - d)) break
                }

                when (board[Position(i)]) {
                    opponent -> l.add(i)
                    turn -> {
                        list += l
                        break
                    }
                    else -> break
                }

                i += d
            }
        }
    }

    val turnList = list.flatten().distinct()
    //return board.mapIndexed{ idx, player -> if (idx in turnList) turn else player }
    return turnList.map { Pair(Position(it), turn) }
}

private fun sameRow(a: Int, b: Int): Boolean =
    (a / BOARD_SIZE) == (b / BOARD_SIZE)
