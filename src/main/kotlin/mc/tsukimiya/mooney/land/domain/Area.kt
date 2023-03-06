package mc.tsukimiya.mooney.land.domain

import mc.tsukimiya.mooney.land.domain.exception.InvalidCoordinateException
import kotlin.math.max
import kotlin.math.min

data class Area(val minCoord: Coordinate, val maxCoord: Coordinate) {
    companion object {
        fun create(x1: Int, x2: Int, z1: Int, z2: Int, world: String): Area {
            return Area(
                Coordinate(min(x1, x2), min(z1, z2), world),
                Coordinate(max(x1, x2), max(x1, x2), world)
            )
        }

        fun create(coord1: Coordinate, coord2: Coordinate): Area {
            return Area(
                Coordinate(min(coord1.x, coord2.x), min(coord1.z, coord2.z), coord1.world),
                Coordinate(max(coord1.x, coord2.x), max(coord1.z, coord2.z), coord2.world),
            )
        }
    }

    init {
        if (minCoord.x > maxCoord.x) {
            throw InvalidCoordinateException(this)
        }
        if (minCoord.z > maxCoord.z) {
            throw InvalidCoordinateException(this)
        }
        if (minCoord.world != maxCoord.world) {
            throw InvalidCoordinateException(this)
        }
    }

    /**
     * world名取得
     * minCoordとmaxCoordのworldは等しいことは約束されている為、
     * minCoord.worldでもmaxCoord.worldでも問題無いが気持ち悪いから
     *
     * @return
     */
    fun getWorld(): String {
        return minCoord.world
    }

    /**
     * 面積を計算
     *
     * @return 縦×横
     */
    fun calcBlocks(): Int {
        return (maxCoord.x - minCoord.x + 1) * (maxCoord.z - minCoord.z + 1)
    }
}
