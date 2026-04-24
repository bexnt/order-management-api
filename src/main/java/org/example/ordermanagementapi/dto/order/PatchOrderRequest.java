package org.example.ordermanagementapi.dto.order;

import java.math.BigDecimal;

public class PatchOrderRequest {
    private String description;
    private BigDecimal amount;

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
