package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class UserDaoImpl implements UserDao{
	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addUser(User u) {
		// TODO Auto-generated method stub
		String query = "Insert into project1.Employee (uid, username, password, firstname, lastname, email, availableAmount, type, directsupervisor, departmenthead, benco) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(u.getId(), u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getAvailableAmount(),
						u.getType(), u.getDirectSupervisor(), u.getDepartmentHead(), u.getBenCo());
		session.execute(bound);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		String query = "Select uid, username, password, firstname, lastname, email, availableAmount, type, directsupervisor, departmenthead, benco from Employee";
		// This query will not be particularly efficient as it needs to query every partition.
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<User> users = new ArrayList<>();
		rs.forEach(row -> {
			User u = new User();
			u.setId(row.getUuid("uid"));
			u.setUsername(row.getString("username"));
			u.setPassword(row.getString("password"));
			u.setFirstName(row.getString("firstname"));
			u.setLastName(row.getString("lastname"));
			u.setEmail(row.getString("email"));
			u.setAvailableAmount(row.getLong("availableAmount"));
			u.setType(UserType.valueOf(row.getString("type")));
			u.setDirectSupervisor(row.getString("directsupervisor"));
			u.setDepartmentHead(row.getString("departmenthead"));
			u.setBenCo(row.getString("benco"));
			users.add(u);
		});
		return users;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		String query = "Select uid, username, password, firstname, lastname, email, availableAmount, type, directsupervisor, departmenthead, benco from Employee where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		// ResultSet is the values returned by my query.
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			// if there is no return values
			return null;
		}
		User u = new User();
		u.setId(row.getUuid("uid"));
		u.setUsername(row.getString("username"));
		u.setPassword(row.getString("password"));
		u.setFirstName(row.getString("firstname"));
		u.setLastName(row.getString("lastname"));
		u.setEmail(row.getString("email"));
		u.setAvailableAmount(row.getLong("availableAmount"));
		u.setType(UserType.valueOf(row.getString("type")));
		u.setDirectSupervisor(row.getString("directsupervisor"));
		u.setDepartmentHead(row.getString("departmenthead"));
		u.setBenCo(row.getString("benco"));
		return u;
	}

	
}
