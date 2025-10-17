package model
import model.Player.*
import logic.*

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

typealias Board = Map<Cell, Player>

data class Game(
    val name: String,
    val board: Board = generateBoard(),
    val turn: Player,
    //val hasPreviousPassed: Boolean = false,
    val toggleTargets: Boolean = false,
)

// If game already exists, new game is created with same name and the opposite player has first turn.
fun Game.new(): Game = Game(name = name, turn = turn.otherPlayer)

fun Game.play(move: Cell): Game {
    check(move !in board) { "Cell $move already occupied." }
    check(move in validMoves()){"Invalid move $move."}

    return this.copy(
        board = board + (move to turn) + turnMoves(move),
        turn = turn.otherPlayer,
    )
}

// Returns a starting BOARD_SIZE x BOARD_SIZE board, with the middle cells of the board already occupied with the players pieces.
private fun generateBoard(): Board {
    val middleColumn = BOARD_SIZE / 2

   return emptyMap<Cell, Player>() +
           (Cell(toBoardIndex(middleColumn, COLUMNS[middleColumn])) to BLACK) +
           (Cell(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn - 1])) to BLACK) +

           (Cell(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn])) to WHITE) +
           (Cell(toBoardIndex(middleColumn, COLUMNS[middleColumn - 1])) to WHITE)
}