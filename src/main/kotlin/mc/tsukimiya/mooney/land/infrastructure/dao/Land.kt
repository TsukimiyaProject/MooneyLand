package mc.tsukimiya.mooney.land.infrastructure.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Land(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Land>(Lands)

    var owner by Lands.owner
    var minX by Lands.minX
    var minZ by Lands.minZ
    var maxX by Lands.maxX
    var maxZ by Lands.maxZ
    var world by Lands.world
    val invitees by Invitee referrersOn Invitees.invitee
}
