package dao.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import dao.AcademiaDAO;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaDAOImplJDBC implements AcademiaDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/dbformacion";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	private static final String FIND_ALL_ALUMNOS_SQL = "select id_alumno, nombre_alumno, foto from alumnos";

	private static final String ADD_ALUMNO_SQL = "insert into alumnos" + " (id_alumno, nombre_alumno, foto) "
			+ " values (?,?,?) ";

	private static final String UPDATE_ALUMNO_SQL = "update alumnos set nombre_alumno=?, foto=? "
			+ " where id_alumno=? ";

	private static final String GET_ALUMNO_SQL = " select id_alumno, nombre_alumno, foto " + " from alumnos "
			+ " where id_alumno = ?";

	private void releaseConnection(Connection con) {
		if (con != null) {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Collection<Alumno> cargarAlumnos() {
		Collection<Alumno> alumnos = new ArrayList<Alumno>();
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(FIND_ALL_ALUMNOS_SQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String nombre = (rs.getString(2) != null ? rs.getString(2) : "sin nombre");
				Blob foto = rs.getBlob(3);

				Alumno alumno = new Alumno(id, nombre);

				if (foto != null)
					alumno.setFoto(foto.getBytes(1L, (int) foto.length()));
				else
					alumno.setFoto(null);

				alumnos.add(alumno);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			for (Throwable t : e) {
				System.err.println("Error: " + t);
			}
		} finally {
			releaseConnection(con);
		}
		return alumnos;
	}

	@Override
	public Alumno getAlumno(int idAlumno) {
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(GET_ALUMNO_SQL);
			ps.setInt(1, idAlumno);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id_Alumno = rs.getInt(1);

				String nombreAlumno = (rs.getString(2) != null ? rs.getString(2) : "sin nombre");
				Blob foto = rs.getBlob(3);
				Alumno alumno = new Alumno(id_Alumno, nombreAlumno);
				if (foto != null)
					alumno.setFoto(foto.getBytes(1L, (int) foto.length()));
				else
					alumno.setFoto(null);
				return alumno;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			for (Throwable t : e) {
				System.err.println("Errores: " + t);
			}
		} finally {
			releaseConnection(con);
		}
		return null;
	}

	@Override
	public int grabarAlumno(Alumno alumno) {
		int retorno = 0;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(ADD_ALUMNO_SQL);
			ps.setInt(1, alumno.getIdAlumno());
			ps.setString(2, alumno.getNombreAlumno());

			if (alumno.getFoto() != null) {
				ps.setBinaryStream(3, new ByteArrayInputStream(alumno.getFoto()));
			} else {
				ps.setBinaryStream(3, null);
			}

			retorno = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(con);
		}
		return retorno;
	}

	@Override
	public int actualizarAlumno(Alumno alumno) {
		int retorno = 0;
		Connection con = null;
		try {
			con = getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_ALUMNO_SQL);
			ps.setString(1, alumno.getNombreAlumno());

			if (alumno.getFoto() != null) {
				ps.setBinaryStream(2, new ByteArrayInputStream(alumno.getFoto()));
			} else {
				ps.setBinaryStream(2, null);
			}

			ps.setInt(3, alumno.getIdAlumno());
			retorno = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConnection(con);
		}
		return retorno;
	}

	@Override
	public int borrarAlumno(int idAlumno) {
		String query = "DELETE FROM alumnos WHERE id_alumno = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idAlumno);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Collection<Curso> cargarCursos() {
		Collection<Curso> cursos = new ArrayList<>();
		String query = "SELECT * FROM cursos";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				cursos.add(new Curso(rs.getInt("id_curso"), rs.getString("nombre_curso")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cursos;
	}

	@Override
	public int grabarCurso(Curso curso) {
		String query = "INSERT INTO cursos (id_curso, nombre_curso) VALUES (?, ?)";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, curso.getIdCurso());
			stmt.setString(2, curso.getNombreCurso());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Collection<Matricula> cargarMatriculas() {
		Collection<Matricula> matriculas = new ArrayList<>();
		String query = "SELECT * FROM matriculas";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				matriculas.add(new Matricula(rs.getInt("id_matricula"), rs.getInt("id_alumno"), rs.getInt("id_curso"),
						rs.getDate("fecha_inicio")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return matriculas;
	}

	@Override
	public int grabarMatricula(Matricula matricula) {
		String query = "INSERT INTO matriculas (id_matricula, id_alumno, id_curso, fecha_inicio) VALUES (?, ?, ?, ?)";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, matricula.getIdMatricula());
			stmt.setInt(2, matricula.getIdAlumno());
			stmt.setInt(3, matricula.getIdCurso());
			stmt.setDate(4, matricula.getFechaInicio());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int actualizarCurso(Curso curso) {
		String query = "UPDATE cursos SET nombre_curso = ? WHERE id_curso = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, curso.getNombreCurso());
			stmt.setInt(2, curso.getIdCurso());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int borrarCurso(int idCurso) {
		String query = "DELETE FROM cursos WHERE id_curso = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idCurso);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long getIdMatricula(int idAlumno, int idCurso) {
		String query = "SELECT id_matricula FROM matriculas WHERE id_alumno = ? AND id_curso = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idAlumno);
			stmt.setInt(2, idCurso);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getLong("id_matricula");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Matricula getMatricula(long idMatricula) {
		String query = "SELECT * FROM matriculas WHERE id_matricula = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setLong(1, idMatricula);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Matricula(rs.getInt("id_matricula"), rs.getInt("id_alumno"), rs.getInt("id_curso"),
						rs.getDate("fecha_inicio"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int actualizarMatricula(Matricula matricula) {
		String query = "UPDATE matriculas SET id_alumno = ?, id_curso = ?, fecha_inicio = ? WHERE id_matricula = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, matricula.getIdAlumno());
			stmt.setInt(2, matricula.getIdCurso());
			stmt.setDate(3, matricula.getFechaInicio());
			stmt.setInt(4, matricula.getIdMatricula());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Curso getCurso(int idCurso) {
		String query = "SELECT * FROM cursos WHERE id_curso = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, idCurso);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Curso(rs.getInt("id_curso"), rs.getString("nombre_curso"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int borrarMatricula(long idMatricula) {
		String query = "DELETE FROM matriculas WHERE id_matricula = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setLong(1, idMatricula);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}