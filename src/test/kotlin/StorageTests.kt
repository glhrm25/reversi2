import model.*
import storage.*
import kotlin.test.*

class StorageTests {
    private val gameSerializer = GameSerializer
    private val storage = TextFileStorage<String, Game>("StorageTests", gameSerializer)

    @Test
    fun `CRUD Operations`() {
        val game = Game(name = "test")
        // Create
        storage.create(game.name, game)
        assertEquals(game, storage.read(game.name))
        assertFailsWith<IllegalStateException> { storage.create(game.name, Game(name = "randomGameToTest")) }
        // Update
        val updatedData = game.play("4C".toBoardPosition())
        storage.update(game.name, updatedData)
        assertEquals(updatedData, storage.read(game.name))
        // Delete
        storage.delete(game.name)
        assertNull(storage.read(game.name))
        assertFailsWith<IllegalStateException> { storage.delete(game.name) }
        assertFailsWith<IllegalStateException> { storage.update(game.name, Game(name = "randomGameToTest")) }
    }
}