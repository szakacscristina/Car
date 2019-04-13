package Service;

import Domain.Car;
import Domain.Client;
import Domain.Transaction;
import Repository.IRepository;

import java.util.*;

public class TransactionService {

    private IRepository<Transaction> transactionIRepository;
    private IRepository<Car> carIRepository;
    private IRepository<Client> clientIRepository;

    private Stack<UndoRedoOperation<Transaction>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Transaction>> redoableeOperations = new Stack<>();


    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Car> carRepository, IRepository<Client> clientRepository) {
        this.transactionIRepository = transactionRepository;
        this.carIRepository = carRepository;
        this.clientIRepository = clientRepository;
    }

    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Car> carRepository) {
    }


    public double PaidPrice(String idCar, double pieceTotal, String idClientCard){
        // determine the price paid for this transaction
        List<Car> cars = carIRepository.getAll();

        for (Car car : cars){
            if (car.getId() == idCar){
                double initialPrice = car.getKilometers() * pieceTotal;
                if (idClientCard != null){
                    if (car.isInWarranty()) return initialPrice - (10.00/100 * initialPrice);
                    else return initialPrice - (10.00/100 * initialPrice);
                } else return initialPrice;
            }
        }
        throw new ValidatorExceptionService("Wrong Car ID ERROR: Found no Car for this transaction.");
    }


    /**
     * @param id
     * @param idCar
     * @param idClientCard
     * @param pieceTotal
     * @param date
     * @param time
     * @return
     */
    public Transaction addOrUpdate(String id, String idCar, String idClientCard, double pieceTotal, String date, String time, double workmanship, double discount) {
        Transaction existing = transactionIRepository.findById(id);
        if (existing != null) {
            // keep unchanged fields as they were
            if (idCar.isEmpty()) {
                idCar = existing.getIdCar();
            }
            if (idClientCard.isEmpty()) {
                idClientCard = existing.getIdClientCard();
            }
            if (pieceTotal == 0) {
                pieceTotal = existing.getPieceTotal();
            }
            if (date.isEmpty()) {
                date = existing.getDate();
            }
            if (time.isEmpty()) {
                time = existing.getTime();
            }

        }

        Car carSold = carIRepository.findById(idCar);
        if (carSold == null) {
            throw new RuntimeException("There is no car with the given id!");
        }
          workmanship = Double.parseDouble(carSold.getPaidPrice());
         discount = 0;
        if (idClientCard != null) {
            workmanship = 0.1; // 10% discount

            if (carSold.isInWarranty()) {
                pieceTotal = 0; // piesele sunt gratis
            }

            Transaction transaction = new Transaction(id, idCar, idClientCard, date, time, pieceTotal, workmanship, discount);
            transactionIRepository.upsert(transaction);
            return transaction;
        }


        return existing;
    }
    public List<Transaction> displayTransaction() {
        int count = 0;
        double avg=0;
        List<Transaction> transList = new ArrayList<>();
        for(Transaction t: transactionIRepository.getAll()){
            Car carSold = carIRepository.findById(t.getIdCar());
            avg += t.getDiscount()*t.getPieceTotal()*carSold.getPaidPrice();
            count ++;
        }
        avg = avg / count;

        for(Transaction t: transactionIRepository.getAll()){
            Car carSold = carIRepository.findById(t.getIdCar());
            if ( avg < t.getDiscount()*t.getPieceTotal()*carSold.getPaidPrice()){
                transList.add(t);
            }
        }
        return transList;
    }
    /**
     *
     * @param id
     */
    public void remove (String id){
        transactionIRepository.remove(id);
    }

    /**
     *
     * @return
     */
    public List<Transaction> getAll () {
        return transactionIRepository.getAll();
    }
    public List<Client> sortedClientsByDiscount() {
        Map<String,Integer> assess = new HashMap<>();

        for (Transaction transaction : transactionIRepository.getAll())
            if (assess.containsKey(clientIRepository.findById(transaction.getId()).getId())) {
                assess.put(clientIRepository.findById(transaction.getId()));
            }

    public List<Transaction> getAllTransactions(){return transactionIRepository.getAll();}


    public Transaction[] fullTextSearch(String text) {
    }
}