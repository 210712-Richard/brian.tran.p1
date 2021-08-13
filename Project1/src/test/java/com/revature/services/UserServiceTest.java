package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;

public class UserServiceTest {

	private UserService service = null;
	private User user = null;
	private UserDao ud = null;

	@BeforeEach
	public void beforeTest() {

		user = new User(UUID.randomUUID(),"test", "test12",  "testF", "testL", "test@test.com", null, 1000, UserType.EMPLOYEE,"testS", "testH", "testB");
		ud = Mockito.mock(UserDao.class);		
	
	}
	@Test
	public void testLogin() {
		Mockito.when(ud.getUser(user.getUsername())).thenReturn(user);

		ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
		
		User loginUser = service.login(user.getUsername(), user.getPassword());

		assertEquals(user, loginUser, "Assert same user");
		}

	
	@Test
	public void testRegister() {
		String username = "test";
		String password = "test12";
		service.register("test", "test12",  "testF", "testL", "test@test.com", null, UserType.EMPLOYEE);
		
		// An object that allows us to receive parameters from methods called on a Mockito mock object
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
	
		// ud.addUser() was called with our User as an argument.
		Mockito.verify(ud).addUser(captor.capture());
	

	}
}
