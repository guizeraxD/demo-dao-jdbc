package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TEST 1: dep findById =====");
		Department dep = departmentDao.findById(3);
		
		System.out.println(dep);
		
		System.out.println("\n=== TEST 2: dep findAll =====");
		List<Department> list = departmentDao.findAll();
		
		for(Department obj:list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: dep insert =====");

		Department newDep = new Department(null, "Games");
		departmentDao.insert(newDep);
		
		System.out.println("Inserted! New id = " + newDep.getId());
		
		System.out.println("\n=== TEST 4: dep update =====");
	
		Department dep2 = departmentDao.findById(7);
		dep2.setName("Music");
		departmentDao.update(dep2);
		System.out.println("Update completed!");
		
		System.out.println("\n=== TEST 5: dep delete =====");
		System.out.println("Enter Id for delete test:");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}

}
