package model
import model.Player.*
import logic.*

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

typealias Board = Map<Position, Player>
typealias Score = Map<Player?, Int>

data class Game (
    val firstTurn : Player = BLACK, // ????
    val board: Board = generateBoard(),
    val state: GameState = Run(firstTurn),
    val score: Score = (Player.entries + null).associateWith { 0 }, // ????
    val toggleTargets: Boolean = false, // ????
    val name: String,
)

sealed class GameState
data class Run(val turn: Player): GameState()
data class Win(val winner: Player): GameState()
data object Draw: GameState()

// If game already exists, new game is created with same name and first turn corresponds to the opposite player
fun Game.new(): Game = Game(name = name, firstTurn = firstTurn.otherPlayer)

fun Game.play(move: Position): Game {
    check(this.state is Run) {"Game is not running."}
    require(board[move] == null) { "Cell $move already occupied." }
    require(move in validMoves()){"Invalid move $move."}

    return this.copy(
        board = board + (move to state.turn) + turnMoves(move),
        state = Run(state.turn.otherPlayer),
    )
}

// Returns a starting board with its middle cells already occupied with the players pieces.
private fun generateBoard(): Board {
    val middleColumn = BOARD_SIZE / 2

   return emptyMap<Position, Player>() +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn - 1])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn])) to WHITE) +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn - 1])) to WHITE)
}