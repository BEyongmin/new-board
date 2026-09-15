package com.board_2.board_2.exception;

public class PostNotFoundException extends RuntimeException  {
    public PostNotFoundException (String message){
        super(message);
    }
}
