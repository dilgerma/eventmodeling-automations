package de.nebulit.publishedarchives

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

import org.hibernate.annotations.JdbcTypeCode
import java.sql.Types
import java.util.UUID;


class PublishedArchivesReadModelQuery()

@Entity
class PublishedArchivesReadModelEntity {
		@Id @JdbcTypeCode(Types.VARCHAR)  @Column(name="aggregateId") var aggregateId:UUID? = null;
	 @Column(name="reportname") var reportname:String? = null;
}

data class PublishedArchivesReadModel(val data: List<PublishedArchivesReadModelEntity>)
