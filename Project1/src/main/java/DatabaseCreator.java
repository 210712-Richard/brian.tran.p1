
import java.time.LocalDate;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
				.append("uid uuid PRIMARY KEY, username text, password text, firstname text, lastname text, email text, ")
				.append("availableAmount float, type text, directsupervisor text, departmenthead text, benco text );");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
//		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS Reimbursement (")
//				.append("id uuid PRIMARY KEY, createDate timestamp, empid int, date date, time time, location text, description text, cost float, gradingFormat text, ")
//				.append("type text, attachmentUrl text, outFromDate timestamp, outToDate timestamp, exceeded boolean, status text, reason text, approvedBy int );");
//		CassandraUtil.getInstance().getSession().execute(sb.toString());
//		
	}
	
	public static void populateEmployeeTable() {
		log.trace("Populate Tables Called");
		User u = new User(UUID.randomUUID(), "brian", "brian12", "Brian", "Tran", 1000, UserType.EMPLOYEE, "Susan", "Henry", "Ben");
		System.out.println(u.toString());
		ud.addUser(u);
		ud.addUser(new User(UUID.randomUUID(), "mark", "mark12", "Mark", "Charles", 1000, UserType.EMPLOYEE, "Susan", "Henry", "Ben"));
		ud.addUser(new User(UUID.randomUUID(), "susan", "susan12", "Susan", "Super", 1000, UserType.SUPERVISOR, null, "Henry", "Ben"));
		ud.addUser(new User(UUID.randomUUID(), "henry", "henry12", "Henry", "Head", 1000, UserType.HEAD, null, null, "Ben"));
		ud.addUser(new User(UUID.randomUUID(), "ben", "ben12", "Ben", "Co", 1000, UserType.BENCO, null, null, null));
	}

	
}
