package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn=conn;
	}
	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"insert into department "
					+ "(Name) "
					+ "Values "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
		
		
			st.setString(1, obj.getName());
				
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"update department "
					+ "Set Name = ? "
					+ "Where Id = ?");
					
		
		
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("Delete from department where Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select * "
					+ "from department "
					+ "where department.Id = ?");
					
			st.setInt(1, id);			 //prepara o interrogaçao p receber o id
			rs = st.executeQuery();			//executa o st e cai o resultado no rs
			
			
			//rs começa na posicao 0 q é null
			if(rs.next()) {		//verifica se tem proxima posicao
				Department obj = new Department();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				return obj;
			}
			
			return null;
			
		}catch(SQLException e) {
			throw new DbException (e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select *from department "
					+ "order by Name");
					
			rs = st.executeQuery();						//executa o st e cai o resultado no rs
			
			List<Department> list = new ArrayList<>();
			
			//percorre rs enquanto tem um proximo
			while(rs.next()) {		
				Department obj = new Department();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				list.add(obj);
				
			}
			
			return list;
			
		}catch(SQLException e) {
			throw new DbException (e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
