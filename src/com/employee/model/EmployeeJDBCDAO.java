package com.employee.model;

import java.sql.*;
import java.util.*;


import com.emp_authority.model.EmpAuthorityVO;

public class EmployeeJDBCDAO implements EmployeeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "XDU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO employee (empno, empacc, emppwd, empname, empsalary, hiredate, empemail) VALUES (('EMP' || LPAD(SEQ_EMPLOYEE.NEXTVAL, 4, 0)), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT empno, empacc, empname, empsalary, to_char(hiredate,'yyyy-mm-dd') hiredate, empemail, empdelete FROM employee ";
	private static final String GET_ONE_STMT = "SELECT empno, empacc, empname, empsalary, to_char(hiredate,'yyyy-mm-dd') hiredate, empemail, empdelete FROM employee where empno = ?";
	private static final String DELETE = "DELETE FROM employee where empno = ?";
	private static final String UPDATE = "UPDATE employee set empacc=?, emppwd=?, empname=?, empsalary=?, hiredate=?, empemail=?, empdelete=? where empno=? ";
	private static final String UPDATESTATUS = "UPdate employee set empdelete? where empno=? ";
	private static final String LOGIN = "SELECT * FROM employee where empacc=? and emppwd=? ";

	@Override
	public void insert(EmployeeVO employeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, employeeVO.getEmpacc());
			pstmt.setString(2, employeeVO.getEmppwd());
			pstmt.setString(3, employeeVO.getEmpname());
			pstmt.setInt(4, employeeVO.getEmpsalary());
			pstmt.setDate(5, employeeVO.getHiredate());
			pstmt.setString(6, employeeVO.getEmpemail());

			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(EmployeeVO employeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, employeeVO.getEmpacc());
			pstmt.setString(2, employeeVO.getEmppwd());
			pstmt.setString(3, employeeVO.getEmpname());
			pstmt.setInt(4, employeeVO.getEmpsalary());
			pstmt.setDate(5, employeeVO.getHiredate());
			pstmt.setString(6, employeeVO.getEmpemail());
			pstmt.setInt(7, employeeVO.getEmpdelete());
			pstmt.setString(8, employeeVO.getEmpno());

			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String empno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, empno);
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void updateStatus(EmployeeVO employeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATESTATUS);

			pstmt.setInt(1, employeeVO.getEmpdelete());
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public EmployeeVO findByPrimaryKey(String empno) {

		EmployeeVO employeeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return employeeVO;
	}

	@Override
	public List<EmployeeVO> getAll() {
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		EmployeeVO employeeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
				list.add(employeeVO);
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
		
	@Override
	public EmployeeVO logIn(String empacc, String emppwd) {
		EmployeeVO employeeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, empacc);
			pstmt.setString(1, emppwd);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmppwd(rs.getString("emppwd"));
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return employeeVO;
	}
	
	@Override
	public void insertWithAhoriy(EmployeeVO employeeVO, List<EmpAuthorityVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String empno = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
		
			
			String cols[] = {"EMPNO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);			
			pstmt.setString(1, employeeVO.getEmpacc());
			pstmt.setString(2, employeeVO.getEmppwd());
			pstmt.setString(3, employeeVO.getEmpname());
			pstmt.setInt(4, employeeVO.getEmpsalary());
			pstmt.setDate(5, employeeVO.getHiredate());
			pstmt.setString(6, employeeVO.getEmpemail());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				empno = rs.getString(1);
				employeeVO.setEmpno(empno);
				System.out.println("自增主鍵" + empno);
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	public static void main(String[] args) {

		EmployeeJDBCDAO dao = new EmployeeJDBCDAO();
	

		// 新增
//		EmployeeVO employeeVO1 = new EmployeeVO();
//		employeeVO1.setEmpacc("T006");
//		employeeVO1.setEmppwd("123456");
//		employeeVO1.setEmpname("Joe");
//		employeeVO1.setEmpsalary(3000);
//		employeeVO1.setHiredate(java.sql.Date.valueOf("2020-09-25"));
//		employeeVO1.setEmpemail("123@gmail.com");
//		dao.insert(employeeVO1);
//		System.out.println("新增成功");

		// 修改
//		EmployeeVO employeeVO2 = new EmployeeVO();
//		employeeVO2.setEmpno("EMP0005");
//		employeeVO2.setEmpacc("B001");
//		employeeVO2.setEmppwd("123456");
//		employeeVO2.setEmpname("Mr.Brown");
//		employeeVO2.setEmpsalary(20000);
//		employeeVO2.setHiredate(java.sql.Date.valueOf("2020-08-25"));
//		employeeVO2.setEmpemail("987@gmail.com");
//		employeeVO2.setEmpdelete(0);
//		dao.update(employeeVO2);
//		System.out.println("修改成功");
//		
//		//刪除
//		dao.delete("EMP0005");
//		System.out.println("刪除成功");
//		
//		//查詢單一
		EmployeeVO employeeVO3 = dao.findByPrimaryKey("EMP0003");
		System.out.print(employeeVO3.getEmpno()+ ",");
		System.out.print(employeeVO3.getEmpacc()+ ",");
		System.out.print(employeeVO3.getEmpname()+ ",");
		System.out.print(employeeVO3.getEmpsalary()+ ",");
		System.out.print(employeeVO3.getHiredate()+ ",");
		System.out.print(employeeVO3.getEmpemail()+ ",");
		System.out.println(employeeVO3.getEmpdelete() );
//	
//		
//		//查詢全部
//		List<EmployeeVO> list = dao.getAll();
//		for (EmployeeVO VO : list) {
//			System.out.print(VO.getEmpno() + ",");
//			System.out.print(VO.getEmpacc() + ",");
//			System.out.print(VO.getEmpname() + ",");
//			System.out.print(VO.getEmpsalary() + ",");
//			System.out.print(VO.getHiredate() + ",");
//			System.out.print(VO.getEmpemail() + ",");
//			System.out.print(VO.getEmpdelete() + ",");
//			System.out.println();
//		}
		
	
	}

	@Override
	public void insertWithEmp(EmployeeVO employeeVO, List<EmpAuthorityVO> authlist) {
		// TODO Auto-generated method stub
		
	}


}
