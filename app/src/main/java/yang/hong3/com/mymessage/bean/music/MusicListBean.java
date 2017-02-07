package yang.hong3.com.mymessage.bean.music;

import java.util.List;

/**
 * Created by hong3 on 2017-1-13.
 */

public class MusicListBean {


    /**
     * filename : 梁朝伟、李宇春 - 十年【《摆渡人》电影岁月版主题曲】
     * songname : 十年
     * m4afilesize : 1037121
     * 320hash : 0ec5b3ed36d4c6914a42bd78d06f72db
     * mvhash : 34a3f970acd2ab4342756c065f3397bc
     * privilege : 0
     * filesize : 4039062
     * source :
     * bitrate : 128
     * ownercount : 236472
     * othername :
     * sqhash : b0f25e338912997951736865215a1df7
     * topic : 《摆渡人》电影岁月版主题曲
     * 320filesize : 10098938
     * isnew : 1
     * duration : 252
     * album_id : 1942595
     * hash : f046654f74243f0599329bb69af73927
     * singername : 梁朝伟、李宇春
     * sqfilesize : 25111254
     * 320privilege : 0
     * sourceid : 0
     * group : []
     * srctype : 1
     * extname : mp3
     * Accompany : 0
     * sqprivilege : 0
     * album_name : 十年
     * feetype : 0
     */

    private String filename;
    private String songname;
    private int m4afilesize;

    private String hash320;
    private String mvhash;
    private int privilege;
    private int filesize;
    private String source;
    private int bitrate;
    private int ownercount;
    private String othername;
    private String sqhash;
    private String topic;
    private int filesize320;
    private int isnew;
    private int duration;
    private String album_id;
    private String hash;
    private String singername;
    private int sqfilesize;
    private int privilege320;
    private int sourceid;
    private int srctype;
    private String extname;
    private int Accompany;
    private int sqprivilege;
    private String album_name;
    private int feetype;
    private List<?> group;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFilesize320() {
        return filesize320;
    }

    public void setFilesize320(int filesize320) {
        this.filesize320 = filesize320;
    }

    public String getHash320() {
        return hash320;
    }

    public void setHash320(String hash320) {
        this.hash320 = hash320;
    }

    public int getPrivilege320() {
        return privilege320;
    }

    public void setPrivilege320(int privilege320) {
        this.privilege320 = privilege320;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public int getM4afilesize() {
        return m4afilesize;
    }

    public void setM4afilesize(int m4afilesize) {
        this.m4afilesize = m4afilesize;
    }


    public String getMvhash() {
        return mvhash;
    }

    public void setMvhash(String mvhash) {
        this.mvhash = mvhash;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getOwnercount() {
        return ownercount;
    }

    public void setOwnercount(int ownercount) {
        this.ownercount = ownercount;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getSqhash() {
        return sqhash;
    }

    public void setSqhash(String sqhash) {
        this.sqhash = sqhash;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }



    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public int getSqfilesize() {
        return sqfilesize;
    }

    public void setSqfilesize(int sqfilesize) {
        this.sqfilesize = sqfilesize;
    }


    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public int getSrctype() {
        return srctype;
    }

    public void setSrctype(int srctype) {
        this.srctype = srctype;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public int getAccompany() {
        return Accompany;
    }

    public void setAccompany(int Accompany) {
        this.Accompany = Accompany;
    }

    public int getSqprivilege() {
        return sqprivilege;
    }

    public void setSqprivilege(int sqprivilege) {
        this.sqprivilege = sqprivilege;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getFeetype() {
        return feetype;
    }

    public void setFeetype(int feetype) {
        this.feetype = feetype;
    }

    public List<?> getGroup() {
        return group;
    }

    public void setGroup(List<?> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "MusicListBean{" +
                "Accompany=" + Accompany +
                ", filename='" + filename + '\'' +
                ", songname='" + songname + '\'' +
                ", m4afilesize=" + m4afilesize +
                ", hash320='" + hash320 + '\'' +
                ", mvhash='" + mvhash + '\'' +
                ", privilege=" + privilege +
                ", filesize=" + filesize +
                ", source='" + source + '\'' +
                ", bitrate=" + bitrate +
                ", ownercount=" + ownercount +
                ", othername='" + othername + '\'' +
                ", sqhash='" + sqhash + '\'' +
                ", topic='" + topic + '\'' +
                ", filesize320=" + filesize320 +
                ", isnew=" + isnew +
                ", duration=" + duration +
                ", album_id='" + album_id + '\'' +
                ", hash='" + hash + '\'' +
                ", singername='" + singername + '\'' +
                ", sqfilesize=" + sqfilesize +
                ", privilege320=" + privilege320 +
                ", sourceid=" + sourceid +
                ", srctype=" + srctype +
                ", extname='" + extname + '\'' +
                ", sqprivilege=" + sqprivilege +
                ", album_name='" + album_name + '\'' +
                ", feetype=" + feetype +
                ", group=" + group +
                '}';
    }
}
