package logic
import model.*

private val directions = listOf(
    1, -1,
    BOARD_SIZE, -BOARD_SIZE,
    BOARD_SIZE + 1, -BOARD_SIZE + 1,
    BOARD_SIZE - 1, -BOARD_SIZE - 1,
)

/*
fun Game.targets(): List<Int> {


}
*/
fun Game.validMoves(): List<Int> {

    val list = mutableSetOf<Int>()
    val opponent = turn.otherPlayer

    for (p in board.indices) {
        if (board[p] == null) {
            for (d in directions) {
                val pd = p + d

                if (pd in board.indices && board[pd] == opponent) {
                    var i = pd
                    while (i in board.indices){

                        if (d == 1 || d == -1) {
                            if (!sameRow(i, i - d)) break
                        }

                        when  (board[i]) {
                            turn -> {
                                list.add(p)
                                break
                            }
                            opponent -> {}
                            else -> break
                        }

                        i += d
                    }
                }
            }
        }
    }

    return list.toList()
}


fun Game.turnMoves(move: Int): List<Player?> {

    val list = mutableListOf<MutableList<Int>>()
    val opponent = turn.otherPlayer

    for (d in directions) {
        val pd = move + d

        if (pd in board.indices && board[pd] == opponent) {
            val l = mutableListOf<Int>()
            l.add(pd)
            var i = pd
            while (i in board.indices){

                if (d == 1 || d == -1) {
                    if (!sameRow(i, i - d)) break
                }

                when (board[i]) {
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

    val turnList = list.flatten().distinct() + move
    return board.mapIndexed{ idx, player -> if (idx in turnList) turn else player }
}

private fun sameRow(a: Int, b: Int): Boolean =
    (a / BOARD_SIZE) == (b / BOARD_SIZE)
