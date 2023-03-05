package mc.tsukimiya.mooney.land.domain.exception

import mc.tsukimiya.mooney.land.domain.Coordinate

class InvalidCoordinateException(coordinate: Coordinate) :
    RuntimeException("minX = ${coordinate.minX}, minZ = ${coordinate.minZ}, maxX = ${coordinate.maxX}, maxZ = ${coordinate.maxZ}") {
}
