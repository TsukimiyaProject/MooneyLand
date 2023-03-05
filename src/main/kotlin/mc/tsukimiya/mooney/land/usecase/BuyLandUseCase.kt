package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.core.domain.Money
import mc.tsukimiya.mooney.core.domain.WalletRepository
import mc.tsukimiya.mooney.core.exception.WalletNotFoundException
import mc.tsukimiya.mooney.land.domain.Area
import mc.tsukimiya.mooney.land.domain.Land
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.exception.LandOverlapException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BuyLandUseCase(private val landRepository: LandRepository, private val walletRepository: WalletRepository) {
    fun execute(x1: Int, x2: Int, z1: Int, z2: Int, world: String, owner: UUID, perArea: Int) {
        transaction {
            val area = Area.create(x1, x2, z1, z2, world)
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
