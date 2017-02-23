package com.moovby.kit.bluetooth;

public interface Filter {

    /**
     * Check if a given response should be published.
     *
     * @param response a given response
     * @return if the response should be published
     */
    boolean isCorrect(String response);

}
