package ui
import model.*

open class Command ( // Funciona como se fosse uma interface, onde os comandos derivam desta class
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false
) {
    open fun execute(args: List<String>, game: Game?): Game? = game
}

// Objetos são parecidos a classes só que são unicos.
// Podemos ter varios objetos que derivam de uma class mas um objeto é unico e nada deriva dele.
/*
object Exit: Command(isTerminate = true)

object New: Command(){
    override fun execute(args: List<String>, game: Game?): Game =
        game?.new() ?: Game()
}
*/

object Play: Command("<position>") {
    override fun execute(args: List<String>, game: Game?): Game {
        val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
        val pos = requireNotNull(arg.toIntOrNull()) {"Invalid position $arg."}

        return checkNotNull(game){"Game not created"}.play(pos)
    }
}

fun getCommands(): Map<String, Command> = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to object : Command() {
        override fun execute(args: List<String>, game: Game?): Game =
            game?.new() ?: Game()
    },
    "PLAY" to Play,
)