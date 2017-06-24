package monopoly

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object LocationsSpec : Spek({

    //OUT OF ORDER : sadly the state of JUnit 5 support with old Junit 4 is a mess :(
    // Moreover Spek doesn't add real bonus on tests

    describe("a player") {
        val board = BuildBoardFromFile()

        on("") {
            val player = Player("player", 40)
            board.move(player, 1)
            assertThat(player.position()).isEqualTo(1)

            it("should gain 200$ when landing on Go location") {
                assertThat(player.money()).isEqualTo(200)
            }
        }

        on("passing Go location") {
            val player = Player("player", 40)
            board.move(player, 2)
            assertThat(player.position()).isEqualTo(2)


            it("should gain 200$") {
                assertThat(player.money()).isEqualTo(200)
            }
        }

        on("landing on Go to jail") {
            val player = Player("player", 30)
            board.move(player, 1)

            it("should move to jail and gain no money") {
                assertThat(player.money()).isEqualTo(0)
                assertThat(player.position()).isEqualTo(11)
            }
        }

        on("landing on Income tax") {

            it("should pay 75% of tax") {
                val player = Player("player", 4, 100)
                board.move(player, 1)
                assertThat(player.money()).isEqualTo(25)
            }

            it("should pay no more than 200$") {
                val player = Player("player", 4, 1000)
                board.move(player, 1)
                assertThat(player.money()).isEqualTo(800)
            }
        }

        on("landing on Luxury tax") {

            it("should pay 75$ of tax") {
                val player = Player("player", 4, 100)
                board.move(player, 1)
                assertThat(player.money()).isEqualTo(25)
            }
        }

    }
})