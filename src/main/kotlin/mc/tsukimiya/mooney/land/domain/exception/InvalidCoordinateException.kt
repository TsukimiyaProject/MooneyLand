package mc.tsukimiya.mooney.land.domain.exception

import mc.tsukimiya.mooney.land.domain.Area

class InvalidCoordinateException(area: Area) :
    RuntimeException("[${area.minCoord.x}, ${area.minCoord.z}, ${area.minCoord.world}], [${area.maxCoord.x}, ${area.maxCoord.z}, ${area.maxCoord.world}]") {
}
