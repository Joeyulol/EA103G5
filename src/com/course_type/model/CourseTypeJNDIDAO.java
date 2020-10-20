package com.course_type.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CourseTypeJNDIDAO implements CourseTypeDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/xduDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, courseTypeVO.getCstypename());

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
	public void update(CourseTypeVO courseTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, courseTypeVO.getCstypename());
			pstmt.setString(2, courseTypeVO.getCstypeno());

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
	public void delete(String cstypeno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cstypeno);

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
	public CourseTypeVO findByPrimaryKey(String cstypeno) {

		CourseTypeVO courseTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cstypeno);

			rs = pstmt.executeQuery();			
			while (rs.next()) {
				courseTypeVO = new CourseTypeVO();
				courseTypeVO.setCstypeno(rs.getString("cstypeno"));
				courseTypeVO.setCstypename(rs.getString("cstypename"));
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
		}  finally {
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();			
			while (rs.next()) {
				courseTypeVO = new CourseTypeVO();
				courseTypeVO.setCstypeno(rs.getString("cstypeno"));
				courseTypeVO.setCstypename(rs.getString("cstypename"));
				list.add(courseTypeVO);
			}
			con.commit();
		}  catch (SQLException e) {
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
}
