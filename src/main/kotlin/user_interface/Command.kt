package user_interface
import storage.*
import model.*

class Command (
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false,
    val execute: (List<String>, Game?) -> Game? = {_, game -> game},
)

val gameSerializer = GameSerializer
val gameStorage = TextFileStorage<String, Game>("GamesStorage", gameSerializer)

val new = Command("<FirstTurn> <Name>"){ args, game ->
        //if(args.isEmpty()) Game(name = "LOCAL")

        //else {
            val argSymbol = if (args.isNotEmpty()) args.first()
                            else "${Player.entries.random().symbol()}" // If first turn not mentioned, selects a random player
            require(argSymbol.length == 1) {"Invalid argument $argSymbol"} // If argument is not a character

            val symbol = argSymbol.first()
            require(symbol == BLACK_SYMBOL || symbol == WHITE_SYMBOL){"Invalid symbol $argSymbol."} // If symbol is not a valid symbol

            val argName = args.drop(1) // if name not specified then game is a local game
            val newGame = game?.new() ?: Game(firstTurn = symbol.player(), name = if (argName.isNotEmpty()) argName.first() else "local")
            newGame.also {
                gameStorage.create(newGame.name, newGame)
            }
        //}
}

val play = Command("<position>") { args, game ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
   // val pos = requireNotNull(arg.toCellOrNull()) {"Invalid position $arg."}
    require(arg.length == 2) { "Invalid argument $arg." }

    checkNotNull(game){"Game not created"}.play(arg.toBoardPosition())
}

val show = Command {_, game ->
    game.also{ checkNotNull(game){"Game not created."}.show() }
}

val targets = Command{_, game ->
    checkNotNull(game){"Game not created"}
    with(game){
        check(state is Run) {"Game has ended."}
        copy(state = state.copy(toggleTargets = !state.toggleTargets))
    }
}


val pass = Command{_, game ->
    checkNotNull(game){"Game not created"}.pass()
}

fun getCommands(): Map<String, Command> = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to new,
    "PLAY" to play,
    "SHOW" to show,
    "PASS" to pass,
    "TARGETS" to targets,
    // "JOIN" to join,
    // "REFRESH" to refresh,
)