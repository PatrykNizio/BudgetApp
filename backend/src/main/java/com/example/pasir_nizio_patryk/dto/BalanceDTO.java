package com.example.pasir_nizio_patryk.dto;

import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BalanceDTO{
    private double totalIncome;
    private double totalExpense;
    private double balance;

    public BalanceDTO(double totalIncome, double totalExpense, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
    }

}
