package user_interface

import model.*
import storage.TextFileStorage

/* TO-DO LIST:
    -> Game.kt:
       -- Review Game class primary constructor
       -- Adjust board and position logic. (should position be converted to an index?)
       -- Create subclass "runpassed", representing the hasPreviousPassed boolean -> Dúvida para a próx. aula.
       -- Rethink function game.new()

    -> Position.kt:
        -- What to do about the private constructor ???

    -> Rules.kt:
        -- Upgrade functions validMoves() and turnMoves() efficiency.

    -> Overall:
        -- Review requires and checks
        -- Delete files when exiting the app.
        -- What to do about ClashRunLocal game storage?
 */

private const val gamesDirectory = "games"

fun main() {

    val gameStorage = TextFileStorage<Name, Game>(gamesDirectory, GameSerializer)
    var clash = Clash(gameStorage)

    val cmds: Map<String, Command> = getCommands()
    while(true) {
        val (name, args) = readCommand()

        val command = cmds[name]
        if (command == null) println("Invalid command $name")

        else try {
            clash = command.execute(clash, args)
            if (command.isTerminate) {
                println("Game ended.")
                return
            }
            if (command.toShow) clash.show()
        }
        catch (ex: IllegalArgumentException) {
            println(ex.message)
            println("Use $name ${command.syntaxArgs}")
        } catch (ex: IllegalStateException) {
            println(ex.message)
        }
    }
}