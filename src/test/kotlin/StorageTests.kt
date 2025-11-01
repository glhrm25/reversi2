import model.*
import storage.*
import kotlin.test.*

class StorageTests {
    /*
    private val gameSerializer = GameSerializer
    private val storage = TextFileStorage<Name, Game>("StorageTests", gameSerializer)

    private fun Game.structurallyEquals(other: Game?) =
        owner == other?.owner && board == other.board && state == other.state

    private fun GameState.structurallyEquals(other: GameState?) =
        when(this) {
            Run() ->
        }

    @Test
    fun `CRUD Operations`() {
        val clash = ClashRun(storage, Name("Test"), Player(Color.BLACK), Game())
        val game = clash.game
        // Create
        storage.create(clash.name, game)
        assertTrue(game.structurallyEquals(storage.read(clash.name)))
        assertFailsWith<IllegalStateException> { storage.create(clash.name, Game()) }
        // Update
        val updatedData = game.play("4C".toBoardPosition())
        storage.update(clash.name, updatedData)
        assertEquals(updatedData, storage.read(clash.name))
        // Delete
        storage.delete(clash.name)
        assertNull(storage.read(clash.name))
        assertFailsWith<IllegalStateException> { storage.delete(clash.name) }
        assertFailsWith<IllegalStateException> { storage.update(clash.name, Game()) }
    }
     */
}