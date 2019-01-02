package com.addressbook.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneNumber {
    private final Type type;
    private final String number;

    enum Type {
        HOME, WORK, MOBILE
    }
}
