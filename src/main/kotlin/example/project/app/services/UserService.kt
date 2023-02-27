package example.project.app.services

import example.project.app.repositories.UserRepository
import example.project.app.services.interfaces.IUserService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.*
import example.project.app.models.User

@Service
class UserService(private val userRepository: UserRepository): IUserService {

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun getUserById(id: String): User {
        return userRepository.findById(UUID.fromString(id)).orElseThrow { EmptyResultDataAccessException(1) }
    }

    override fun delete(userId: UUID) {
        try { userRepository.deleteById(userId)
        } catch (e: EmptyResultDataAccessException) {
            throw EmptyResultDataAccessException(1)
        }
    }
}