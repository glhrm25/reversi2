import model.*
import storage.TextFileStorage
import user_interface.*

/* TO-DO LIST:
    -> Game.kt:
       -- Review Game class primary constructor
       -- Adjust board and position logic. (should position be converted to an index?)
       -- Game State should turn to "Draw" when there's no possible moves & players have the same amount of pieces or should it wait for "pass" command ?????

    -> Position.kt:
        -- What to do about the private constructor ???

    -> Rules.kt:
        -- Upgrade functions validMoves() and turnMoves() efficiency.
        -- Fix bug on turnMoves and validMoves.

    -> Overall:
        -- Review requires and checks
        -- Finish details off Multiplayer:
            What happens when a third instance tries to join ????
 */

fun main() {

    var game: Game? = null
    val gameStorage = TextFileStorage<String, Game>("games", GameSerializer)

    val cmds: Map<String, Command> = getCommands()
    while(true) {
        val (name, args) = readCommand()

        val command = cmds[name]
        if (command == null) println("Invalid command $name")

        else try {
            game = command.execute(args, game, gameStorage)
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