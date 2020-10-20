package com.employee.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.emp_authority.model.EmpAuthorityJNDIDAO;
import com.emp_authority.model.EmpAuthorityVO;
import com.functionx.model.FunctionxJNDIDAO;
import com.functionx.model.FunctionxVO;

public class EmployeeJNDIDAO implements EmployeeDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/xduDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO employee (empno, empacc, emppwd, empname, empsalary, hiredate, empemail) VALUES (('EMP' || LPAD(SEQ_EMPLOYEE.NEXTVAL, 4, 0)), ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT empno, empacc, emppwd, empname, empsalary, to_char(hiredate,'yyyy-mm-dd') hiredate, empemail, empdelete FROM employee order by empno ";
	private static final String GET_ONE_STMT = "SELECT empno, empacc, emppwd, empname, empsalary, to_char(hiredate,'yyyy-mm-dd') hiredate, empemail, empdelete FROM employee where empno = ?";
	private static final String DELETE = "DELETE FROM employee where empno = ?";
	private static final String UPDATE = "UPDATE employee set empacc=?, emppwd=?, empname=?, empsalary=?, hiredate=?, empemail=?, empdelete=? where empno=? ";
	private static final String UPDATESTATUS = "UPDATE employee set empdelete? where empno=? ";
	private static final String LOGIN = "SELECT * FROM employee where empacc=? and emppwd=? ";
	private static final String GET_ONE_ACC = "SELECT * FROM employee where empacc = ?";
	
	

	@Override
	public void insert(EmployeeVO employeeVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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
		} catch (SQLException e) {
			try {
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

			con = ds.getConnection();
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
		} catch (SQLException e) {
			try {
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

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, empno);
			pstmt.executeUpdate();
			con.commit();
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATESTATUS);

			pstmt.setInt(1, employeeVO.getEmpdelete());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally{
			
		}

	}

	@Override
	public EmployeeVO findByPrimaryKey(String empno) {

		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, empno);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmppwd(rs.getString("emppwd"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
			}
			con.commit();
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmppwd(rs.getString("emppwd"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
				list.add(employeeVO);
			}
			con.commit();
		} catch (SQLException e) {
			try {
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(LOGIN);
			
			pstmt.setString(1,empacc);
			pstmt.setString(2,emppwd);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmppwd(rs.getString("emppwd"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
				
			}
			con.commit();

		} catch (SQLException e) {
			try {
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
	public String insertWithEmp(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String empno = null;
		try {

			con = ds.getConnection();
			
			String cols[] = {"empno"};
			
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
				System.out.println("自增主鍵" + empno);
			}
			System.out.println("新增完成");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException se) {
				System.out.println("SQLE");
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
		return empno;
	}

	@Override
	public EmployeeVO getOneAcc(String empacc) {
		
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_ACC);

			pstmt.setString(1, empacc);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpno(rs.getString("empno"));
				employeeVO.setEmpacc(rs.getString("empacc"));
				employeeVO.setEmppwd(rs.getString("emppwd"));
				employeeVO.setEmpname(rs.getString("empname"));
				employeeVO.setEmpsalary(rs.getInt("empsalary"));
				employeeVO.setHiredate(rs.getDate("hiredate"));
				employeeVO.setEmpemail(rs.getString("empemail"));
				employeeVO.setEmpdelete(rs.getInt("empdelete"));
			}
			con.commit();
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
	
}
