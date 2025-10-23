package user_interface
import storage.*
import model.*

typealias GameStorage = Storage<String, Game>

class Command (
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false,
    val execute: (List<String>, Game?, gs: GameStorage) -> Game? = {_, game, _ -> game},
)

private fun storageCommand(fx: (String, Game?) -> Game) = Command("<name>") {args, game, _ ->
    val name = requireNotNull(args.firstOrNull()) {"Missing name"}
    fx(name, game)
}

val new = Command("<FirstTurn> <Name>"){ args, game, gs ->
    val argSymbol = if (args.isNotEmpty()) args.first()
                    else "${Player.entries.random().symbol()}" // If first turn not mentioned, selects a random player
    require(argSymbol.length == 1) {"Invalid argument $argSymbol"} // If argument is not a character

    val symbol = argSymbol.first()
    require(symbol == BLACK_SYMBOL || symbol == WHITE_SYMBOL){"Invalid symbol $argSymbol."} // If symbol is not a valid symbol

    val argName = args.drop(1) // if name not specified then game is a local game
    val newGame = game?.new() ?: Game(firstTurn = symbol.player(), name = if (argName.isNotEmpty()) argName.first() else "local")
    newGame.also {
        gs.create(newGame.name, newGame)
    }
}

val play = Command("<position>") { args, game, gs ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
   // val pos = requireNotNull(arg.toCellOrNull()) {"Invalid position $arg."}
    require(arg.length == 2) { "Invalid argument $arg." }

    val newgame = checkNotNull(game){"Game not created"}.play(arg.toBoardPosition())
    newgame.also{
        gs.update(newgame.name, newgame)
    }
}

val show = Command {_, game, _ ->
    game.also{ checkNotNull(game){"Game not created."}.show() }
}

val targets = Command{_, game, gs ->
    checkNotNull(game){"Game not created"}
    with(game){
        check(state is Run) {"Game has ended."}
        val newgame = copy(state = state.copy(toggleTargets = !state.toggleTargets))
        newgame.also{
            gs.update(newgame.name, newgame)
        }
    }
}

val pass = Command{_, game, gs ->
    val newgame = checkNotNull(game){"Game not created"}.pass()
    newgame.also{
        gs.update(newgame.name, newgame)
    }
}

val join = Command("<Name>"){args, _, gs ->
    require(args.isNotEmpty()){"Missing game's name"}
    val name = args.first()
    val game = gs.read(name)
    checkNotNull(game){"Game $name is not running."}
}

val refresh = Command{_, game, gs ->
    requireNotNull(game){"Game is not running"}
    gs.read(game.name)
}

fun getCommands() = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to new,
    "PLAY" to play,
    "SHOW" to show,
    "PASS" to pass,
    "TARGETS" to targets,
    "JOIN" to join,
    "REFRESH" to refresh,
)