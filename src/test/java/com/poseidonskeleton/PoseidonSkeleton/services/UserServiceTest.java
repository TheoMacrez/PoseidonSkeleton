package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserDomain user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserDomain(passwordEncoder);
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFullname("Test User");
        user.setRole("USER");
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(any(UserDomain.class))).thenReturn(user);

        UserDomain createdUser = userService.saveUser(user);

        assertThat(createdUser).isEqualTo(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetAllUsers() {
        List<UserDomain> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDomain> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result).contains(user);
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<UserDomain> result = userService.getUserById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(any(UserDomain.class))).thenReturn(user);

        UserDomain updatedUser = userService.updateUser(1, user);

        assertThat(updatedUser).isEqualTo(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
