package com.solbeg.wallet.entity.base;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    void setId(T id);

    T getId();
}
