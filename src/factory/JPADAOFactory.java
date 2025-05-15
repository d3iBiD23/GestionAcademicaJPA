package factory;

import dao.AcademiaDAO;
import dao.jpa.AcademiaDAOImplJPA;

public class JPADAOFactory implements DAOFactory {
	@Override
    public AcademiaDAO getAcademiaDAO() {
        return new AcademiaDAOImplJPA();
    }
}
