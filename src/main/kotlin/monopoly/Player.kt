package monopoly

data class Player(val name: String, private var position: Int = 1, private var money: Int = 0) {

    private var hasLost = false

    fun money() = money

    fun gain(gain: Int) {
        money += gain
    }

    fun loose(lost: Int) {
        money -= lost
        hasLost = money < 0
    }

    fun position() = position

    fun landingAt(location: Location) {
        position = location.position
    }

    fun hasLost() = hasLost


}