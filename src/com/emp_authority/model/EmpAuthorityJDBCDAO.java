package com.emp_authority.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.employee.model.EmployeeVO;

public class EmpAuthorityJDBCDAO implements EmpAuthorityDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "XDU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Emp_Authority (empno, funcno) VALUES ( ?, ?)";
	private static final String DELETE = "DELETE FROM  Emp_Authority where empno=?";

	@Override
	public void insert(EmpAuthorityVO empAuthorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, empAuthorityVO.getEmpno());
			pstmt.setString(2, empAuthorityVO.getFuncno());

			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, empno);

			pstmt.executeUpdate();
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

		EmpAuthorityJDBCDAO dao = new EmpAuthorityJDBCDAO();

		// 新增
//		EmpAuthorityVO empAuthorityVO1 = new EmpAuthorityVO();
//		empAuthorityVO1.setEmpno("EMP0011");
//		empAuthorityVO1.setFuncno("FUN0002");
//		
		// 刪除
		dao.delete("EMP0011");
		System.out.println("刪除成功");

	}

	@Override
	public List<EmpAuthorityVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  List<EmpAuthorityVO> findByEmp(String empno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert2(EmpAuthorityVO empAuthorityVO, Connection con) {
		// TODO Auto-generated method stub
		
	}
}
