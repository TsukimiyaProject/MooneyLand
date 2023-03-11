package mc.tsukimiya.mooney.land.domain.exception

import java.util.*

class InvalidOwnerException(owner: UUID) : RuntimeException("UUID : $owner") {
}
