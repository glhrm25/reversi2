fun main() {

    var game: Game? = null

    while(true) {
        print("> ")

        val input = readln().trim().uppercase().split(' ')
        when (val cmd = input[0]) {
            "NEW" -> game = game.new()
            "JOIN" -> game = game.join()
            "PLAY" -> game.play()
            "PASS" -> {}
            "REFRESH" -> game = game.refresh()
            "TARGETS" -> {}
            "SHOW" -> {}
            "EXIT" -> break
            else -> println("Invalid command")
        }

        game?.show()
    }
}