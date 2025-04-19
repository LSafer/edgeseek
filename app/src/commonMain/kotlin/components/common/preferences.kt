package net.lsafer.edgeseek.app.components.common

import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgePosData
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import net.lsafer.edgeseek.app.data.settings.EdgeSideData
import net.lsafer.sundry.storage.edit
import org.cufy.json.deserializeOrNull
import org.cufy.json.serializeToJsonElement

fun Local.editEdgeData(pos: EdgePos, update: (EdgePosData) -> EdgePosData) {
    dataStore.edit {
        val oldValue = it[pos.key]?.deserializeOrNull<EdgePosData>()
        val newValue = update(oldValue ?: EdgePosData(pos))
        it[pos.key] = newValue.serializeToJsonElement()
    }
}

fun Local.editEdgeSideData(side: EdgeSide, update: (EdgeSideData) -> EdgeSideData) {
    dataStore.edit {
        val oldValue = it[side.key]?.deserializeOrNull<EdgeSideData>()
        val newValue = update(oldValue ?: EdgeSideData(side))
        it[side.key] = newValue.serializeToJsonElement()
    }
}

fun Local.editEachEdgeData(update: (EdgePosData) -> EdgePosData) {
    dataStore.edit {
        EdgePos.entries.forEach { pos ->
            val oldValue = it[pos.key]?.deserializeOrNull<EdgePosData>()
            val newValue = update(oldValue ?: EdgePosData(pos))
            it[pos.key] = newValue.serializeToJsonElement()
        }
    }
}

fun Local.clearAndSetEdgeDataList(edges: List<EdgePosData>, sides: List<EdgeSideData>) {
    dataStore.edit {
        EdgePos.entries.forEach { pos -> it -= pos.key }
        edges.forEach { data -> it[data.pos.key] = data.serializeToJsonElement() }

        EdgeSide.entries.forEach { side -> it -= side.key }
        sides.forEach { data -> it[data.side.key] = data.serializeToJsonElement() }
    }
}
