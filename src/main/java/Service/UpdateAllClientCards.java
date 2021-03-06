package Service;
import Domain.Entity;
import Repository.IRepository;

import java.util.List;

    public class UpdateAllClientCards<T extends Entity> extends UndoRedoOperation {
        private List<T> updatedList;
        private List<T> actualList;

        UpdateAllClientCards(IRepository<T> repository, List<T> updatedList, List<T> actualList) {
            super(repository);
            this.updatedList = updatedList;
            this.actualList = actualList;
        }

        @Override
        public void doUndo() {
            for (T updatedEntity : actualList) {
                repository.upsert(updatedEntity);
            }
        }

        @Override
        public void doRedo() {
            for (T updatedEntity : updatedList) {
                repository.upsert(updatedEntity);
            }
        }
    }
