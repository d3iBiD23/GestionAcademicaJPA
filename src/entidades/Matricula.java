package entidades;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_matricula")
	private long idMatricula;
	
    @Column(name = "id_alumno")
	private int idAlumno;
    
    @Column(name = "id_curso")
	private int idCurso;
    
    @Column(name = "fecha_inicio")
	private Date fechaInicio;

	public Matricula() {
	}

	public Matricula(int idMatricula, int idAlumno, int idCurso, Date fechaInicio) {
		this.idMatricula = idMatricula;
		this.idAlumno = idAlumno;
		this.idCurso = idCurso;
		this.fechaInicio = fechaInicio;
	}

	public long getIdMatricula() {
		return idMatricula;
	}

	public int getIdAlumno() {
		return idAlumno;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Override
	public String toString() {
		return "Matricula [idMatricula=" + idMatricula + ", idAlumno=" + idAlumno + ", idCurso=" + idCurso
				+ ", fechaInicio=" + fechaInicio + "]";
	}

}
