package mc.tsukimiya.mooney.land.domain.exception

class LandNotFoundException(val id: Int) : RuntimeException("ID : $id") {
}
