package model

const val BOARD_SIZE = 8 // Sets the number of rows and columns of the board
const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE
const val SYMBOL1 = '#'
const val SYMBOL2 = '@'

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

// Retirar esta função deste ficheiro por não ser uma função pura
// Por em que ficheiro?
private val COLUMNS = ('A'..<'A' + BOARD_SIZE).toList()
fun Game.show() {
    println("You are player $turn in game $name.") // Change $turn for the right player symbol
    println("  " + COLUMNS.joinToString(" "))
    board.chunked(BOARD_SIZE).forEachIndexed{idx, row ->
        println("${idx+1} ${row.joinToString(" "){"$it"}}")
    }
    println("$SYMBOL1 =  | $SYMBOL2 =  ")
    println("Turn: $turn")
} // Possibly put everything in one only println?

fun Game.play(move: Cell): Game =
    this.copy(
        board = board.mapIndexed { idx, char -> if (idx == move.toBoardIndex()) turn else char },
        turn = turn.otherPlayer(),
    )


private fun Char.otherPlayer(): Char = if (this == SYMBOL1) SYMBOL2 else SYMBOL1

// Returns a BOARD_SIZE x BOARD_SIZE board, filled with '.' .
private fun generateBoard(): List<Char> = List(BOARD_CELLS) {'.'}