package net.lsafer.edgeseek.app.components.common

import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.sundry.storage.edit
import org.cufy.json.deserializeOrNull
import org.cufy.json.serializeToJsonElement

fun Local.editEdgeData(pos: EdgePos, update: (EdgeData) -> EdgeData) {
    dataStore.edit {
        val oldValue = it[pos.key]?.deserializeOrNull<EdgeData>()
        val newValue = update(oldValue ?: EdgeData(pos))
        it[pos.key] = newValue.serializeToJsonElement()
    }
}

fun Local.editEachEdgeData(update: (EdgeData) -> EdgeData) {
    dataStore.edit {
        EdgePos.entries.forEach { pos ->
            val oldValue = it[pos.key]?.deserializeOrNull<EdgeData>()
            val newValue = update(oldValue ?: EdgeData(pos))
            it[pos.key] = newValue.serializeToJsonElement()
        }
    }
}

fun Local.clearAndSetEdgeDataList(edges: List<EdgeData>) {
    dataStore.edit {
        EdgePos.entries.forEach { pos -> it -= pos.key }
        edges.forEach { data -> it[data.pos.key] = data.serializeToJsonElement() }
    }
}
