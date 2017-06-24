package monopoly

import java.io.File


fun BuildBoardFromFile(): Board {
    val locations = File(Game::class.java.getResource("/locations.csv").toURI())
            .readLines()
            .map {
                val v = it.split(",")
                val price = if (v[2].isBlank()) null else v[2].toInt()
                val rent = if (v[3].isBlank()) null else v[3].toInt()
                Location(v[0].toInt(), v[1], price, rent, v[4])
            }.associateBy({it.position}, {it})

    val go = locations[1]?:throw RuntimeException("Go not present")
    go.addingLandingOnRule { it.gain(200) }
    go.addingPassingRule { it.gain(200) }

    val goToJail = locations[31]?:throw RuntimeException("Go To Jail not present")
    val jail = locations[11]?:throw RuntimeException("Jail not present")
    goToJail.addingLandingOnRule { it.landingAt(jail) }

    val incomeTax = locations[5]?:throw RuntimeException("Income tax not present")
    incomeTax.addingLandingOnRule {
        var amountFromTax = Math.round(it.money().toDouble() * 75 / 100).toInt()
        amountFromTax = if (amountFromTax > 200) 200 else amountFromTax
        it.loose(amountFromTax)
    }
    
    val luxuryTax = locations[39]?:throw RuntimeException("Luxury tax not present")
    luxuryTax.addingLandingOnRule {
        it.loose(75)
        
    }

    return Board(locations)
}
//http://www.math.yorku.ca/~zabrocki/math2042/Monopoly/prices.html

fun main(args: Array<String>) {
    println("Hello")
}

