package mc.tsukimiya.mooney.land.domain

import java.util.UUID

interface LandRepository {
    companion object

    fun exists(id: LandId): Boolean

    fun existsByCoordinate(x: Int, z: Int, world: String): Boolean

    fun find(id: LandId): Land?

    fun findByCoordinate(x: Int, z: Int, world: String): Land?

    fun findByOwner(owner: UUID): Map<UUID, Land>

    fun findAll(): Map<LandId, Land>

    fun count(): Long

    fun store(land: Land)

    fun delete(id: LandId)
}
