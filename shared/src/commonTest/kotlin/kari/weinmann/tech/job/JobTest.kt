package kari.weinmann.tech.job

import kari.weinmann.tech.actitime.ActitimeConnection
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.*
 class JobTest {
  lateinit var dut: Job
  init{
   val user:String = System.getenv("USER")
   val pass = System.getenv("PASS")
   val base = System.getenv("BASE")

   val connection = ActitimeConnection.createInstance(base, user, pass)
   dut = Job(connection, 35)
  }
@Test
 fun getDeltaFrom() = runTest {
 val res = dut.getDeltaFrom(LocalDate.parse("2025-07-01"))
 assertTrue(res != 0)
}

@Test
 fun getWeekSaldo() {}
}