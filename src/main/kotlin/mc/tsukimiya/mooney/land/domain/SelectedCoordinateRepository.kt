package mc.tsukimiya.mooney.land.domain

import java.util.UUID

interface SelectedCoordinateRepository {
    fun find(player: UUID): Pair<SelectedCoordinate, SelectedCoordinate>?

    fun store(coord: SelectedCoordinate?, coordinate: SelectedCoordinate? = null)

    fun delete(player: UUID)
}
