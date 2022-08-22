@file:OptIn(ExperimentalFoundationApi::class)

package net.lsafer.edgeseek.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.LocalNavController
import net.lsafer.edgeseek.model.EdgeData
import net.lsafer.edgeseek.model.EdgeSide
import net.lsafer.edgeseek.model.rememberApplicationData
import net.lsafer.edgeseek.ui.widget.content.MobileModel
import net.lsafer.edgeseek.ui.widget.preferences.*
import net.lsafer.edgeseek.util.NavigationBarPadding
import net.lsafer.edgeseek.util.StatusBarPadding

const val EdgesScreenRoute = "edges"

@Composable
fun EdgesScreen() {
    Scaffold(
        Modifier.padding(
            top = StatusBarPadding,
            bottom = NavigationBarPadding
        ),
        content = {
            EdgesScreenContent()
        }
    )
}

@Composable
fun EdgesScreenContent() {
    val context = LocalContext.current
    val layoutDirection = LocalLayoutDirection.current
    val navController = LocalNavController.current

    val coroutineScope = rememberCoroutineScope()

    var data by rememberApplicationData()

    var graphical by remember { mutableStateOf(true) }

    fun handleAddEdge() {
        coroutineScope.launch {
            val edge = EdgeData()
            data = data.copy(edges = data.edges + edge)
            navController.navigate(EdgeScreenRoute.replace("{id}", edge.id))
        }
    }

    fun handleConfigureEdge(edge: EdgeData) {
        coroutineScope.launch {
            navController.navigate(EdgeScreenRoute.replace("{id}", edge.id))
        }
    }

    fun handleDeleteEdge(edge: EdgeData) {
        coroutineScope.launch {
            data = data.copy(edges = data.edges.filter { it.id != edge.id })
        }
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        PreferenceHeader(
            title = "Choose Edge",
            summary = "Touch an edge to customize it"
        )
        PreferenceSection(title = "Display")
        SwitchPreference(
            checked = graphical,
            onCheckedChange = { graphical = it },
            title = "Graphical List",
            summary = "Toggle the configuration display from graphical style to list style"
        )
        PreferenceDivider()
        Crossfade(graphical) {
            when (it) {
                true -> GraphicalEdgeList(
                    edges = data.edges,
                    onAddEdge = { handleAddEdge() },
                    onConfigureEdge = { handleConfigureEdge(it) },
                    onDeleteEdge = { handleDeleteEdge(it) }
                )
                false -> EdgesPreferenceList(
                    edges = data.edges,
                    onAddEdge = { handleAddEdge() },
                    onConfigureEdge = { handleConfigureEdge(it) },
                    onDeleteEdge = { handleDeleteEdge(it) }
                )
            }
        }
        Spacer(Modifier.height(50.dp))
    }
}

@Composable
fun EdgesPreferenceList(
    edges: List<EdgeData>,
    onAddEdge: () -> Unit,
    onConfigureEdge: (EdgeData) -> Unit,
    onDeleteEdge: (EdgeData) -> Unit
) {
    Column {
        PreferenceSection(title = "Edges")
        edges.forEach { edge ->
            EdgeItemPreference(
                edge = edge,
                onConfigure = { onConfigureEdge(edge) },
                onDelete = { onDeleteEdge(edge) }
            )
        }
        Preference(
            title = "Add Another Edge",
            summary = "Click here to add another edge",
            onClick = { onAddEdge() }
        )
    }
}

@Composable
fun EdgeItemPreference(
    edge: EdgeData,
    onConfigure: () -> Unit,
    onDelete: () -> Unit
) {
    val idTruncated = when {
        edge.id.length > 10 -> edge.id.take(10) + "..." + edge.id.take(10)
        else -> edge.id
    }
    Preference(
        title = edge.side.name,
        summary = "Click here to configure:\n${idTruncated}",
        onClick = { onConfigure() },
        action = {
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    )
}

@Composable
fun GraphicalEdgeList(
    edges: List<EdgeData>,
    onAddEdge: () -> Unit,
    onConfigureEdge: (EdgeData) -> Unit,
    onDeleteEdge: (EdgeData) -> Unit
) {
    Box(Modifier.fillMaxWidth().aspectRatio(.75f).padding(bottom = 40.dp, start = 60.dp, end = 60.dp)) {
        MobileModel(Modifier.fillMaxSize())

        IconButton(onClick = { onAddEdge() }, Modifier.align(Alignment.Center)) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            edges.forEach { edge ->
                GraphicalEdgeItem(
                    edge = edge,
                    onConfigure = { onConfigureEdge(edge) },
                    onDelete = { onDeleteEdge(edge) }
                )
            }
        }
    }
}

@Composable
fun BoxScope.GraphicalEdgeItem(
    edge: EdgeData,
    onConfigure: () -> Unit,
    onDelete: () -> Unit
) {
    val modifier = Modifier
        .padding(5.dp)
        .background(Color((edge.color)).copy(alpha = .5f), RoundedCornerShape(30.dp))
        .combinedClickable(
            onClick = { onConfigure() },
            onLongClick = { onDelete() }
        )

    when (edge.side) {
        EdgeSide.Bottom -> Row(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                Modifier
                    .fillMaxWidth(edge.ratio)
                    .height(edge.width.dp)
                    .then(modifier)
            )
            Spacer(Modifier.fillMaxWidth(edge.offset))
        }
        EdgeSide.Left -> Column(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                Modifier
                    .fillMaxHeight(edge.ratio)
                    .width(edge.width.dp)
                    .then(modifier)
            )
            Spacer(Modifier.fillMaxHeight(edge.offset))
        }
        EdgeSide.Top -> Row(
            modifier = Modifier.align(Alignment.TopStart),
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(Modifier.fillMaxWidth(edge.offset))
            Box(
                Modifier
                    .fillMaxWidth(edge.ratio)
                    .height(edge.width.dp)
                    .then(modifier)
            )
        }
        EdgeSide.Right -> Column(
            modifier = Modifier.align(Alignment.TopEnd),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.fillMaxHeight(edge.offset))
            Box(
                Modifier
                    .fillMaxHeight(edge.ratio)
                    .width(edge.width.dp)
                    .then(modifier)
            )
        }
    }
}
