import storage.*
import model.*
import kotlin.math.PI
import kotlin.test.*

class TextFileStorageTests {
    private val serializer = object : Serializer<Double> {
        override fun serialize(data: Double): String = data.toString()
        override fun deserialize(txt: String): Double = txt.toDouble()
    }
    private val storage = TextFileStorage<String, Double>("BaseDir", serializer)

    @Test
    fun `CRUD operations`() {
        val key = "PI"
        // Create
        storage.create(key, PI)
        assertEquals(PI, storage.read(key))
        assertFailsWith<IllegalStateException> { storage.create(key, 0.0) }
        // Update
        val updatedData = PI-3.0
        storage.update(key, updatedData)
        assertEquals(updatedData, storage.read(key))
        // Delete
        storage.delete(key)
        assertNull(storage.read(key))
        assertFailsWith<IllegalStateException> { storage.delete(key) }
        assertFailsWith<IllegalStateException> { storage.update(key, 0.0) }
    }

    @Test
    fun `Create Games`() {
        val gameSerializer = GameSerializer
        val gameStorage = TextFileStorage<String, Game>("BaseDir", gameSerializer)

        val game = Game(name = "local")
        // Create
        gameStorage.create(game.name, game)
        assertEquals(game, gameStorage.read(game.name))
        assertFailsWith<IllegalStateException> { storage.create(game.name, 0.0) }
        /*
        // Update
        val updatedData = PI-3.0
        storage.update(key, updatedData)
        assertEquals(updatedData, storage.read(key))
         */
        // Delete
        gameStorage.delete(game.name)
        assertNull(gameStorage.read(game.name))
        assertFailsWith<IllegalStateException> { gameStorage.delete(game.name) }
        assertFailsWith<IllegalStateException> { gameStorage.update(game.name, Game(name = "randomGameToTest")) }
    }
}