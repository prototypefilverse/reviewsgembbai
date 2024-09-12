package model;

import java.util.List;

import dao.MuttersDAO;

public class GetMutterListLogic {

    public List<Mutter> execute(int pageNumber, int pageSize) {
        MuttersDAO dao = new MuttersDAO();
        int offset = (pageNumber - 1) * pageSize;
        return dao.findAll(offset, pageSize);
    }

    public int getTotalMutters() {
        MuttersDAO dao = new MuttersDAO();
        return dao.countAll();
    }

}
