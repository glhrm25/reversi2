package model

import storage.Serializer
object GameSerializer: Serializer<Game>{
    override fun serialize(data: Game): String =
        buildString {
            appendLine(data.name)
            appendLine(data.firstTurn)
            appendLine(StateSerializer.serialize(data.state))
            appendLine(data.board.entries.joinToString(" "){ (k, v) -> "${k.index}:$v" })
        }


    override fun deserialize(text: String): Game {
        val (l1, l2, l3, l4) = text.split("\n")
        return Game(
            name = l1,
            firstTurn = l2.toColor(),
            state = StateSerializer.deserialize(l3),
            board = l4.split(' ').associate {
                val (pos, pl) = it.split(':')
                Position(pos.toInt()) to pl.toColor()
            },
        )
    }
}

/**
 *  GameState Serializer that transforms a gamestate object into a string.
 */
object StateSerializer: Serializer<GameState> {
    override fun serialize(data: GameState): String =
        when(data) {
            is Run -> buildString {
                append("Run:${data.turn}-${data.hasPreviousPassed}")
            }
        is Win -> "Win:${data.winner}"
        is Draw -> "Draw:"
    }

    override fun deserialize(text: String): GameState {
        val (type, content) = text.split(":")
        return when(type){
            "Run" -> {
                val (tr, hpp) = content.split("-")
                Run(Color.valueOf(tr), hpp.toBoolean())
            }
            "Win" -> Win(Color.valueOf(content))
            "Draw"-> Draw
            else -> error("Invalid game state $type")
        }
    }
}
