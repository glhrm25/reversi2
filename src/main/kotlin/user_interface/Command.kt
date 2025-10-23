package user_interface
import storage.*
import model.*

typealias GameStorage = Storage<String, Game>

class Command (
    val syntaxArgs: String = "",
    val isTerminate: Boolean = false,
    val execute: (List<String>, Game?, gs: GameStorage) -> Game? = {_, game, _ -> game},
)

/**
 * Updates the game file stored in the game storage if game is not a local game.
 * @param game is current game to write on the file
 * @param gs is GameStorage where it will be created or update the game's file
 * @return Returns the game received
 */
private fun updateGameFile(game: Game, gs: GameStorage): Game =
    game.also{
        if (game.name != null) {
            gs.update(game.name, game)
        }
    }

/**
 * Creates the game file stored in the game storage if game is not a local game.
 * @param game is current game to write on the file
 * @param gameStorage is GameStorage where it will be created or update the game's file
 * @return Returns the game received
 */
private fun createGameFile(game: Game, gameStorage: GameStorage): Game =
    game.also{
        if (game.name != null) {
            gameStorage.create(game.name, game)
        }
    }

private fun Boolean.toOnOrOff() = if (this) "On" else "Off"

private val new = Command("<FirstTurn> <Name>"){ args, game, gs ->
    require(args.isNotEmpty()){"Missing arguments"}
    val argSymbol = args.first()
    require(argSymbol.length == 1) {"Invalid argument $argSymbol"} // If argument is not a character

    val symbol = argSymbol.first()
    require(symbol == BLACK_SYMBOL || symbol == WHITE_SYMBOL){"Invalid symbol $argSymbol"} // If symbol is not a valid symbol

    val newGame = game?.new() ?: Game(firstTurn = symbol.player(), name = args.drop(1).firstOrNull())
    if (newGame.name != null)
        require(gs.read(newGame.name) == null){"Game ${newGame.name} already exists"}

    createGameFile(newGame, gs)
}

private val play = Command("<position>") { args, game, gs ->
    val arg = requireNotNull(args.firstOrNull()) {"Missing position"}
   // val pos = requireNotNull(arg.toCellOrNull()) {"Invalid position $arg."}
    require(arg.length == 2) { "Invalid argument $arg." }

    val newGame = checkNotNull(game){"Game not created"}.play(arg.toBoardPosition())
    updateGameFile(newGame, gs)
}

private val show = Command {_, game, _ ->
    game.also{ checkNotNull(game){"Game not created."}.show() }
}

private val targets = Command("<ON/OFF>"){args, game, _ ->
    checkNotNull(game){"Game not created"}
    check(args.isNotEmpty()){"Targets = ${game.pl.toggleTargets.toOnOrOff()}"}
    val targets = args.first().uppercase()
    require((targets == "ON") || (targets == "OFF")){"Invalid argument"}

    game.copy(pl = game.pl.copy(
        toggleTargets = targets == "ON"
        )
    )
}

private val pass = Command{_, game, gs ->
    val newGame = checkNotNull(game){"Game not created"}.pass()
    updateGameFile(newGame, gs)
}

private val join = Command("<Name>"){args, _, gs ->
    require(args.isNotEmpty()){"Missing game's name"}
    val name = args.first()
    val game = gs.read(name)
    checkNotNull(game){"Game $name is not running."}.copy(pl = Player(game.firstTurn.otherColor))
}

private val refresh = Command{_, game, gs ->
    checkNotNull(game){"Game is not running"}
    checkNotNull(game.name){"This command is not valid on a local game."}
    gs.read(game.name)?.copy(pl = game.pl)
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