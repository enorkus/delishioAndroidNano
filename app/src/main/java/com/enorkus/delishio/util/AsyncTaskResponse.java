package com.enorkus.delishio.util;

import java.util.List;

/**
 * Interface for moving AsyncTask output data to an Activity.
 */
public interface AsyncTaskResponse {
    void getAsyncResponseOnFinish(List<?> response);
}