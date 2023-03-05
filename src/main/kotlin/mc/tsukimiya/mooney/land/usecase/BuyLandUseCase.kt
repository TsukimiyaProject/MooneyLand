package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.core.domain.Money
import mc.tsukimiya.mooney.core.domain.WalletRepository
import mc.tsukimiya.mooney.core.exception.WalletNotFoundException
import mc.tsukimiya.mooney.land.domain.Coordinate
import mc.tsukimiya.mooney.land.domain.Land
import mc.tsukimiya.mooney.land.domain.LandRepository
import mc.tsukimiya.mooney.land.domain.exception.LandOverlapException
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class BuyLandUseCase(private val landRepository: LandRepository, private val walletRepository: WalletRepository) {
    fun execute(minX: Int, minZ: Int, maxX: Int, maxZ: Int, world: String, owner: UUID, perArea: Int) {
        transaction {
            val coordinate = Coordinate(minX, minZ, maxX, maxZ, world)
            if (landRepository.existsOverlapLand(coordinate)) {
                throw LandOverlapException()
            }

            val wallet = walletRepository.find(owner) ?: throw WalletNotFoundException(owner)
            val land = Land(null, coordinate, owner, listOf())
            wallet.decreaseMoney(Money(land.coordinate.calcArea() * perArea))
            walletRepository.store(wallet)
            landRepository.store(land)
        }
    }
}
