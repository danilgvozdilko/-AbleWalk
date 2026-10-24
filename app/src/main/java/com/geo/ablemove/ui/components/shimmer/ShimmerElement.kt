package com.geo.ablemove.ui.components.shimmer

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo

fun Modifier.shimmer(
    visible: Boolean,
    shape: Shape,
    themeOverride: ShimmerTheme,
): Modifier = this then ShimmerElement(
    visible = visible,
    shape = shape,
    themeOverride = themeOverride,
)

private data class ShimmerElement(
    val visible: Boolean,
    val shape: Shape,
    val themeOverride: ShimmerTheme?,
) : ModifierNodeElement<ShimmerNode>() {

    override fun create(): ShimmerNode = ShimmerNode(
        visible = visible,
        shape = shape,
        themeOverride = themeOverride,
    )

    override fun update(node: ShimmerNode) {
        node.update(
            visible = visible,
            shape = shape,
            themeOverride = themeOverride,
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "shimmer"
        properties["visible"] = visible
        properties["shape"] = shape
        properties["themeOverride"] = themeOverride
    }
}
