package user_interface
import logic.validMoves
import model.*

class Command (
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false,
    val execute: (List<String>, Game?) -> Game? = {_, game -> game},
)

val new = Command("<FirstTurn> <Name>"){ args, game ->
        if(args.isEmpty()) Game(name = "LOCAL")

        else {
            val first = args.first()
            require(first.length == 1) {"Invalid argument $first"} // If argument is not a character

            val symbol = first.first()
            require(symbol == BLACK_SYMBOL || symbol == WHITE_SYMBOL){"Invalid symbol $first."} // If symbol is not a valid symbol

            val argName = args.drop(1) // if name not specified then game is a local game
            game?.new() ?: Game(firstTurn = symbol.player(), name = if (argName.isNotEmpty()) argName.first() else "local")
        }
}

val play = Command("<position>") { args, game ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
   // val pos = requireNotNull(arg.toCellOrNull()) {"Invalid position $arg."}
    require(arg.length == 2) { "Invalid argument $arg." }

    checkNotNull(game){"Game not created"}.play(arg.toBoardPosition())
}

val show = Command() {_, game ->
    game.also{ checkNotNull(game){"Game not created."}.show() }
}

val targets = Command(){_, game ->
    checkNotNull(game){"Game not created"}
    check(game.state is Run) {"Game has ended."}
    game.copy(toggleTargets = !game.toggleTargets) // Game should not be null by this line ????
}

val pass = Command(){_, game ->
    checkNotNull(game){"Game not created"}.pass()
}

fun getCommands(): Map<String, Command> = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to new,
    "PLAY" to play,
    "SHOW" to show,
    "PASS" to pass,
    "TARGETS" to targets,
)

    /*
"JOIN" -> game = game?.join()
"REFRESH" -> game = game?.refresh()

    Passa a vez para o adversário se não for possível fazer uma jogada.
    O jogo termina caso o adversário também tenha passado na vez anterior.
val pass = Command(){ args, game ->
    // TO-DO
    game
}
 */