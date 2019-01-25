package bytedance.com.chapter6.bean;

public class Video {
    private int imageId;
    private String videoPath;

    public Video(int imageId, String videoPath) {
        this.imageId = imageId;
        this.videoPath = videoPath;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public String toString() {
        return "Video{" +
                "imageId=" + imageId +
                ", videoPath='" + videoPath + '\'' +
                '}';
    }
}
