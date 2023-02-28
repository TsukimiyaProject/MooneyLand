package mc.tsukimiya.mooney.land.domain

import java.util.*

class Land(val landId: LandId, val coordinate: Coordinate, owner: Owner, invitees: Invitees) {
    var owner: Owner = owner; private set
    var invitees: Invitees = invitees; private set

    fun changeOwner(newOwner: UUID) {
        owner = Owner(newOwner)
    }

    fun addInvitee(invitee: UUID) {
        val newList = mutableListOf(invitee)
        newList.addAll(invitees.list)
        invitees = Invitees(newList)
    }
}
