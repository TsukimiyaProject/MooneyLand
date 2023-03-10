package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.core.domain.Money
import mc.tsukimiya.mooney.core.domain.WalletRepository
import mc.tsukimiya.mooney.core.exception.WalletNotFoundException
import mc.tsukimiya.mooney.land.domain.Area
import mc.tsukimiya.mooney.land.domain.Land
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.SelectedCoordinateRepository
import mc.tsukimiya.mooney.land.domain.exception.LandOverlapException
import mc.tsukimiya.mooney.land.domain.exception.SelectedCoordNotFoundException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BuyLandUseCase(
    private val landRepository: LandRepository,
    private val coordRepository: SelectedCoordinateRepository,
    private val walletRepository: WalletRepository
) {
    fun execute(owner: UUID, perArea: Int) {
        transaction {
            val coords = coordRepository.find(owner)
            val coord1 = coords.first ?: throw SelectedCoordNotFoundException()
            val coord2 = coords.second ?: throw SelectedCoordNotFoundException()

            val area = Area.create(coord1, coord2)
            if (landRepository.existsOverlapLand(area)) {
                throw LandOverlapException()
            }

            val wallet = walletRepository.find(owner) ?: throw WalletNotFoundException(owner)
            val land = Land(null, area, owner, listOf())
            wallet.decreaseMoney(Money(land.area.calcBlocks() * perArea))
            walletRepository.store(wallet)
            landRepository.store(land)
        }
    }
}
