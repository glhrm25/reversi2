import model.*
import storage.TextFileStorage
import user_interface.*

/* TO-DO LIST:

    -> Output.kt:
        -- game.show(): Change first $turn.symbol for the right player symbol when multiplayer
                        Review the turn println

    -> Game.kt:
       -- Review Game class primary constructor
       -- Adjust board and position logic. (should position be converted to an index?)
       -- Game State should turn to "Draw" when there's no possible moves & players have the same amount of pieces or should it wait for "pass" command ?????
       -- Fix bug on turnMoves and validMoves.
       -- Review gameState Run constructor. Maybe redo player's class with those constructors???

    -> Position.kt:
        -- What to do about the private constructor ???

    -> Rules.kt:
        -- Upgrade functions validMoves() and turnMoves() efficiency.

    -> Overall:
        -- Finish the implementantion of the storage package
        -- Finish the implementation of all the possible commands (Join and Refresh)
        -- Review requires and checks
 */

fun main() {

    var game: Game? = null
    val gameStorage = TextFileStorage<String, Game>("games", GameSerializer)
    val cmds: Map<String, Command> = getCommands(gameStorage)

    while(true) {
        val (name, args) = readCommand()

        val command = cmds[name]
        if (command == null) println("Invalid command $name")

        else try {
            game = command.execute(args, game)
            if (command.isTerminate) {
                println("Game ended.")
                return
            }
        }
        catch (ex: IllegalArgumentException) {
            println(ex.message)
            println("Use $name ${command.syntaxArgs}")
        } catch (ex: IllegalStateException) {
            println(ex.message)
        }

    }
}