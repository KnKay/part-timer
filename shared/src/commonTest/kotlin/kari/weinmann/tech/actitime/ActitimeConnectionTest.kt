package kari.weinmann.tech.actitime

import kotlinx.coroutines.test.runTest
import kotlin.test.*

 class ActitimeConnectionTest {
  lateinit var connection: ActitimeConnection
 init{
   val user:String = System.getenv("USER")
   val pass = System.getenv("PASS")
   val base = System.getenv("BASE")

   connection = ActitimeConnection.createInstance(base, user, pass)
  }
@Test
fun whoAmI() = runTest {
    val dut = connection.whoAmI()
    assertEquals(dut.username, "K.Richter")
 }
}