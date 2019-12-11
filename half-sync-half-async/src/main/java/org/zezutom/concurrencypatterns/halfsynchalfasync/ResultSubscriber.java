package org.zezutom.concurrencypatterns.halfsynchalfasync;


public interface ResultSubscriber {

    void onResult(boolean result);
}
