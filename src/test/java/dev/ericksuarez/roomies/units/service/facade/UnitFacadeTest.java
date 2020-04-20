package dev.ericksuarez.roomies.units.service.facade;

import dev.ericksuarez.roomies.units.service.model.entity.Unit;
import dev.ericksuarez.roomies.units.service.model.entity.User;
import dev.ericksuarez.roomies.units.service.service.UnitService;
import dev.ericksuarez.roomies.units.service.service.UserService;
import dev.ericksuarez.roomies.units.service.util.ReferenceGenerator;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UnitFacadeTest {

    @Autowired
    private UnitFacade unitFacade;

    @Mock
    private UnitService unitService;

    @Mock
    private UserService userService;

    @Mock
    private ReferenceGenerator referenceGenerator;

    @Before
    public void setUp(){
        unitFacade = new UnitFacade(userService, unitService, referenceGenerator);
    }

    @Test
    public void registerUser_userOk_returnUser() {
        val unit =  Unit.builder()
                .id(5L)
                .build();
        val user = User.builder()
                .id(UUID.randomUUID())
                .username("Random name")
                .build();

        doReturn(unit)
                .when(unitService).saveOrUpdateUnit(any(Unit.class));
        doReturn(user)
                .when(userService).findUser(any(UUID.class));
        doReturn(user)
                .when(userService).saveOrUpdateUser(any(UUID.class), any(User.class));

        val unitSaved = unitFacade.createUnit(UUID.randomUUID(), unit);

        assertNotNull(unitSaved);
    }
}
