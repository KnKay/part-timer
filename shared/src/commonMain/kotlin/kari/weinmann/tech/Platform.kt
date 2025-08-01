package kari.weinmann.tech

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform