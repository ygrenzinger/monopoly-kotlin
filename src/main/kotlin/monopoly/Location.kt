package monopoly

import java.util.*

class Location(val position: Int, val title: String, val price: Int?, val rent: Int?, val group: String?) {

    constructor(position: Int, title: String) : this(position, title, null, null, null)

    private var landingOnRule : Optional<(player:Player) -> Unit> = Optional.empty()

    private var passingOnRule : Optional<(player:Player) -> Unit> = Optional.empty()

    fun addingLandingOnRule(rule: (player:Player) -> Unit) {
        landingOnRule = Optional.of(rule)
    }

    fun withPlayerLandingOn(player: Player) {
        landingOnRule.ifPresent { it(player) }
    }

    fun addingPassingRule(rule: (player:Player) -> Unit) {
        passingOnRule = Optional.of(rule)
    }

    fun withPlayerPassingOver(player: Player) {
        passingOnRule.ifPresent { it(player) }
    }

}