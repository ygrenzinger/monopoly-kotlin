package monopoly

import org.assertj.core.api.Assertions.*
import org.junit.Test

class LocationTests {
    val board = BuildBoardFromFile()

    @Test
    fun `should gain 200$ when landing on Go location`() {
        val player = Player("player", 40)
        board.move(player, 1)
        assertThat(player.position()).isEqualTo(1)
        assertThat(player.money()).isEqualTo(200)
    }

    @Test
    fun `should gain 200$ when passing over Go location`() {
        val player = Player("player", 40)
        board.move(player, 2)
        assertThat(player.position()).isEqualTo(2)
        assertThat(player.money()).isEqualTo(200)
    }

    @Test
    fun `should go in Jail when landing on Go To Jail location`() {
        val player = Player("player", 30, 10)
        board.move(player, 1)
        assertThat(player.position()).isEqualTo(11)
        assertThat(player.money()).isEqualTo(10)
    }

    @Test
    fun `should pay 75% tax when landing on Income Tax`() {
        val player = Player("player", 4, 100)
        board.move(player, 1)
        assertThat(player.money()).isEqualTo(25)
    }

    @Test
    fun `should not pay more than 200$ when landing on Income Tax`() {
        val player = Player("player", 4, 1000)
        board.move(player, 1)
        assertThat(player.money()).isEqualTo(800)
    }

    @Test
    fun `should pay 75$ when landing on Luxury Tax`() {
        val player = Player("player", 4, 100)
        board.move(player, 1)
        assertThat(player.money()).isEqualTo(25)
    }

}