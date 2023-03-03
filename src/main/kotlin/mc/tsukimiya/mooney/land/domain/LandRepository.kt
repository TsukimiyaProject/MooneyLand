package mc.tsukimiya.mooney.land.domain

import java.util.UUID

interface LandRepository {
    companion object

    fun exists(id: LandId): Boolean

    fun find(id: LandId): Boolean

    fun findByOwner(owner: UUID): Map<UUID, Land>

    fun findAll(): Map<LandId, Land>

    fun count(): Long

    fun store(land: Land)

    fun create(coordinate: Coordinate, owner: UUID)

    fun delete(id: LandId)
}
