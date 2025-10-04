package model
import model.Player.*

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE
private val EMPTY = null

data class Game(
    val name: String,
    val board: List<Player?> = generateBoard(),
    val firstTurn: Player,
    val turn: Player = firstTurn,
    val hasPreviousPassed: Boolean = false,
)

fun Game.new(): Game = Game(name = name, firstTurn = firstTurn.otherPlayer())

fun Game.play(move: Cell): Game {
    val pos = move.toBoardIndex()
    check(board[pos] == EMPTY) { "Cell $move already occupied." }

    return this.copy(
        board = board.mapIndexed { idx, player -> if (idx == move.toBoardIndex()) turn else player },
        turn = turn.otherPlayer(),
    )
}

// Returns a starting BOARD_SIZE x BOARD_SIZE board, with the middle cells of the board already occupied with the players pieces.
private fun generateBoard(): List<Player?> {
    val middleColumn = BOARD_SIZE / 2

   return List(BOARD_CELLS) { EMPTY }.mapIndexed { idx, player ->
        when (idx) {
            Cell(middleColumn, COLUMNS[middleColumn]).toBoardIndex(),
            Cell(middleColumn + 1, COLUMNS[middleColumn - 1]).toBoardIndex(), -> BLACK

            Cell(middleColumn + 1, COLUMNS[middleColumn]).toBoardIndex(),
            Cell(middleColumn, COLUMNS[middleColumn - 1]).toBoardIndex(), -> WHITE

            else -> player
        }
    }
}