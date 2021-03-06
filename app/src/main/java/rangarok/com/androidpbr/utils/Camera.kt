package rangarok.com.androidpbr.utils

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import kotlin.math.cos
import kotlin.math.sin

class Camera {

    var position = Vec3(0)
    var front = Vec3(0, 0, -1)
    var up = Vec3(0, 1, 0)
    var worldUp = up
    var right = Vec3(0)
    var yaw: Float = -90.0f
    var pitch: Float = 0f
    var zoom = 45.0f

    constructor(pos: Vec3 = Vec3(0), up: Vec3 = Vec3(0, 1, 0), yaw: Float = -90f, pitch: Float = 0f) {
        this.position = pos
        this.up = up
        this.yaw = yaw
        this.pitch = pitch
        calcPostion()
    }

    fun getViewMatrix(): Mat4 {
        return glm.lookAt(position, position + front, up)
    }

    fun lookAt(center: Vec3): Mat4 {
        return glm.lookAt(position, center, up)
    }

    private fun calcPostion() {
        front.x = cos(glm.radians(yaw)) * cos(glm.radians(pitch))
        front.y = sin(glm.radians(pitch))
        front.z = sin(glm.radians(yaw)) * cos(glm.radians(pitch))
        front = glm.normalize(front)
        // Also re-calculate the Right and Up vector
        right = glm.normalize(glm.cross(front, worldUp))  // Normalize the vectors, because their length gets closer to 0 the more you look up or down which results in slower movement.
        up    = glm.normalize(glm.cross(right, front))
    }
}