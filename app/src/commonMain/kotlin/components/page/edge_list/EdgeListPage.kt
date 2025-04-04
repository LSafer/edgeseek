/*
 *	Copyright 2020-2022 LSafer
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */

@file:OptIn(ExperimentalFoundationApi::class)

package net.lsafer.edgeseek.app.components.page.edge_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import net.lsafer.edgeseek.app.Local
import net.lsafer.edgeseek.app.UniRoute
import net.lsafer.edgeseek.app.components.lib.MobileModel
import net.lsafer.edgeseek.app.data.settings.EdgeData
import net.lsafer.edgeseek.app.data.settings.EdgePos
import net.lsafer.edgeseek.app.data.settings.EdgeSide
import net.lsafer.edgeseek.app.data.settings.calculateLengthPct
import net.lsafer.edgeseek.app.l10n.strings
import net.lsafer.sundry.storage.select

@Composable
fun EdgeListPage(
    local: Local,
    route: UniRoute.EdgeListPage,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        snackbarHost = {
            SnackbarHost(local.snackbar)
        },
    ) { innerPadding ->
        EdgeListPageContent(local, Modifier.padding(innerPadding))
    }
}

@Composable
fun EdgeListPageContent(
    local: Local,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Spacer(Modifier.height(50.dp))

        Text(
            text = strings.stmt.page_edge_list_heading,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = strings.stmt.page_edge_list_summary,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(.5f),
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            MobileModel(Modifier.fillMaxSize())

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                for (pos in EdgePos.entries)
                    Item(local, pos)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BoxScope.Item(
    local: Local,
    pos: EdgePos,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    val handleOnClick: () -> Unit = {
        coroutineScope.launch {
            local.navController.push(UniRoute.EdgeEditPage(pos))
        }
    }

    val data by produceState(EdgeData(pos)) {
        local.dataStore
            .select<EdgeData>(pos.key)
            .filterNotNull()
            .distinctUntilChanged()
            .collect { value = it }
    }

    val thickness = 24.dp
    val lengthPct = pos.calculateLengthPct()

    val alignModifier = when (pos) {
        EdgePos.BottomLeft -> Modifier.align(Alignment.BottomStart)
        EdgePos.BottomRight -> Modifier.align(Alignment.BottomEnd)
        EdgePos.TopLeft -> Modifier.align(Alignment.TopStart)
        EdgePos.TopRight -> Modifier.align(Alignment.TopEnd)
        EdgePos.LeftBottom -> Modifier.align(Alignment.BottomStart)
        EdgePos.LeftCenter -> Modifier.align(Alignment.CenterStart)
        EdgePos.LeftTop -> Modifier.align(Alignment.TopStart)
        EdgePos.RightBottom -> Modifier.align(Alignment.BottomEnd)
        EdgePos.RightCenter -> Modifier.align(Alignment.CenterEnd)
        EdgePos.RightTop -> Modifier.align(Alignment.TopEnd)
    }
    val widthModifier = when (pos.side) {
        EdgeSide.Top, EdgeSide.Bottom -> Modifier.fillMaxWidth(lengthPct)
        EdgeSide.Left, EdgeSide.Right -> Modifier.width(thickness)
    }
    val heightModifier = when (pos.side) {
        EdgeSide.Top, EdgeSide.Bottom -> Modifier.height(thickness)
        EdgeSide.Left, EdgeSide.Right -> Modifier.fillMaxHeight(lengthPct)
    }
    val innerPaddingModifier = when (pos) {
        EdgePos.LeftBottom, EdgePos.RightBottom -> Modifier.padding(bottom = thickness)
        EdgePos.LeftTop, EdgePos.RightTop -> Modifier.padding(top = thickness)
        EdgePos.BottomLeft, EdgePos.TopLeft -> Modifier.padding(start = thickness)
        EdgePos.BottomRight, EdgePos.TopRight -> Modifier.padding(end = thickness)
        else -> Modifier
    }
    val innerColor = when {
        data.activated -> Color(data.color).copy(alpha = .5f)
        else -> Color.Gray
    }

    Box(
        Modifier
            .then(alignModifier)
            .then(widthModifier)
            .then(heightModifier)
            .then(innerPaddingModifier)
            .then(modifier)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(2.5.dp)
                .background(innerColor, RoundedCornerShape(30.dp))
                .combinedClickable(onClick = handleOnClick)
        )
    }
}
