    package com.example.ssoprovider.service;
    
    
    import com.example.ssoprovider.dto.ChangePasswordDto;
    import com.example.ssoprovider.dto.UserLoginDto;
    import com.example.ssoprovider.dto.UserRegisterDto;
    import com.example.ssoprovider.dto.UserResponseDto;
    import com.example.ssoprovider.jwt.JWTConfig;
    import com.example.ssoprovider.model.User;
    import com.example.ssoprovider.model.UserUpdateDto;
    import com.example.ssoprovider.repository.SSOAuthRepository;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.*;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.mockito.junit.jupiter.MockitoSettings;
    import org.mockito.quality.Strictness;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    
    import java.time.LocalDate;
    import java.util.*;
    
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;
    
    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    class SSOAuthServiceTest {
    
        @Mock
        private PasswordEncoder passwordEncoder;
    
        private SSOAuthServiceImpl ssoAuthService;
    
        @Mock
        private SSOAuthRepository userRepository;
    
        @Mock
        private JWTConfig jwtConfig;
    
        @Captor
        private ArgumentCaptor<User> userCaptor;
    
        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            ssoAuthService = new SSOAuthServiceImpl(userRepository, jwtConfig, passwordEncoder);
        }
    
        @Test
        void registerShouldReturnUserResponseDtoWithToken() {
            UserRegisterDto registerDto = new UserRegisterDto("Alex", "alex@example.com", "pass123", java.sql.Date.valueOf("2000-11-30"));
            User savedUser = new User();
            savedUser.setUserId("user123");
            savedUser.setEmail("alex@example.com");
            savedUser.setPassword("encodedPass");
            registerDto.setDateOfBirth(java.sql.Date.valueOf("1990-11-30"));
    
            savedUser.setFullName("Alex");
    
            when(userRepository.save(any(User.class))).thenReturn(savedUser);
            when(jwtConfig.generateToken(savedUser)).thenReturn("jwt-token");
    
            when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> {
                String raw = invocation.getArgument(0);
                return "encoded_" + raw;  // or any non-null dummy encoded string
            });
    
            UserResponseDto response = ssoAuthService.register(registerDto);
    
            assertTrue(response.isSuccess());
            assertEquals("alex@example.com", response.getEmail());
            assertEquals("jwt-token", response.getToken());
            assertEquals("user123", response.getId());
    
            verify(userRepository).save(userCaptor.capture());
            User userToSave = userCaptor.getValue();
            assertEquals("Alex", userToSave.getFullName());
            assertEquals("alex@example.com", userToSave.getEmail());
            assertNotNull(userToSave.getPassword());
            assertEquals(LocalDate.of(1990, 11, 30), userToSave.getBirthDate());
        }
    
        @Test
        void loginWhenUserNotFoundShouldReturnFailureResponse() {
            UserLoginDto loginDto = new UserLoginDto("alex@example.com", "pass123");
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.empty());
    
            UserResponseDto response = ssoAuthService.login(loginDto);
    
            assertFalse(response.isSuccess());
            assertEquals("User not found", response.getMessage());
        }
    
        @Test
        void loginWhenPasswordDoesNotMatchShouldReturnFailureResponse() {
            String rawPassword = "correctPass";
            UserLoginDto loginDto = new UserLoginDto("alex@example.com", "wrongPass");
    
            User user = new User();
            user.setUserId("user123");
            user.setEmail("alex@example.com");
            user.setPassword(passwordEncoder.encode(rawPassword));
    
            // THIS MUST BE EXACT MATCH for email
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.of(user));
    
            UserResponseDto response = ssoAuthService.login(loginDto);
    
            assertFalse(response.isSuccess());
            assertEquals("Invalid credentials", response.getMessage());
        }
    
    
        @Test
        void loginWhenCorrectCredentialsShouldReturnSuccessResponse() {
            String rawPassword = "pass123";
            UserLoginDto loginDto = new UserLoginDto("alex@example.com", rawPassword);
            User user = new User();
            user.setUserId("user123");
            user.setEmail("alex@example.com");
            user.setPassword(passwordEncoder.encode(rawPassword));
    
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.of(user));
            when(jwtConfig.generateToken(user)).thenReturn("jwt-token");
    
            UserResponseDto response = ssoAuthService.login(loginDto);
    
            assertTrue(response.isSuccess());
            assertEquals("alex@example.com", response.getEmail());
            assertEquals("jwt-token", response.getToken());
            assertEquals("user123", response.getId());
            assertEquals("Successfully authenticated!", response.getMessage());
        }
    
        @Test
        void changePasswordWhenUserNotFoundShouldReturnFailure() {
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.empty());
            ChangePasswordDto dto = new ChangePasswordDto("old", "new");
    
            UserResponseDto response = ssoAuthService.changePassword("alex@example.com", dto);
    
            assertFalse(response.isSuccess());
            assertEquals("User not found", response.getMessage());
        }
    
        @Test
        void changePasswordWhenOldPasswordIncorrectShouldReturnFailure() {
            String encodedPassword = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("correctOldPass");
            User user = new User();
            user.setPassword(encodedPassword);
            user.setUserId("user123");
            user.setEmail("alex@example.com");
    
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.of(user));
    
            ChangePasswordDto dto = new ChangePasswordDto("wrongOldPass", "newPass");
    
            UserResponseDto response = ssoAuthService.changePassword("alex@example.com", dto);
    
            assertFalse(response.isSuccess());
            assertEquals("Old password is incorrect", response.getMessage());
        }
    
        @Test
        void changePasswordWhenOldPasswordCorrectShouldUpdatePasswordAndReturnSuccess() {
            String oldEncodedPassword = passwordEncoder.encode("oldPass");
            User user = new User();
            user.setPassword(oldEncodedPassword);
            user.setUserId("user123");
            user.setEmail("alex@example.com");
    
            when(userRepository.findByEmail("alex@example.com")).thenReturn(Optional.of(user));
            when(jwtConfig.generateToken(user)).thenReturn("jwt-token");
            when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));


            when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");

            ChangePasswordDto dto = new ChangePasswordDto("oldPass", "newPass");
            when(passwordEncoder.matches("oldPass", oldEncodedPassword)).thenReturn(true);
            UserResponseDto response = ssoAuthService.changePassword("alex@example.com", dto);
    
            assertTrue(response.isSuccess());
            assertEquals("Successfully changed password", response.getMessage());
            assertEquals("alex@example.com", response.getEmail());
            assertEquals("jwt-token", response.getToken());
            assertEquals("user123", response.getId());
    
            verify(userRepository).save(userCaptor.capture());
            User savedUser = userCaptor.getValue();
            assertNotEquals(oldEncodedPassword, savedUser.getPassword());
        }
    
        @Test
        void logoutWhenTokenAlreadyBlacklistedShouldReturnFailure() {
            String token = "token123";
            ssoAuthService.getTokenBlacklist().add(token);
    
            UserResponseDto response = ssoAuthService.logout(token);
    
            assertFalse(response.isSuccess());
            assertEquals("User already logged out!", response.getMessage());
        }
    
        @Test
        void logoutWhenTokenNotBlacklistedShouldAddTokenAndReturnSuccess() {
            String token = "token123";
    
            UserResponseDto response = ssoAuthService.logout(token);
    
            assertTrue(response.isSuccess());
            assertEquals("Successfully logged out!", response.getMessage());
            assertTrue(ssoAuthService.getTokenBlacklist().contains(token));
        }
    
        @Test
        void getUserDataShouldReturnListOfUserResponseDtos() {
            User user1 = new User();
            user1.setUserId("id1");
            user1.setEmail("email1@example.com");
            User user2 = new User();
            user2.setUserId("id2");
            user2.setEmail("email2@example.com");
    
            when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
            when(jwtConfig.generateToken(user1)).thenReturn("token1");
            when(jwtConfig.generateToken(user2)).thenReturn("token2");
    
            List<UserResponseDto> users = ssoAuthService.getUserData();
    
            assertEquals(2, users.size());
    
            assertEquals("id1", users.get(0).getId());
            assertEquals("email1@example.com", users.get(0).getEmail());
            assertEquals("token1", users.get(0).getToken());
    
            assertEquals("id2", users.get(1).getId());
            assertEquals("email2@example.com", users.get(1).getEmail());
            assertEquals("token2", users.get(1).getToken());
        }
    
        @Test
        void deleteUserShouldCallRepositoryDeleteById() {
            String userId = "user123";
            doNothing().when(userRepository).deleteById(userId);
    
            ssoAuthService.deleteUser(userId);
    
            verify(userRepository, times(1)).deleteById(userId);
        }
    }
    
