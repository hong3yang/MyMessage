package yang.hong3.com.mymessage.bean.music;

/**
 * Created by hong3 on 2017-1-16.
 */

public class MusicInfoBean {


    /**
     * fileName : (0)
     * url : http://fs.web.kugou.com/0e8a1d12a4e223ec227f479bf3dcf16a/587c8c07/G008/M00/1B/1B/SA0DAFULHj-AAuyoABEiEfMP5gM675.m4a
     * fileSize : 1122833
     * status : 1
     * extName : m4a
     * bitRate : 32000
     * timeLength : 275
     */

    private String fileName;
    private String url;
    private int fileSize;
    private int status;
    private String extName;
    private int bitRate;
    private int timeLength;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(int timeLength) {
        this.timeLength = timeLength;
    }
}
