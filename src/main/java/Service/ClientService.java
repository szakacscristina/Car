package Service;

import Domain.Car;
import Domain.Client;
import Domain.Transaction;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ClientService {
    private IRepository<Client> clientRepository;

    private Stack<UndoRedoOperation<Car>> undoableOperations = new Stack<UndoRedoOperation<Car>>();
    private Stack<UndoRedoOperation<Car>> redoableeOperations = new Stack<UndoRedoOperation<Car>>();
    private Client client;

    public ClientService(IRepository<Client> clientRepository) {
        this.clientRepository = clientRepository;
    }


   /** public void searchFields(String[] terms){
        super.fullTextSearch(terms, repository.getAll());
    }**/

    /**
     *
     * @param cId
     * @param id
     * @param lastName
     * @param firstName
     * @param CNP
     * @param dateOfBirth
     * @param dateOfRegistration
     */
    public void addOrUpdate(String cId, String id, String lastName, String firstName, String CNP, String dateOfBirth, String dateOfRegistration) {
        Client existing = clientRepository.findById(id);
        if (existing != null) {
            // keep unchanged fields as they were
            if (lastName.isEmpty()) {
                lastName = existing.getLastName();
            }
            if (firstName.isEmpty()) {
                firstName = existing.getFirstName();
            }
            if (CNP.isEmpty()) {
                CNP = existing.getCNP();
            }
           /* if (dateOfBirth.isEmpty()) {
                dateOfBirth = existing.getDateOfBirth();
            }*/
            if (dateOfRegistration.isEmpty()) {
                dateOfRegistration = existing.getDateOfRegistration();
            }
            List<Client> CNPcheck = clientRepository.getAll();
            for (Client c : CNPcheck) {
                if (c.getCNP().equals(CNP)) {
                    throw new ValidatorExceptionService("error: existent CNP");
                }
            }
        clientRepository.upsert(client);
        undoableOperations.add(new AddOperation<Client>(clientRepository, client));
        redoableeOperations.clear();
    }
        Prim is = new Prim();
        if (!is.Prim(Integer.parseInt(id))){
            throw new ValidatorExceptionService("error: existent ID");

        }
    /**
     *
     * @param id
     */
    public void remove(String id){
            Client client = clientRepository.findById(id);
            clientRepository.remove(id);
            undoableOperations.add(new DeleteOperation<Client>(clientRepository, client));
            redoableeOperations.clear();
        }
    }

    /**
     *
     * @return
     */
    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public List<Client> getClients(){ return clientRepository.getAll();}

    public List<Client> fullTextSearch(String text) {
        List<Client> found = new ArrayList<Client>();
        for (Client c : clientRepository.getAll()) {
            if ((c.getId().contains(text) || c.getFirstName().contains(text) || c.getLastName().contains(text) ||
                    c.getCNP().contains(text) || c.getDateOfBirth().toString().contains(text) || c.getDateOfRegistration().toString().contains(text) || !found.contains(c))) {
                found.add(c);
            }
        }
        return found;
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Car> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);

        }
    }

    public void redo() {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Car> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    public List<Client> sortedClientsByDiscount() {
        Map<String Integer > assess = new HashMap<>();

        for (Transaction transaction : Transaction.getAll)

    }
}
