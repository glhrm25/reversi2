package model

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE
const val SYMBOL1 = '#'
const val SYMBOL2 = '@'
val COLUMNS = ('A'..<'A' + BOARD_SIZE).toList()

data class Game(
    val name: String,
    val board: List<Char> = generateBoard(),
    val firstTurn: Char,
    val turn: Char = firstTurn,
)

fun Game.new(): Game =
    Game(
        name = name,
        firstTurn = firstTurn.otherPlayer(),
        )

fun Game.play(move: Cell): Game =
    this.copy(
        board = board.mapIndexed { idx, char -> if (idx == move.toBoardIndex()) turn else char },
        turn = turn.otherPlayer(),
    )


private fun Char.otherPlayer(): Char = if (this == SYMBOL1) SYMBOL2 else SYMBOL1

// Returns a starting BOARD_SIZE x BOARD_SIZE board, with the middle cells of the board already occupied with the players pieces.
private fun generateBoard(): List<Char> =
    List(BOARD_CELLS) {'.'}.mapIndexed{ idx, char ->
        when (idx) {
            Cell(BOARD_SIZE/2, COLUMNS[BOARD_SIZE/2]).toBoardIndex(), Cell(BOARD_SIZE/2 + 1, COLUMNS[BOARD_SIZE/2 - 1]).toBoardIndex(), -> SYMBOL1
            Cell(BOARD_SIZE/2 + 1, COLUMNS[BOARD_SIZE/2]).toBoardIndex(), Cell(BOARD_SIZE/2, COLUMNS[BOARD_SIZE/2 - 1]).toBoardIndex(), -> SYMBOL2
            else -> char
        }
    }