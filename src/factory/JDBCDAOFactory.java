package factory;

import dao.AcademiaDAO;
import dao.jdbc.AcademiaDAOImplJDBC;

public class JDBCDAOFactory implements DAOFactory{
	@Override
    public AcademiaDAO getAcademiaDAO() {
        return new AcademiaDAOImplJDBC();
    }
}
