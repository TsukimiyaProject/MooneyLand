package mc.tsukimiya.mooney.land.domain

interface LandRepository {
    companion object

    fun exists(id: LandId): Boolean

    fun find(id: LandId): Boolean

    fun findAll(): Map<LandId, Land>

    fun count(): Long

    fun store(land: Land)

    fun delete(id: LandId)
}
