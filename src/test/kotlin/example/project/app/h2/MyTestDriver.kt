package example.project.app.h2

import java.sql.Connection
import java.sql.DriverManager


class MyTestDriver {
    private val jdbcURL = "jdbc:h2:mem:testdb;mode=mysql"
    private val jdbcUsername = "sa"
    private val jdbcPassword = "password"

    fun getConnection(): Connection? {
        var connection: Connection? = null

        try { connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)
            println("Connection to H2 DB successful!")
        } catch (e: Exception) {
            println(e)
        }
        return connection
    }

}