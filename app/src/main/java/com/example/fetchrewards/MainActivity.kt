package com.example.fetchrewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val repository = Repository()
    private lateinit var recyclerView: RecyclerView
    private var dataList: MutableList<DataClass> = mutableListOf()
    private lateinit var prunedList: Map<Int, List<DataClass>>
    private var consolidatedList: ArrayList<Items> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.list_recycler_view)

        runBlocking { dataList = repository.loadData() }
        prunedList = pruneNullAndEmpty(dataList)
        assignToClass(prunedList)

        val adapter = ListAdapter(consolidatedList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
    }

    private fun pruneNullAndEmpty(data: MutableList<DataClass>) : Map<Int, List<DataClass>> {
        val pruned = ArrayList<DataClass>()
        for (item in data) {
            if (!item.name.isNullOrEmpty() && item.name != "null") { // null values come back as string
                pruned.add(item)
            }
        }
        return pruned.sortedWith(compareBy(DataClass::listId, DataClass::name)).groupBy { it.listId }
    }

    private fun assignToClass(classMap: Map<Int, List<DataClass>>) {
        for (ids in classMap.keys) {
            val groupItem = GroupClass(listId = ids)
            consolidatedList.add(groupItem)
            for (values in classMap[ids] ?: error("")) {
                val data = DataClass(values.id, values.listId, values.name)
                consolidatedList.add(data)
            }
        }
    }
}
