package entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_curso")
	private int idCurso;
	
	@Column(name = "nombre_curso")
	private String nombreCurso;

	public Curso() {
	}

	public Curso(int idCurso, String nombrecurso) {
		this.idCurso = idCurso;
		this.nombreCurso = nombrecurso;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	@Override
	public String toString() {
		return "Curso [idCurso=" + idCurso + ", nombreCurso=" + nombreCurso + "]";
	}
}
