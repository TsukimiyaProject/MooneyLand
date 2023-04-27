package mc.tsukimiya.mooney.land.infrastructure.repository

import mc.tsukimiya.mooney.land.domain.*
import mc.tsukimiya.mooney.land.infrastructure.dao.Lands
import org.jetbrains.exposed.sql.and
import java.util.*
import mc.tsukimiya.mooney.land.infrastructure.dao.Land as LandDao

class LandRepositoryImpl : LandRepository {
    override fun exists(id: LandId): Boolean {
        return LandDao.findById(id.value) != null
    }

    override fun existsByCoordinate(coordinate: Coordinate): Boolean {
        return LandDao.find {
            (Lands.world eq coordinate.world)
                .and(Lands.minX greaterEq coordinate.x)
                .and(Lands.maxX lessEq coordinate.x)
                .and(Lands.minZ greaterEq coordinate.z)
                .and(Lands.maxZ lessEq coordinate.z)
        }.empty()
    }

    override fun existsOverlapLand(area: Area): Boolean {
        return LandDao.find {
            (Lands.world eq area.getWorld())
                .and(Lands.maxX lessEq area.minCoord.x)
                .and(Lands.minX greaterEq area.maxCoord.x)
                .and(Lands.maxZ lessEq area.minCoord.z)
                .and(Lands.minZ greaterEq area.maxCoord.z)
        }.empty()
    }

    override fun find(id: LandId): Land? {
        val land = LandDao.findById(id.value) ?: return null
        val invitee = mutableListOf<UUID>()
        land.invitees.forEach {
            invitee.add(it.invitee)
        }
        return Land(
            id,
            Area(Coordinate(land.minX, land.minZ, land.world), Coordinate(land.maxX, land.maxZ, land.world)),
            land.owner,
            invitee
        )
    }
}
