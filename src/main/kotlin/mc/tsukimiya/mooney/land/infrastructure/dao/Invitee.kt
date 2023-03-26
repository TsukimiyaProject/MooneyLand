package mc.tsukimiya.mooney.land.infrastructure.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Invitee(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Invitee>(Invitees)

    var landId by Land referencedOn Invitees.landId
    var invitee by Invitees.invitee
}
