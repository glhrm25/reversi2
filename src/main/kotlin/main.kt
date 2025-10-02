import Model.*

/* TO-DO LIST:
    -> Verificação dos inputs do utilizador
 */

fun main() {

    var game: Game? = null

    while(true) {
        print("> ")

        val input = readln().trim().uppercase().split(' ')
        when (val cmd = input[0]) {
            "NEW" -> {
                val firstTurn = input[1][0]
                val name = input[2]
                game = game?.new() ?: Game(firstTurn = firstTurn, name = name)
            }
           // "JOIN" -> game = game?.join()
            //"PLAY" -> game?.play()
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
                visualização. O estado da visualização mantém-se durante tod o jogo.
                 */
            }
            "SHOW" -> game?.show()
            "EXIT" -> break
            else -> println("Invalid command $cmd")
        }

        // game?.show()
    }

    println("Game ended")
}