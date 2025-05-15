package dao;

import java.util.Collection;

import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public interface AcademiaDAO {
	public Collection<Alumno> cargarAlumnos(); // SI

	public Alumno getAlumno(int idAlumno); // SI

	public int grabarAlumno(Alumno alumno); // SI

	public int actualizarAlumno(Alumno alumno); // SI

	public int borrarAlumno(int idAlumno); // SI

	public Collection<Curso> cargarCursos(); // SI

	public Curso getCurso(int idCurso); // NO

	public int grabarCurso(Curso curso); // SI

	public int actualizarCurso(Curso curso); // NO

	public int borrarCurso(int idCurso); // NO

	public Collection<Matricula> cargarMatriculas(); // SI

	public long getIdMatricula(int idAlumno, int idCurso); // NO

	public Matricula getMatricula(long idMatricula); // NO

	public int grabarMatricula(Matricula matricula); // SI

	public int actualizarMatricula(Matricula matricula); // NO

	public int borrarMatricula(long idMatricula);
}
