description = "ROOT"
plugins {
    id("online.colaba.ssh") version "1.7.33"
    id("online.colaba.docker") version "1.2" apply false
}
defaultTasks("compose")
allprojects{ group = "space.colaba" }
subprojects{ apply(plugin = "online.colaba.docker") }
