package mc.tsukimiya.mooney.land.domain

data class LandId(val value: Int) {
    init {
        require(value > 0)
    }
}
