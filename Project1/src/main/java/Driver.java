
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.RequestController;
import com.revature.controllers.RequestControllerImpl;
import com.revature.controllers.UserController;
import com.revature.controllers.UserControllerImpl;
import com.revature.factory.BeanFactory;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	public static void main(String[] args) {
		//instantiateDatabase();
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
		RequestController rc = (RequestController) BeanFactory.getFactory().get(RequestController.class, RequestControllerImpl.class);
		//UserController uc = new UserControllerImpl();
		//RequestController rc = new RequestControllerImpl();
		
		// As a user, I can login
		app.post("/login", uc::login);
		
		// As a user, I can get a user information
		//app.get("/users/:username", uc::login);
		
		// As a user, I can register for a login account
		app.post("/users/", uc::register);
		
		// As a user, I can update a user account
		//app.put("/users/:userid", uc::update)
		
		// As a user, I can logout 
		app.delete("/users/:username", uc::logout);
		
		
		//Endpoints for Reimbursements
		//As a user, I can get a reimbursement
		app.get("/users/:username/reimbursements", rc::getRequest);
		
		//As an Admin, I can get urgent requests
		app.get("/users/:username/urgents", rc::getUrgents);
		
		//As a user, I can request for a reimbursement
		app.post("/users/:username/reimbursements", rc::newRequest);
		
		//As a user, I can cancel a pending request
		app.delete("/users/:username/reimbursements", rc::cancelRequest);
		
		//As a user, I can update/approve/deny a reimbursement request
		app.put("/users/:username/reimbursements/:requestid", rc::updateRequest);
		//As a user, I can approve/deny a reimbursement request
		
		//Endpoints for S3
		//As a user, I can upload files to S3 bucket	
		app.post("/users/:username/reimbursements/:requestid/attachments", rc::uploadFile);
		
		//As a user, I can get an attachment from a request
		app.get("/users/:username/reimbursements/:requestid/attachments", rc::getFile);

	}
}
