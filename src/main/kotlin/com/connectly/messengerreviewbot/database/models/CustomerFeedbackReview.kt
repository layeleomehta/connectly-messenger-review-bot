package com.connectly.messengerreviewbot.database.models

import jakarta.persistence.*
import org.hibernate.Hibernate

@Table
@Entity
data class CustomerFeedbackReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    var id: Long?,
    var externalId: String,

    @ManyToOne(optional = false)
    @JoinColumn(name = "business_page_id")
    var businessPage: BusinessPage,

    var reviewText: String?,
    var starRating: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CustomerFeedbackReview

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
