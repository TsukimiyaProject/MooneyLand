package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.core.domain.Money
import mc.tsukimiya.mooney.core.domain.WalletRepository
import mc.tsukimiya.mooney.core.exception.WalletNotFoundException
import mc.tsukimiya.mooney.land.domain.LandId
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.exception.InvalidOwnerException
import mc.tsukimiya.mooney.land.domain.exception.LandNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class SellLandUseCase(
    private val landRepository: LandRepository,
    private val walletRepository: WalletRepository) {
    fun execute(owner: UUID, landId: Int, perArea: Int) {
        transaction {
            val land = landRepository.find(LandId(landId)) ?: throw LandNotFoundException(landId)
            if (land.owner != owner) {
                throw InvalidOwnerException(owner)
            }

            val wallet = walletRepository.find(owner) ?: throw WalletNotFoundException(owner)
            wallet.increaseMoney(Money(land.area.calcBlocks() * perArea))

            landRepository.delete(land.landId!!)
            walletRepository.store(wallet)
        }
    }
}
