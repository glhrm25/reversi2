package user_interface
import model.*

open class Command (
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false
) {
    open fun execute(args: List<String>, game: Game?): Game? = game
}

object New: Command("<FirstTurn> <Name>"){
    override fun execute(args: List<String>, game: Game?): Game {
        require(args.size == 2) {"Invalid Arguments."}

        val first = args.first()
        require(first.length == 1) {"Invalid argument $first"}

        val symbol = first.first()
        require(symbol == BLACK_SYMBOL || symbol == WHITE_SYMBOL){"Invalid symbol $first."}

        return game?.new() ?: Game(turn = symbol.player(), name = args.drop(1).first())
    }
}

object Play: Command("<position>") {
    override fun execute(args: List<String>, game: Game?): Game {
        val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
        require(arg.length == 2) { "Invalid argument $arg." }

        return checkNotNull(game){"Game not created"}.play(arg.toBoardCell())
    }
}

object Show: Command() {
    override fun execute(args: List<String>, game: Game?): Game {
        checkNotNull(game){"Game not created."}.show()
        return game
    }
}

object Pass: Command() {
/*
    Passa a vez para o adversário se não for possível fazer uma jogada.
    O jogo termina caso o adversário também tenha passado na vez anterior.
*/
}


object Targets: Command(){
    override fun execute(args: List<String>, game: Game?): Game? =
        game?.copy(toggleTargets = !game.toggleTargets)
}

fun getCommands(): Map<String, Command> = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to New,
    "PLAY" to Play,
    "SHOW" to Show,
    "PASS" to Pass, // NOT IMPLEMENTED YET
    "TARGETS" to Targets, // NOT IMPLEMENTED YET
)

    /*
"JOIN" -> game = game?.join()
"REFRESH" -> game = game?.refresh()
     */
