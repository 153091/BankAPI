package service;

import Entity.User;
import dto.UserDTO;
import repository.UserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    UserRepositoryImpl userRepository;


    public UserDTO register(User user) throws SQLException {
        return fromUserDTO(userRepository.addUser(user));
    }

    public UserDTO getUserById(int id) throws SQLException {
        return fromUserDTO(userRepository.getUserById(id));
    }

    public List<UserDTO> getAll() throws SQLException {
        List<User> listUser = userRepository.getAll();
        List<UserDTO> listUserDto = new ArrayList<>();

        for (User user : listUser) {
            listUserDto.add(fromUserDTO(user));
        }
        return listUserDto;
    }

    private UserDTO fromUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge());

    }
}