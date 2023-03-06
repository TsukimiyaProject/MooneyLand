package mc.tsukimiya.mooney.land.usecase

import mc.tsukimiya.mooney.land.domain.SelectedCoordinate
import mc.tsukimiya.mooney.land.domain.SelectedCoordinateRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class StoreSelectedCoordinateUseCase(private val repository: SelectedCoordinateRepository) {
    /**
     * @param player
     * @param x
     * @param z
     * @param world
     * @param other pos2の場合true
     */
    fun execute(player: UUID, x: Int, z: Int, world: String, other: Boolean = false) {
        transaction {
            val coordinate = SelectedCoordinate(player, x, z, world)
            if (other) {
                repository.store(null, coordinate)
            } else {
                repository.store(coordinate)
            }
        }
    }
}
