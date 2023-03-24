package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.land.domain.LandId
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.exception.InvalidOwnerException
import mc.tsukimiya.mooney.land.domain.exception.LandNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class KickPlayerUseCase(private val landRepository: LandRepository) {
    fun execute(owner: UUID, target: UUID, landId: Int) {
        transaction {
            val land = landRepository.find(LandId(landId)) ?: throw LandNotFoundException(landId)
            if (land.owner != owner) {
                throw InvalidOwnerException(owner)
            }

            land.deleteInvitee(target)
            landRepository.store(land)
        }
    }
}
