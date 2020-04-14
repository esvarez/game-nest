package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.dto.RegisterUserDto;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.model.responses.AuthUserResponse;
import dev.ericksuarez.roomies.units.service.service.AuthClientService;
import dev.ericksuarez.roomies.units.service.service.UserClientService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;

    @Mock
    private AuthClientService authClientService;

    @Mock
    private UserClientService userClientService;

    @Mock
    private UserService userService;

    @Before
    public void setUp(){
        userFacade = new UserFacade(authClientService, userClientService, userService);
    }

    @Test
    public void registerUser_userOk_returnUser() {
        val userDto =  RegisterUserDto.builder()
                .email("nuevo@mail.com")
                .enabled(true)
                .username("nuevo")
                .build();
        val userResponse = AuthUserResponse
                .builder()
                .id(UUID.randomUUID())
                .build();
        val user = User.builder()
                .id(userResponse.getId())
                .username(userDto.getUsername())
                .build();

        doReturn(userResponse)
                .when(userClientService).registerServerUser(any(RegisterUserDto.class));
        doReturn(user)
                .when(userService).registerUser(any(User.class));

        val userSaved = userFacade.registerUser(userDto, Optional.empty());

        assertNotNull(userSaved.getId());
        assertEquals(userDto.getUsername(), userSaved.getUsername());
        assertEquals(Optional.of(1L), userSaved.getUnit().getId());
    }

    @Test
    public void testToBuilder() {
        val user = User.builder()
                .active(true)
                .build();

        assertTrue(user.getActive());

        val user2 = user.toBuilder()
                .id(UUID.randomUUID())
                .build();

        assertTrue(user2.getActive());
        assertNotNull(user2.getId());
    }

}
