package bytedance.com.jnidemo.utils;

import android.text.TextUtils;
import android.util.Log;

public class FaceDetectHelper {
    static {
        System.loadLibrary("native-lib");
    }

    private static FaceDetectHelper mHelper = null;
    private OnFaceDetectedCallback mFaceDetectedCallback = null;

    private FaceDetectHelper() {
        nativeInit();
    }

    public static FaceDetectHelper getHelper() {
        synchronized (FaceDetectHelper.class) {
            if (mHelper == null) {
                synchronized (FaceDetectHelper.class) {
                    if (mHelper == null) {
                        mHelper = new FaceDetectHelper();
                    }
                }
            }
        }
        return mHelper;
    }

    public void destroy() {
        nativeUnInit();
        mHelper = null;
    }

    public void setFaceDetectModelPath(String modelPath) {
        if (!TextUtils.isEmpty(modelPath)) {
            nativeSetFaceDetectModelPath(modelPath);
        }
    }

    public void setLicense(String license) {
        nativeSetLicense(license);
    }

    public void setFaceDetectedCallback(OnFaceDetectedCallback callback) {
        mFaceDetectedCallback = callback;
    }

    public void writeBMP() {
        nativeTestWriteBmp();
    }

    public interface OnFaceDetectedCallback {
        void onFaceDetected(int action,int top,int bottom,int left,int right);
    }

    public void detectFace(byte[] image, int pixelFormat, int width, int height, int stride) {
        nativeDetectFace(image, pixelFormat, width, height, stride);
    }

    public void onFaceDetectedCallback(int action,int top,int bottom,int left,int right) {
        if (mFaceDetectedCallback != null) {
            mFaceDetectedCallback.onFaceDetected(action,top,bottom,left,right);
        }
    }

    //TODO: add fact rect points through the params in the callback
    public static void nativeOnFaceDetectedCallback(int action,int top,int bottom,int left,int right) {
        Log.d("FaceDetectHelper", "JAVA detectFaceCallbackMethod ret : ");
        if (mHelper != null) {
            mHelper.onFaceDetectedCallback(action,top,bottom,left,right);
        }
    }

    private native void nativeSetLicense(String license);

    private native void nativeSetFaceDetectModelPath(String effectModePath);

    private native void nativeInit();

    private native void nativeUnInit();

    private native void nativeDetectFace(byte[] image, int pixelFormat, int width, int height, int stride);

    private native void nativeTestWriteBmp();
}
