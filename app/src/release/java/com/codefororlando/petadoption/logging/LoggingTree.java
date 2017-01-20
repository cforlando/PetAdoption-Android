package com.codefororlando.petadoption.logging;

import timber.log.Timber;

/**
 * Created by tencent on 10/10/16.
 */
public class LoggingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        // No op on release builds
    }

}
