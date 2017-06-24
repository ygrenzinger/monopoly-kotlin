package monopoly

import io.kotlintest.matchers.*
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.stream.Collectors

class GameTests {

    class FixedDice(val value: Int) : Rollable {
        override fun roll(): Int = value
    }

    val locations = IntRange(1, 40).map { Location(it, "") }.associateBy { it.position }
    val board = Board(locations)

    @Test
    fun `should move a player on board`() {
        val martin = Player("Martin")
        board.move(martin, 7)
        martin.position() shouldBe 8

        val fowler = Player("Fowler", 39)
        board.move(fowler, 6)
        fowler.position() shouldBe 5
    }

    @Test
    fun `should have enough players`() {
        shouldThrow<RuntimeException> {
            val players = listOf(Player("1"))
            Game(players, Board(emptyMap()), FixedDice(1))
        }
    }

    @Test
    fun `should not have too much players`() {
        shouldThrow<RuntimeException> {
            val players = IntRange(1, 9).map { Player(it.toString()) }
            Game(players, Board(emptyMap()), FixedDice(1))
        }
    }

    @Test
    fun `should randomize order of players at start`() {
        val playersOfGame = IntRange(1, 20).map {
            val game = Game.Builder()
                    .withPlayer(Player("Horse"))
                    .withPlayer(Player("Car"))
                    .build();
            game.players().map({ it.name }).joinToString()
        }
        playersOfGame should contain("Horse, Car")
        playersOfGame should contain("Car, Horse")
    }

    @Test
    fun `should limit game to 20 turns and players should have played 20 turn`() {
        val game = Game.Builder()
                .withPlayer(Player("Horse", money = 1000))
                .withPlayer(Player("Car", money = 1000))
                .withDice(FixedDice(1))
                .build();
        game.play()
        game.turnNumber shouldBe 20
        game.players().forEach { it.position() shouldBe 20 }

    }

    @Test
    fun `should not change orders of players during game`() {
        val game = Game.Builder()
                .withPlayer(Player("Horse", money = 1000))
                .withPlayer(Player("Car", money = 1000))
                .withDice(FixedDice(1))
                .build();
        val playersAtStart = game.players().stream().collect(Collectors.toList())
        game.play()
        game.players().get(0).name shouldEqual playersAtStart.get(0).name
        game.players().get(1).name shouldEqual playersAtStart.get(1).name

    }

    @Test
    fun `should remove a player when he loose`() {
        val game = Game.Builder()
                .withPlayer(Player("Rich class", 32, 1000))
                .withPlayer(Player("Medium class", 32, 100))
                .withPlayer(Player("Poor class", 32, 0))
                .withDice(FixedDice(1))
                .build();
        game.play()
        Assertions.assertThat(game.players())
                .extracting("name").containsExactlyInAnyOrder("Rich class", "Medium class")
    }

    @Test
    fun `should stop the game when there is one player remaining, the winner`() {
        val game = Game.Builder()
                .withPlayer(Player("Winner", 32, 100))
                .withPlayer(Player("Looser 1", 32, 0))
                .withPlayer(Player("Looser 2", 32, 0))
                .withDice(FixedDice(7))
                .build();
        game.play()
        Assertions.assertThat(game.players()).containsExactly(Player("Winner", 39, 25))
        Assertions.assertThat(game.turnNumber).isEqualTo(1)
    }


}