package com.kks.instagramcompose.data

open class Event<out T>(private val content: T) {

    /* private set 은 hasBeenHandled 변수를
    이 클래스 내에서만 set(변경)할 수 있게한다는 의미다.*/
    var hasBeenHandled = false
        private set

    fun getContentOrNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}