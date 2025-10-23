package model
import model.Player.*
import user_interface.*

enum class Player {
    BLACK, WHITE,
}

data class Player2(val color: Player, val toggleTargets: Boolean = false)

val Player.otherPlayer: Player get() = if (this == BLACK) WHITE else BLACK

fun Char.player(): Player = if (this == BLACK_SYMBOL) BLACK else WHITE

fun String.toPlayer(): Player = Player.valueOf(this)

fun String.toPlayerOrNull(): Player? = Player.entries.firstOrNull{ it.name == this }