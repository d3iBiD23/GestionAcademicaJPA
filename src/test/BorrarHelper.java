package test;

import java.util.Collection;

import dao.AcademiaDAO;
import dao.DAOFactoryProvider;
import dao.jdbc.AcademiaDAOImplJDBC;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class BorrarHelper {

	private AcademiaDAO dao;

	// Constructor: crea el DAO
	public BorrarHelper() {
		System.out.println("Creando el DAO...");
		dao = DAOFactoryProvider.getDAOFactory().getAcademiaDAO();
	}

	// Borra todas las matrículas existentes
	private void borrarMatriculas() {
		System.out.println("Borrando cualquier matricula previa...");
		Collection<Matricula> matriculas = dao.cargarMatriculas();
		for (Matricula m : matriculas) {
			if (dao.borrarMatricula(m.getIdMatricula()) == 1) {
				System.out.println("Se ha borrado la matricula");
			} else {
				System.out.println("Error al borrar la matricula");
			}
		}
	}

	// Borra todos los alumnos existentes
	private void borrarAlumnos() {
		System.out.println("Borrando cualquier alumno previo...");
		Collection<Alumno> alumnos = dao.cargarAlumnos();
		for (Alumno a : alumnos) {
			if (dao.borrarAlumno(a.getIdAlumno()) == 1) {
				System.out.println("Se ha borrado el alumno");
			} else {
				System.out.println("Error al borrar el alumno");
			}
		}
	}

	// Borra todos los cursos existentes
	private void borrarCursos() {
		System.out.println("Borrando cualquier curso previo...");
		Collection<Curso> cursos = dao.cargarCursos();
		for (Curso c : cursos) {
			if (dao.borrarCurso(c.getIdCurso()) == 1) {
				System.out.println("Se ha borrado el curso");
			} else {
				System.out.println("Error al borrar el curso");
			}
		}
	}

	public static void main(String[] args) {
		BorrarHelper helper = new BorrarHelper();
		helper.borrarMatriculas();
		helper.borrarAlumnos();
		helper.borrarCursos();
		System.out.println("\nfin del programa.");
	}
}