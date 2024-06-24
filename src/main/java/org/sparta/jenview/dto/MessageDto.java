package org.sparta.jenview.dto;

import lombok.Getter;

@Getter
public class MessageDto {
    private String msg;

    public MessageDto(String msg) {
        this.msg = msg;
    }
}
