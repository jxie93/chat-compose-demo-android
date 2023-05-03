package com.example.shared

import platform.Foundation.NSUUID

actual fun randomUUID(): String {
    return NSUUID().UUIDString()
}