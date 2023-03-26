package mc.tsukimiya.mooney.land.infrastructure.dao

import org.jetbrains.exposed.dao.id.IntIdTable

object Lands : IntIdTable() {
    val owner = uuid("owner")
    val minX = integer("min_x")
    val minZ = integer("min_z")
    val maxX = integer("max_x")
    val maxZ = integer("max_z")
    val world = varchar("world", 20)
}
