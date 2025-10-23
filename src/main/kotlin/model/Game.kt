package model
import model.Player.*
import logic.*

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

typealias Board = Map<Position, Player>
//typealias Score = Map<Player?, Int>

data class Game (
    val firstTurn : Player = BLACK,
    val board: Board = generateBoard(),
    val state: GameState = Run(firstTurn),
    //val score: Score = (Player.entries + null).associateWith { 0 }, // ????
    val name: String?,
) {
    // val validMoves get() = validMoves()
}

sealed class GameState
data class Run(
    val turn: Player,
    val toggleTargets: Boolean = false,
    val hasPreviousPassed: Boolean = false,
): GameState()
data class Win(val winner: Player): GameState()
data object Draw: GameState()

// If game already exists, new game is created with same name and first turn corresponds to the opposite player
fun Game.new(): Game = Game(name = name, firstTurn = firstTurn.otherPlayer)

fun Game.play(move: Position): Game {
    check(this.state is Run) {"Game has ended."}
    require(board[move] == null) { "Cell $move already occupied." }
    require(move in validMoves()){"Invalid move $move."}

    val newState = updateState()
    return this.copy(
        board = board + (move to state.turn) + turnMoves(move),
        state = if (newState is Run) newState.copy(hasPreviousPassed = false) else newState,
    )
}

fun Game.pass(): Game =
    with(this) {
        check(state is Run) {"Game has ended."}
        check(validMoves().isEmpty()) { "There's possible moves for you to make." }

        if (state.hasPreviousPassed)
            copy(state = getEndState(board))

        else {
            val newState = updateState()
            copy(
                state = if (newState is Run) newState.copy(hasPreviousPassed = true) else newState,
            )
        }
    }

/**
 * @return Updated state of the game
 */
private fun Game.updateState(): GameState =
    if (board.size != BOARD_CELLS && state is Run)
        Run(state.turn.otherPlayer)
    else
        getEndState(board)

/**
 * @return Player with the most amount of pieces on the board
 */
private fun Board.mostCommonPieces(): Player? {
    val dif = this.count{ it.value == BLACK} - this.count{ it.value == WHITE}
    return when {
        dif > 0 -> BLACK
        dif < 0 -> WHITE
        else -> null
    }
}

/**
 * @param board the game's board
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

   return emptyMap<Position, Player>() +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn - 1])) to BLACK) +
           (Position(toBoardIndex(middleColumn + 1, COLUMNS[middleColumn])) to WHITE) +
           (Position(toBoardIndex(middleColumn, COLUMNS[middleColumn - 1])) to WHITE)
}