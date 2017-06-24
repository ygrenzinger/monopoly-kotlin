package monopoly

import java.security.SecureRandom
import java.util.*
import java.util.Collections.shuffle

class Game(private var players: List<Player>, val board: Board, val rollable: Rollable) {
    var turnNumber = 1;

    init {
        if (players.size < 2) throw RuntimeException("Not enough players")
        if (players.size > 8) throw RuntimeException("Too much players")
    }

    class RandomDice : Rollable {
        val random = SecureRandom()
        
        override fun roll(): Int {
            return random.nextInt(6)
        }
    }

    fun play() : Optional<Player> {
        while (turnNumber < 20 && players.size > 1) {
            players.forEach {
                board.move(it, rollable.roll())
                players = players.filter { !it.hasLost() }
                if (players.size == 1) {
                    return Optional.of(players.get(0))
                }
                
            }
            turnNumber++;
        }
        return Optional.empty()
    }

    fun players() = players.map { it.copy() }

    class Builder {
        val board = BuildBoardFromFile()
        var rollable: Rollable = RandomDice()
        var players = ArrayList<Player>();
        fun withPlayer(player: Player) : Builder {
            players.add(player)
            return this
        }

        fun  withDice(fixedRollable: Rollable) : Builder {
            rollable = fixedRollable
            return this
        }

        fun build() : Game {
            shuffle(players)
            return Game(players, board, rollable)
        }
    }

}