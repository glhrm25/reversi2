package model
import model.Color.*
import logic.*

const val BOARD_SIZE = 3 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

typealias Board = Map<Position, Color>

data class Game (
    val owner : Color = BLACK,
    val board: Board = generateBoard(),
    val state: GameState = Run(owner),
)

sealed class GameState
open class Run(
    val turn: Color,
): GameState()
class RunPassed(tr: Color): Run(tr)
data class Win(val winner: Color): GameState()
data object Draw: GameState()

fun Game.play(move: Position): Game {
    check(this.state is Run) {"Game has ended."}
    require(move in validMoves(state.turn)){"Invalid move $move."}

    val newBoard = board + (move to state.turn) + turnMoves(state.turn, move)
    return this.copy(
        board = newBoard,
        state = updateState(newBoard, state),
    )
}

fun Game.pass(): Game =
    with(this) {
        check(state is Run) {"Game has ended."}
        check(validMoves(state.turn).isEmpty()) { "There is possible moves for you to make." }

        if (state is RunPassed)
            copy(state = getEndState(board))

        else
            copy(state = RunPassed(state.turn.otherColor))
    }

/**
 * @param board The game's board
 * @param state The game's actual state
 * @return Updated state of the game
 */
private fun updateState(board: Board, state: Run): GameState =
    if (board.size != BOARD_CELLS)
        Run(state.turn.otherColor)
    else
        getEndState(board)

/**
 * @return Player with the most amount of pieces on the board
 */
private fun Board.mostCommonPieces(): Color? {
    val dif = this.count{ it.value == BLACK} - this.count{ it.value == WHITE}
    return when {
        dif > 0 -> BLACK
        dif < 0 -> WHITE
        else -> null
    }
}

/**
 * @param board The game's board
 * @return Gamestate Win or Draw, depending on the number of pieces on the board for each player
 */
private fun getEndState(board: Board): GameState =
    when (board.mostCommonPieces()) {
        WHITE -> Win(WHITE)
        BLACK -> Win(BLACK)
        else -> Draw
    }

/**
 * @return Starting board with its middle cells already occupied with the players pieces.
  */
private fun generateBoard(): Board {
    val middleColumn = BOARD_SIZE / 2

   return emptyMap<Position, Color>() +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn - 1])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn])) to WHITE) +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn - 1])) to WHITE)
}