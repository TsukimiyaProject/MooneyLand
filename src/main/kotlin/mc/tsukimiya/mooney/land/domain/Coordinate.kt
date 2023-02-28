package mc.tsukimiya.mooney.land.domain

data class Coordinate(val minX: Int, val minZ: Int, val maxX: Int, val maxZ: Int) {
    init {
        require(minX <= maxX)
        require(minZ <= maxZ)
    }
}
