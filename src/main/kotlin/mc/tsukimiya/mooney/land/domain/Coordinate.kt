package mc.tsukimiya.mooney.land.domain

data class Coordinate(val minX: Int, val minZ: Int, val maxX: Int, val maxZ: Int, val world: String) {
    init {
        require(minX <= maxX)
        require(minZ <= maxZ)
    }

    /**
     * 面積を計算
     *
     * @return 縦×横
     */
    fun calcArea(): Int {
        return (maxX - minX + 1) * (maxZ - minZ + 1)
    }
}
