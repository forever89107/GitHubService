package com.my.github;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private String owner;
    private String new_owner;
    private String repo;


    public TransferRequest(String owner, String new_owner) {
        this.owner = owner;
        this.new_owner = new_owner;
    }
}
