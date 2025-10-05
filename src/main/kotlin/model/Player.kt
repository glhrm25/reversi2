package model
import model.Player.*
import user_interface.*

enum class Player {
    BLACK, WHITE
}

val Player.otherPlayer: Player get() = if (this == BLACK) WHITE else BLACK

fun Char.player(): Player = if (this == BLACK_SYMBOL) BLACK else WHITE