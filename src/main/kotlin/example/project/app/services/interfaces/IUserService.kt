package example.project.app.services.interfaces

import example.project.app.models.User
import java.util.*

interface IUserService {

    fun getAll(): List<User>
    fun getUserById(id: String): User
    fun delete(userId: UUID)
}