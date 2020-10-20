package com.course_type.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CourseTypeJDBCDAO implements CourseTypeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "XDU";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO course_type (cstypeno, cstypename) VALUES (('TYPE' || LPAD(SEQ_EMPLOYEE.NEXTVAL, 4, 0)), ?)";
	private static final String GET_ALL_STMT = "SELECT cstypeno, cstypename FROM course_type";
	private static final String GET_ONE_STMT = "SELECT cstypeno, cstypename FROM course_type where cstypeno = ?";
	private static final String DELETE = "DELETE FROM course_type where cstypeno = ?";
	private static final String UPDATE = "UPDATE course_type set cstypename=? where cstypeno=? ";

	@Override
	public void insert(CourseTypeVO courseTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, courseTypeVO.getCstypename());
			
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
	public void update(CourseTypeVO courseTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, courseTypeVO.getCstypename());
			pstmt.setString(2, courseTypeVO.getCstypeno());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		}catch (SQLException e) {
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
	public void delete(String cstypeno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, cstypeno);

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
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public CourseTypeVO findByPrimaryKey(String cstypeno) {

		CourseTypeVO courseTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, cstypeno);

			rs = pstmt.executeQuery();
			con.commit();
			while (rs.next()) {
				courseTypeVO = new CourseTypeVO();
				courseTypeVO.setCstypeno(rs.getString("cstypeno"));
				courseTypeVO.setCstypename(rs.getString("cstypename"));

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}  catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}finally {

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
		return courseTypeVO;
	}

	@Override
	public List<CourseTypeVO> getAll() {

		List<CourseTypeVO> list = new ArrayList<CourseTypeVO>();
		CourseTypeVO courseTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			con.commit();
			while (rs.next()) {
				courseTypeVO = new CourseTypeVO();
				courseTypeVO.setCstypeno(rs.getString("cstypeno"));
				courseTypeVO.setCstypename(rs.getString("cstypename"));

				list.add(courseTypeVO);
			}

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

	public static void main(String[] args) {

		CourseTypeJDBCDAO dao = new CourseTypeJDBCDAO();

		// 新增
//		CourseTypeVO courseTypeVO1 = new CourseTypeVO();
//		courseTypeVO1.setCstypename("T006");
//
//		dao.insert(courseTypeVO1);
//		System.out.println("新增成功");

		// 修改
//		CourseTypeVO courseTypeVO2 = new CourseTypeVO();
//		courseTypeVO2.setCstypeno("TYPE0034");
//		courseTypeVO2.setCstypename("程式設計");
//		
//		dao.update(courseTypeVO2);
//		System.out.println("修改成功");

		// 刪除
//		dao.delete("TYPE0034");
//		System.out.println("刪除成功");

		// 查詢單一
//		CourseTypeVO courseTypeVO3 = dao.findByPrimaryKey("TYPE0001");
//		System.out.print(courseTypeVO3.getCstypeno() + ",");
//		System.out.print(courseTypeVO3.getCstypename());
		
		//查詢全部
		List<CourseTypeVO> list = dao.getAll();
		for (CourseTypeVO VO : list) {
			System.out.print(VO.getCstypeno()+ ",");
			System.out.print(VO.getCstypename());

			System.out.println();
		}
		
	}
}
