package cashbackservice.service;

import java.util.concurrent.ThreadLocalRandom;

public class CashbackService {
    public double getSum(String productPrice) {
        return ThreadLocalRandom.current().nextInt(8);
    }
}