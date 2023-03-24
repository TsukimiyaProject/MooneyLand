package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.land.domain.Land
import mc.tsukimiya.mooney.land.domain.LandId
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.exception.LandNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction

class FindLandUseCase(private val repository: LandRepository) {
    fun execute(x: Int, z: Int, world: String): Land {
        return transaction {
            return@transaction repository.findByCoordinate(x, z, world) ?: throw LandNotFoundException(0)
        }
    }

    fun execute(landId: Int): Land {
        return transaction {
            return@transaction repository.find(LandId(landId)) ?: throw LandNotFoundException(landId)
        }
    }
}
