import model.*
import user_interface.*

/* TO-DO LIST:

    -> Output.kt: (LATER ON THE PROJECT)
        -- game.show(): Change first $turn.symbol for the right player symbol when multiplayer

        -- Rewrite the logic behind the targets command. (TARGET should not be part of player class)

    -> Game.kt:
       -- Game class: Possibly take board out of the primary constructor ???
       -- game.new(): Fix the extraction of argument "name". Must be "local" when not specified.

    -> Cell.kt:
        -- Rename this class to Position.
        -- Possibly make this a private constructor ???

    -> Rules.kt:
        -- Upgrade functions validMoves() and turnMoves() efficiency.

    -> Finish the implementation of all the possible commands

 */

fun main() {

    var game: Game? = null

    val cmds: Map<String, Command> = getCommands()

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