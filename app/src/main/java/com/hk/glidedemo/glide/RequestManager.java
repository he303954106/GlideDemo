package com.hk.glidedemo.glide;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hk on 2019/5/25.
 */
public class RequestManager {

    private static RequestManager requestManager = new RequestManager();

    private RequestManager() {
        start();
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    private BitmapDispatcher[] bitmapDispatchers;

    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }
        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }

    public void start(){
        stop();
        startAllDispatcher();
    }

    private void startAllDispatcher() {
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    private void stop() {
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if (!bitmapDispatcher.isInterrupted()) {
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }
}
