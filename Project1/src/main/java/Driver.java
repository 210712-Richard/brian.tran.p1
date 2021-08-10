
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.UserController;
import com.revature.controllers.UserControllerImpl;
import com.revature.factory.BeanFactory;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		instantiateDatabase();
		javalin();
	}

	public static void instantiateDatabase() {
		///DatabaseCreator.dropTables();
//		try {
//			Thread.sleep(30000); // wait 30 seconds
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		DatabaseCreator.createTables();
//		try {
//			Thread.sleep(20000); // wait 20 seconds
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		DatabaseCreator.populateEmployeeTable();
		//DatabaseCreator.populateGachaTable();
		System.exit(0);
	}

	public static void javalin() {
		
		// Set up Jackson to serialize LocalDates and LocalDateTimes
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);
		
		// Starts the Javalin Framework
		Javalin app = Javalin.create().start(8080);
		
		UserController uc = (UserController) BeanFactory.getFactory().get(UserController.class, UserControllerImpl.class);
		//GachaController gachaController = (GachaController) BeanFactory.getFactory().get(GachaController.class, GachaControllerImpl.class);

	}
}
