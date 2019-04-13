package Service;
import Domain.Entity;
import Repository.IRepository;

import java.util.List;

    public class DeleteAllTransactions<T extends Entity> extends UndoRedoOperation {
        private List<T> deletedList;

        DeleteAllTransactions(IRepository<T> repository, List<T> deletedList) {
            super(repository);
            this.deletedList = deletedList;
        }

        @Override
        public void doUndo() {
            for (T deletedEntity : deletedList) {
                repository.upsert(deletedEntity);
            }
        }

        @Override
        public void doRedo() {
            for (T deletedEntity : deletedList) {
                repository.remove(deletedEntity.getId());
            }
        }
    }


