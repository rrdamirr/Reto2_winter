package com.banana.bananawhatsapp.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageException extends RuntimeException {
    public MessageException(String message) {
        super(message);
    }
}
