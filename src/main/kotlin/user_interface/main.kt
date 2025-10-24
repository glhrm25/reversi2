package user_interface

import model.*
import storage.TextFileStorage

/* TO-DO LIST:
    -> Game.kt:
       -- Review Game class primary constructor
       -- Adjust board and position logic. (should position be converted to an index?)
       -- Create subclass "runpassed", representing the hasPreviousPassed boolean

    -> Position.kt:
        -- What to do about the private constructor ???

    -> Rules.kt:
        -- Upgrade functions validMoves() and turnMoves() efficiency.

    -> Overall:
        -- Review requires and checks
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
            if (command.toShow) game?.show()
        }
        catch (ex: IllegalArgumentException) {
            println(ex.message)
            println("Use $name ${command.syntaxArgs}")
        } catch (ex: IllegalStateException) {
            println(ex.message)
        }
    }
}