
import kotlin.test.Test
import model.*
import kotlin.test.*

class CommandsTests {
/*
    private fun fullBoard(): Map<Position, Color> {
        return (0 until BOARD_CELLS).map { i ->
            Position(i)
        }.associateWith { position -> if (position.index % 2 == 0) Color.BLACK else Color.WHITE }
    }

    @Test
    fun `PASS command test with full board`() {
        // FullBoard
        var game = Game(
            name = null,
            board = fullBoard().filter { it.key != Position(BOARD_CELLS - 1) },
            state = Run(Color.BLACK)
        )
        assertEquals(Run(turn = Color.BLACK), game.state)
        game = game.pass()
        assertEquals(Run(turn = Color.WHITE, hasPreviousPassed = true), game.state)
        game = game.play(Position(BOARD_CELLS - 1))
        assertEquals(Win(Color.WHITE), game.state)
        assertFailsWith<IllegalStateException> {
            game = game.pass()
        }
    }
    @Test
    fun `PASS command test with initial board`() {
        // initialBoard
        var game = Game(name = null)
        assertEquals(Run(turn = Color.BLACK), game.state)
        assertFailsWith<IllegalStateException> {
            game = game.pass()
        }
        assertEquals(Run(turn = Color.BLACK), game.state)
        game = game.play(Position(toBoardIndex(4, 'C')))
        assertEquals(Run(Color.WHITE), game.state)
        assertFailsWith<IllegalStateException> {
            game = game.pass()
        }
        assertEquals(Run(Color.WHITE), game.state)
    }*/
}
