package model

import storage.Storage
import user_interface.show
import user_interface.showHeader
import user_interface.showTargets

typealias GameStorage = Storage<Name, Game>

open class Clash (val gs: GameStorage) {
    private fun notStarted(): Nothing = error("Clash not started")
    open fun play(pos: Position): Clash {
        notStarted()
    }
    open fun pass(): Clash {
        notStarted()
    }
    open fun targets(t: Boolean): Clash {
        notStarted()
    }
    open fun showPlayersTargetsConfig(){
        notStarted()
    }
    open fun refresh(): Clash {
        notStarted()
    }
    open fun show(): Clash {
        notStarted()
    }

    fun new(name: Name?, owner: Color): Clash {
        val newGame = Game(owner = owner)
        val side = Player(owner)
        return name?.let { ClashRun(gs, it, side, newGame.also{gs.create(name, it)}) } ?: ClashRunLocal(gs, newGame, side)
    }

    fun join(name: Name): Clash {
        val g = gs.read(name)
        checkNotNull(g){"Game $name does not exist"}
        return ClashRun(gs, name, Player(g.owner.otherColor), g)
    }
}

class ClashRun(
    gs: GameStorage,
    val name: Name,
    val side: Player,
    val game: Game,
): Clash(gs) {
    override fun play(pos: Position): Clash =
        copy(game = game.play(pos)).also {
            check(side.color == (game.state as Run).turn) { "Not your turn" }
            gs.update(name,it.game)
        }

    override fun pass(): Clash =
        this.copy(game = game.pass()).also {
            gs.update(name, it.game)
        }

    override fun refresh() =
        copy( game = gs.read(name)?.also { check(it != game) { "No changes" } }
            ?: error("Game not found")
        )

    override fun targets(t: Boolean) =
        this.copy(side = side.copy(toggleTargets = t))

    override fun showPlayersTargetsConfig() = showTargets(side)

    override fun show(): Clash =
        this.also {
            this.showHeader()
            game.show(side.toggleTargets)
        }

    private fun copy(
        gs: GameStorage = this.gs,
        name: Name = this.name,
        side: Player = this.side,
        game: Game = this.game)
    = ClashRun(gs, name, side, game)
}

class ClashRunLocal(
    gs: GameStorage,
    val game: Game,
    private val side: Player,
): Clash(gs) {
    override fun play(pos: Position): Clash =
        copy(game = game.play(pos))

    override fun pass(): Clash =
        this.copy(game = game.pass())

    override fun refresh(): Clash =
        error("Command unavailable on a local game.")

    override fun targets(t: Boolean) =
        this.copy(side = side.copy(toggleTargets = t))

    override fun showPlayersTargetsConfig() = showTargets(side)

    override fun show(): Clash =
        this.also {
            this.showHeader()
            game.show(side.toggleTargets)
        }

    private fun copy(gs: GameStorage = this.gs,
             game: Game = this.game,
             side: Player = this.side)
            = ClashRunLocal(gs, game, side)
}

fun Clash.deleteIfOwner() {
    if (this is ClashRun && side.color == this.game.owner) gs.delete(name)
}
/*
fun ClashRun.newAvailable() =
    side.color == when(val state = game.state) {
        is Run -> state.turn
        else -> game.owner
    }
*/
/*
/**
 * Updates the game file stored in the game storage if game is not a local game.
 * @param game is current game to write on the file
 * @param gs is GameStorage where it will be created or update the game's file
 * @return Returns the game received
 */
private fun updateGameFile(game: Game, gs: GameStorage): Game =
    game.also{
        if (game is MpGame) {
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
    game.also{ gameStorage.create(game.name, game) }
    }
 */