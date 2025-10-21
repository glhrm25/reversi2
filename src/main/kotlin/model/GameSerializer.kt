package model

import storage.Serializer
object GameSerializer: Serializer<Game>{
    override fun serialize(data: Game): String =
        with(data){
            "name:${data.name}" +
            "state:${StateSerializer.serialize(state)}" +
            "firstTurn:$firstTurn" +
            "board:$board"
        }


    override fun deserialize(text: String): Game {
        val (name, state, firstTurn, board) = text.split(":")
        println("name:$name, state:$state, firstTurn:$firstTurn, board:$board")
        return Game(name = "A")
    }
}

/**
 *  GameState Serializer that transforms a gamestate object into a string.
 */
object StateSerializer: Serializer<GameState> {
    override fun serialize(data: GameState): String =
        when(data) {
        is Run -> "Run:${data.turn}"
        is Win -> "Win:${data.winner}"
        is Draw -> "Draw:"
    }

    override fun deserialize(text: String): GameState {
        val (type, player) = text.split(":")
        return when(type){
            "Run" -> Run(Player.valueOf(player))
            "Win" -> Win(Player.valueOf(player))
            "Draw"-> Draw
            else -> error("Invalid game state $type")
        }
    }
}
