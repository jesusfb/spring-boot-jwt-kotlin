package example.project.app.utils.mapper

import example.project.app.dtos.UserRegisterDTO
import example.project.app.models.User
import org.springframework.stereotype.Service

@Service
class UserMapper {
    fun toEntity(userRegisterDTO: UserRegisterDTO): User {
        val user = User()
        user.username = userRegisterDTO.username
        user.email = userRegisterDTO.email
        user.password = userRegisterDTO.password
        return user
    }
}