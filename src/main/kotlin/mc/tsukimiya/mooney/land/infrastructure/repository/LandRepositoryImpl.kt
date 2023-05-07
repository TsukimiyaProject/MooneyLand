package mc.tsukimiya.mooney.land.infrastructure.repository

import mc.tsukimiya.mooney.land.domain.*
import mc.tsukimiya.mooney.land.infrastructure.dao.Invitees
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
        val dao = LandDao.findById(id.value) ?: return null
        return dao2land(dao)
    }

    override fun findByCoordinate(coordinate: Coordinate): Land? {
        val dao = LandDao.find {
            (Lands.world eq coordinate.world)
                .and(Lands.minX greaterEq coordinate.x)
                .and(Lands.maxX lessEq coordinate.x)
                .and(Lands.minZ greaterEq coordinate.z)
                .and(Lands.maxZ lessEq coordinate.z)
        }.firstOrNull() ?: return null
        return dao2land(dao)
    }

    override fun findByOwner(owner: UUID): Map<LandId, Land> {
        val lands = mutableMapOf<LandId, Land>()
        LandDao.find {
            Lands.owner eq owner
        }.forEach {
            lands[LandId(it.id.value)] = dao2land(it)
        }
        return lands
    }

    override fun findAll(): Map<LandId, Land> {
        val lands = mutableMapOf<LandId, Land>()
        LandDao.all().forEach {
            lands[LandId(it.id.value)] = dao2land(it)
        }
        return lands
    }

    override fun count(): Long {
        return LandDao.count()
    }

    override fun store(land: Land) {
        val dao = land.landId?.let { LandDao.findById(it.value) }
        if (dao == null) {
            LandDao.new {
                this.owner = land.owner
                this.minX = land.area.minCoord.x
                this.minZ = land.area.minCoord.z
                this.maxX = land.area.maxCoord.x
                this.maxZ = land.area.maxCoord.z
                this.world = land.area.getWorld()

            }
        } else {
            dao.owner = land.owner
            TODO("招待された/kickされたプレイヤーの永続化処理")
        }
    }

    override fun delete(id: LandId) {
        TODO("Not yet implemented")
    }

    private fun dao2land(dao: LandDao): Land {
        val invitee = mutableListOf<UUID>()
        dao.invitees.forEach { invitee.add(it.invitee) }
        return Land(
            LandId(dao.id.value),
            Area.create(dao.minX, dao.maxX, dao.minZ, dao.maxZ, dao.world),
            dao.owner,
            invitee
        )
    }
}
