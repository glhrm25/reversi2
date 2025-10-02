package Model

const val BOARD_SIZE = 9 // Sets the number of rows and columns of the board
const val SYMBOL1 = '#'
const val SYMBOL2 = '@'

data class Game(
    val name: String,
    /*
    val board: List<Char> = listOf(
        ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
    ),
     */
    val board: List<Char> = generateBoard(),
    val firstTurn: Char,
    val turn: Char = firstTurn,
)

fun Game.new(): Game = Game(name = name, firstTurn = firstTurn.otherPlayer())

// Retirar esta função deste ficheiro por não ser uma função pura
// Por em que ficheiro?
fun Game.show() {
    ('A'..'Z').forEachIndexed{idx, char ->
        if (idx < BOARD_SIZE) print("$char ")
    }
    print("\n")
    board.chunked(BOARD_SIZE).forEachIndexed{idx, row ->
        println("$idx ${row.joinToString(" "){"$it"}}")
    }
}

private fun Char.otherPlayer(): Char = if (this == SYMBOL1) SYMBOL2 else SYMBOL1

// Returns a BOARD_SIZE x BOARD_SIZE board, filled with '.' .
private fun generateBoard(): List<Char> {
    val list = List(BOARD_SIZE * BOARD_SIZE) {'.'}
}
