
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.EventType;
import com.revature.beans.Request;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.ReimbursementDao;
import com.revature.data.ReimbursementDaoImpl;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class DatabaseCreator {
	private static UserDao ud = new UserDaoImpl();
	private static ReimbursementDao rd = new ReimbursementDaoImpl();
	private static final Logger log = LogManager.getLogger(CassandraUtil.class);

	
	public static void dropTables() {
		log.trace("Drop Tables Called");
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS Employee;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS Reimbursement;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}
	
	public static void createTables() {
		log.trace("Create Tables Called");
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS Employee (")
				.append("uid uuid PRIMARY KEY, username text, password text, firstName text, lastName text, email text, managerId uuid, ")
				.append("availableAmount float, type text, directSupervisor text, departmentHead text, benCo text );");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS project1.Reimbursement (")
				.append("rid uuid PRIMARY KEY, eid uuid, reimburseAmount float, rewardAmount float, firstName text, lastName text, email text, submitDate date, ")
				.append("submitTime time, location text, description text, cost float, startDate date, gradingFormat text, ")
				.append("passingGrade text, attachment text, type text, approvedBy text, status text, reason text, isUrgent boolean);");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
	}
	
	public static void populateEmployeeTable() {
		log.trace("Populate Tables Called");
				
		UUID henryuuid = UUID.randomUUID();
		UUID susanuuid = UUID.randomUUID();
		
		ud.addUser(new User(UUID.randomUUID(), "ben", "ben12", "Ben", "Co", "bc@gmail.com", null, 1000, UserType.BENCO, null, null, null));
		ud.addUser(new User(henryuuid, "henry", "henry12", "Henry", "Head", "hh@gmail.com", null, 1000, UserType.HEAD, null, null, "Ben"));
		ud.addUser(new User(susanuuid, "susan", "susan12", "Susan", "Super", "ss@gmail.com", henryuuid, 1000, UserType.SUPERVISOR, null, "Henry", "Ben"));
		
		ud.addUser(new User(UUID.randomUUID(), "mark", "mark12", "Mark", "Charles", "mc@gmail.com", susanuuid, 1000, UserType.EMPLOYEE, "Susan", "Henry", "Ben"));
		
		User u = new User(UUID.randomUUID(), "brian", "brian12", "Brian", "Tran", "bt@gmail.com", susanuuid,1000, UserType.EMPLOYEE, "Susan", "Henry", "Ben");
		System.out.println(u.toString());
		ud.addUser(u);
		
		Request r = new Request(UUID.randomUUID(), u.getId(), 500, 0, "Brian", "Tran", "bt@gmail.com", LocalDate.now(), LocalTime.now(), "Nutley", "Test Request", 500, LocalDate.of(9, 1, 2021), "Letter", "C", null, EventType.COURSE, null, "pending", null, false);
		rd.addRequest(u.getId(), r);
	}
	
	
}

