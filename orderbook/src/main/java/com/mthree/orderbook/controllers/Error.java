package com.mthree.orderbook.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
}
