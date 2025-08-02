package kari.weinmann.tech.actitime

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
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

    @Test
     fun getGroups() = runTest {
         val dut = connection.getGroups()
         assertTrue(dut.size > 1)
     }
     @Test
     fun getProjects() = runTest {
         val dut = connection.getProjects()
         assertTrue(dut.size > 1)
     }
     @Test
     fun getTasks() = runTest {
         val dut = connection.getTasks()
         assertTrue(dut.size > 1)
     }
     @Test
     fun getTasksForProject() = runTest {
         val dut = connection.getProjectTasks(2732)
         assertTrue(dut.size > 1)
     }
    @Test
    fun getSpentTime() = runTest {
        val jan=LocalDate.parse("2025-01-01")
        val dut = connection.getSpentTime(jan)
        assertEquals(dut.from, jan)
        assertTrue(dut.spent > 100)
    }

     @Test
     fun getTaskTime() = runTest {
        val dut = connection.getTaskTime(LocalDate.parse("2025-07-28"), 28472)
        assertEquals(dut, 90)
     }

     @Test
     fun addTime()= runTest {
         connection.addTime(28472, 1, LocalDate.parse("2025-01-01"))
     }

 }