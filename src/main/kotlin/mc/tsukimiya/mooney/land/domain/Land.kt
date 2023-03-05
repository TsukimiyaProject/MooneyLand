package mc.tsukimiya.mooney.land.domain

import mc.tsukimiya.mooney.land.domain.exception.LandNoIdException
import java.util.*

class Land(landId: LandId?, val coordinate: Coordinate, owner: UUID, invitees: List<UUID>) {
    val landId = landId
        get() = field ?: throw LandNoIdException()
    var owner = owner; private set
    var invitees = invitees; private set

    fun changeOwner(newOwner: UUID) {
        owner = newOwner

        deleteInvitee(newOwner)
    }

    fun addInvitee(invitee: UUID) {
        invitees = invitees.toMutableList().apply {
            add(invitee)
        }
    }

    fun deleteInvitee(invitee: UUID) {
        invitees = invitees.toMutableList().apply {
            remove(invitee)
        }
    }
}
