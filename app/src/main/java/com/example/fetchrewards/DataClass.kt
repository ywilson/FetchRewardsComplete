package com.example.fetchrewards

data class DataClass(
    var id: Int,
    var listId: Int,
    var name: String
) : Items() {
    override fun getType(): Int {
        return item
    }
}

class GroupClass (
    var listId: Int
) : Items() {
    override fun getType(): Int {
        return group
    }
}

abstract class Items {
    val group: Int = 0
    val item: Int = 1

    abstract fun getType() : Int

}
