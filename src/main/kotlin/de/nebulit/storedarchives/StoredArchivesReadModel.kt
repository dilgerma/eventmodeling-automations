package de.nebulit.storedarchives

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.sql.Types
import java.util.UUID
import org.hibernate.annotations.JdbcTypeCode

class StoredArchivesReadModelQuery()

@Entity
class StoredArchivesReadModelEntity {
  @Id @JdbcTypeCode(Types.VARCHAR) @Column(name = "aggregateId") var aggregateId: UUID? = null

  @Column(name = "reportname") var reportname: String? = null
}

data class StoredArchivesReadModel(val data: List<StoredArchivesReadModelEntity>)
