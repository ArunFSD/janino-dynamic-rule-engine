package org.janino.service;

import org.springframework.stereotype.Service;

@Service
public class HardcodedDiscountService {

    public double calculate(double amount) {
        if (amount > 5000) {
            return amount * 0.10;
        }
        return 0;
    }
}
