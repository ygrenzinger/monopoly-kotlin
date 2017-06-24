package monopoly

class Board(val locations: Map<Int, Location>) {
    private val MAX_LOCATION = 40

    fun move(player: Player, moves: Int) {
        val initialPosition = player.position()
        positionsPassedOver(initialPosition, moves)
                .map { locationAt(it) }
                .forEach { it.withPlayerPassingOver(player) }
        val finalLocation = locationAt(nextPosition(initialPosition, moves))
        player.landingAt(finalLocation)
        finalLocation.withPlayerLandingOn(player)
    }

    fun locationAt(position: Int) =  locations[position]?:throw RuntimeException("The location at ${position} doesn't exist")

    private fun positionsPassedOver(initial: Int, moves: Int): List<Int> {
        return IntRange(1, moves-1).fold(listOf<Int>(), { l, m -> l + nextPosition(initial, m)})
    }

    private fun nextPosition(initial: Int, moves: Int) : Int {
        var position = initial + moves
        if (position > MAX_LOCATION) {
            position -= MAX_LOCATION
        }
        return position
    }

}