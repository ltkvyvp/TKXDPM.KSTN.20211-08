package accessor;

import java.util.List;

public interface DataAccessorInterface<T> {

    /**
     * Select query in the table of database (by id) corresponding to T
     * and save it in object
     * @param id
     * @return object entity which has type T
     */
    public T get(int id);

    /**
     * Select query in the table of database corresponding to T
     * and save in List T
     * @return all objects type T
     */
    public List<T> getAll();

    /**
     * Select table of database corresponding to T in database ,
     * update the sample which has id = t.id
     * @param t
     */
    public void update(T t);

    /**
     * Select table of database corresponding to T in database, insert attribute of t to this table
     * @param t
     */
    public void save(T t);

    /**
     * Select table of database corresponding to T in database, delete row which has id = t.id
     * @param id
     */
    public void delete(int id);

}
