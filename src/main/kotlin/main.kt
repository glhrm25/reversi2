import model.*
import ui.*

/* TO-DO LIST:
    -> Safety check of users input
    -> Game.kt:
        -- game.show(): Change $turn for the right player symbol
    ->
 */

fun main() {

    var game: Game? = null

    val cmds: Map<String, Command> = getCommands()

    while(true) {
        // Retorna uma data class onde o primeiro componente da class vai para "name" e o segundo para "args".
        val (name, args) = readCommand()

        val command = cmds[name]
        if (command == null) println("Invalid command $name")
        else try { // Tenta executar o código e caso haja uma exceção, resolve o código dos catch caso seja a exceção correta

            // Lança uma exceção caso não consiga executar o comando, caso contrário, retorna o game atualizado
            game = command.execute(args, game)
            if (command.isTerminate) return
            game?.show()
        } catch (ex: IllegalArgumentException) {
            println(ex.message)
            println("Use $name ${command.syntaxArgs}")
        } catch (ex: IllegalStateException) {
            println(ex.message)
        }

    }

/*
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
*/
    println("Game ended")
}