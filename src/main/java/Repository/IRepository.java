
package Repository;

import Domain.Entity;

import java.util.List;

public interface IRepository <T extends Entity>{

    /**
     * returns an entity by id
     * @param id of the entity
     * @return a entity
     */

    T findById(String id);

    /**
     * updates a car
     * @param car entity to update
     * @throws RuntimeException in case a movie with that id does not exist
     */
    void upsert(T car);

    /**
     * removed a movie
     * @param id if the movie we want to remove
     * @throws  RuntimeException in case a movie with that id does not exist
     */
    void remove(String id);

    /**
     *
     * @return a list with all the entities
     */
    List<T> getAll();
}
