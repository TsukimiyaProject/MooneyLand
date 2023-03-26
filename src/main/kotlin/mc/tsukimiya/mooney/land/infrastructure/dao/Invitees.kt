package mc.tsukimiya.mooney.land.infrastructure.dao

import org.jetbrains.exposed.dao.id.IntIdTable

object Invitees : IntIdTable() {
    val landId = reference("land_id", Lands)
    val invitee = uuid("invitee")
}
