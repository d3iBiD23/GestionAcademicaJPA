package dao.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import dao.AcademiaDAO;
import entidades.Alumno;
import entidades.Curso;
import entidades.Matricula;

public class AcademiaDAOImplJPA implements AcademiaDAO {
    private EntityManagerFactory emf;
    
    public AcademiaDAOImplJPA() {
        emf = Persistence.createEntityManagerFactory("gestionacademia");
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Collection<Alumno> cargarAlumnos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Alumno getAlumno(int idAlumno) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, idAlumno);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarAlumno(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(alumno);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarAlumno(Alumno alumno) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(alumno);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarAlumno(int idAlumno) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Alumno alumno = em.find(Alumno.class, idAlumno);
            if (alumno != null) {
                em.remove(alumno);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Curso> cargarCursos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Curso getCurso(int idCurso) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, idCurso);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarCurso(Curso curso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarCurso(int idCurso) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Curso curso = em.find(Curso.class, idCurso);
            if (curso != null) {
                em.remove(curso);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Matricula> cargarMatriculas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Matricula m", Matricula.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long getIdMatricula(int idAlumno, int idCurso) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT m.id FROM Matricula m WHERE m.alumno.id = :idAlumno AND m.curso.id = :idCurso", 
                Long.class);
            query.setParameter("idAlumno", idAlumno);
            query.setParameter("idCurso", idCurso);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public Matricula getMatricula(long idMatricula) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, idMatricula);
        } finally {
            em.close();
        }
    }

    @Override
    public int grabarMatricula(Matricula matricula) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(matricula);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int actualizarMatricula(Matricula matricula) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(matricula);
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    @Override
    public int borrarMatricula(long idMatricula) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Matricula matricula = em.find(Matricula.class, idMatricula);
            if (matricula != null) {
                em.remove(matricula);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }
}