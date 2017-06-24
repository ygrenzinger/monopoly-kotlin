package monopoly.utils

import monopoly.Game
import monopoly.Location
import org.paumard.streams.StreamsUtils
import java.io.File
import java.io.PrintWriter
import java.util.Comparator
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream


private fun convert() {
    val resource = Game::class.java.getResource("/raw.csv")
    val rawFile = File(resource.toURI())
    val lines = rawFile.readLines()
    val group = StreamsUtils.group(lines.stream(), 5)
    val convertToLocation = Function<Stream<String>, Location> {
        val raw: MutableList<String> = it.collect(Collectors.toList())
        val rent = if (raw[3].startsWith("*")) null else raw[3].toInt()
        val price = raw[2].toInt()
        Location(raw[1].toInt(), raw[0], price, rent, raw[4])
    }
    var building = group.map(convertToLocation)
            .collect(Collectors.toList())

    building.add(Location(1, "Go"))
    building.add(Location(11, "Just Visiting/Jail"))
    building.add(Location(31, "Go to Jail"))
    building.add(Location(23, "Chance"))
    building.add(Location(5, "Income Tax"))
    building.add(Location(3, "Community Chest"))
    building.add(Location(8, "Chance"))
    building.add(Location(21, "Free Parking"))
    building.add(Location(37, "Chance"))
    building.add(Location(34, "Community Chest"))
    building.add(Location(13, "Electric Company"))
    building.add(Location(39, "Luxury Tax"))
    building.add(Location(18, "Community Chest"))
    building.add(Location(29, "Water Works"))
    building = building.stream()
            .sorted(Comparator.comparingInt { it.position })
            .collect(Collectors.toList())

    val file = File("locations.csv")
    PrintWriter(file).use({ out ->
        building.forEach { building ->
            val price = building.price ?: ""
            val rent = building.rent ?: ""
            out.println("${building.position},${building.title},$price,$rent,${building.group}")
        }
    })

    println("Done")
}