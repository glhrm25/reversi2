import model.*
import ui.show

/* TO-DO LIST:
    -> Safety check of users input
    -> Game.kt:
        -- game.show(): Change $turn for the right player symbol
    ->
 */

fun main() {

    var game: Game? = null

    while(true) {
        print("> ")

        val input = readln().trim().uppercase().split(' ') // TO-DO: Safety checks of users input
        when (val command = input[0]) {
            "NEW" -> {
                val firstTurn = input[1][0]
                val name = input[2]
                game = game?.new() ?: Game(firstTurn = firstTurn, name = name)
            }
            //"JOIN" -> game = game?.join()
            "PLAY" -> {
                val move = input[1]
                val row = move[0].digitToIntOrNull() ?: -1
                val cell = Cell(row, move[1])
                if (row != -1 && cell.toBoardIndex() in 0.. BOARD_CELLS){
                    game = game?.play(cell)
                }
                else println("Invalid move: $move")
            }
            "PASS" -> {
                /*
                Passa a vez para o adversário se não for possível fazer uma jogada.
                O jogo termina caso o adversário também tenha passado na vez anterior.
                */
            }
            //"REFRESH" -> game = game?.refresh()
            "TARGETS" -> {
                /*
                Controlo da visualização das posições possíveis para jogar. Argumento: ON ou OFF
                para ligar ou desligar a visualização ou sem argumento para mostrar o estado da
                visualização. O estado da visualização mantém-se durante tdo o jogo.
                 */
            }
            "SHOW" -> game?.show()
            "EXIT" -> break
            else -> println("Invalid command $command")
        }

        // game?.show()
    }

    println("Game ended")
}