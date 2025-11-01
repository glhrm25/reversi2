import model.*
import kotlin.test.*

class GameSerializerTests {
    // Helper function to play a list of moves
    /*
    private fun playMoves(vararg moves:Int): Game =
        moves.fold(Game(name = "test")) { g, pos -> g.play(Position(pos)) }

    @Test fun serializeRunGame() {
        val game = playMoves(toBoardIndex(4, 'C'), toBoardIndex(3, 'C'))
        val s = GameSerializer.serialize(game)
        println(s)
        assertEquals(game,GameSerializer.deserialize(s))
    }
    @Test fun serializeWinGame() {
        val game = Game(name = "test", state = Win(Color.BLACK))
        val s = GameSerializer.serialize(game)
        assertEquals(game,GameSerializer.deserialize(s))
    }
    @Test fun serializeDrawGame() {
        val game = Game(name = "test", state = Draw)
        val s = GameSerializer.serialize(game)
        assertEquals(game,GameSerializer.deserialize(s))
    }*/
}