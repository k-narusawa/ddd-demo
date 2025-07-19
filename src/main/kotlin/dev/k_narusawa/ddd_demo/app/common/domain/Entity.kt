package dev.k_narusawa.ddd_demo.app.common.domain

import java.io.Serializable

abstract class Entity<ID : Serializable>(val id: ID) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity<*>) return false

        // IDが同じであれば、同じエンティティと見なす
        return this.id == other.id
    }

    override fun hashCode(): Int {
        // equals()の実装と一貫性を保つため、idのハッシュコードを返す
        return id.hashCode()
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}(id=$id)"
    }
}
