package com.geo.ablemove.ui.components.shimmer

import android.graphics.Matrix
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.node.ObserverModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.node.observeReads
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.hypot

class ShimmerNode(
    var visible: Boolean,
    var shape: Shape,
    var themeOverride: ShimmerTheme?,
) : Modifier.Node(),
    DrawModifierNode,
    CompositionLocalConsumerModifierNode,
    GlobalPositionAwareModifierNode,
    ObserverModifierNode {

    private val shaderMatrix = Matrix()
    private val paint = Paint()

    private var globalX = 0f
    private var globalY = 0f

    private var cachedOutline: Outline? = null
    private var cachedSize: Size = Size.Unspecified
    private var cachedLayoutDirection: LayoutDirection? = null
    private var cachedShape: Shape? = null
    private var cachedTheme: ShimmerTheme? = null

    private var attachedHost: ShimmerHostState? = null

    override fun onAttach() {
        updateHostRegistration()
    }

    override fun onDetach() {
        attachedHost?.let { it.activeNodeCount -= 1 }
        attachedHost = null
    }

    override fun onObservedReadsChanged() {
        updateHostRegistration()
    }

    fun update(
        visible: Boolean,
        shape: Shape,
        themeOverride: ShimmerTheme?,
    ) {
        val visibilityChanged = this.visible != visible

        this.visible = visible
        this.shape = shape
        this.themeOverride = themeOverride

        if (visibilityChanged) {
            updateHostRegistration()
        }

        invalidateDraw()
    }

    private fun updateHostRegistration() {
        observeReads {
            val newHost = currentValueOf(LocalShimmerHostState)

            if (!visible) {
                attachedHost?.let { it.activeNodeCount -= 1 }
                attachedHost = null
                return@observeReads
            }

            if (newHost != attachedHost) {
                attachedHost?.let { it.activeNodeCount -= 1 }
                attachedHost = newHost
                attachedHost?.let { it.activeNodeCount += 1 }
            }
        }
    }

    override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
        val position = coordinates.positionInWindow()
        globalX = position.x
        globalY = position.y
    }

    override fun ContentDrawScope.draw() {
        if (!visible) {
            drawContent()
            return
        }

        val host = attachedHost
        val activeTheme = themeOverride ?: host?.theme

        if (activeTheme == null) {
            drawContent()
            return
        }

        val waveSize = host?.waveSize ?: (hypot(size.width, size.height) * 2f)
        val progress = host?.progress ?: 0f

        if ((cachedSize != size) || (cachedTheme != activeTheme)) {
            createShader(theme = activeTheme, waveSize = waveSize)
            cachedSize = size
            cachedTheme = activeTheme
        }

        if (
            cachedOutline == null ||
            cachedSize != size ||
            cachedLayoutDirection != layoutDirection ||
            cachedShape != shape
        ) {
            cachedOutline = shape.createOutline(size, layoutDirection, this)
            cachedLayoutDirection = layoutDirection
            cachedShape = shape
        }

        val translation = (progress * waveSize) - (waveSize / 2f)

        shaderMatrix.reset()
        shaderMatrix.setRotate(activeTheme.angle)
        shaderMatrix.postTranslate(
            translation - globalX,
            -globalY,
        )

        paint.shader?.let { shader ->
            shader.setLocalMatrix(shaderMatrix)
            paint.shader = shader
        }

        drawIntoCanvas { canvas ->
            canvas.drawOutline(cachedOutline!!, paint)
        }
    }

    private fun createShader(
        theme: ShimmerTheme,
        waveSize: Float,
    ) {
        val waveWidth = waveSize / 2f
        paint.shader = android.graphics.LinearGradient(
            0f,
            0f,
            waveWidth,
            0f,
            theme.argbColors,
            theme.colorPositions,
            android.graphics.Shader.TileMode.CLAMP,
        )
    }
}
