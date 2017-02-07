package yang.hong3.com.mymessage.bean.live;

import java.util.List;

/**
 * 推荐
 * Created by hong3 on 2016/12/3.
 */

public class RecommendData {


    /**
     * id : 0
     * name : 推荐主播
     * area : hot
     * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?2016120101","height":"63","width":"63"}
     * count : 5560
     */

    private PartitionBean partition;
    /**
     * owner : {"face":"http://i1.hdslb.com/bfs/face/f6d3536c3004254174d8da34a1c261115f90e3c0.jpg","mid":67141,"name":"C酱です"}
     * cover : {"src":"http://i0.hdslb.com/bfs/live/5a38818e2b592b3ce95682e9d08716d3d2ac6d79.jpg","height":180,"width":320}
     * room_id : 47867
     * check_version : 0
     * online : 6887
     * area : 单机联机
     * area_id : 1
     * title : 【最终幻想15】 决战到天亮！
     * playurl : http://live-play.acgvideo.com/live/675/live_67141_7013132.flv?wsSecret=3679eb843b164215833e5db762253f2f&wsTime=581aef3e
     * accept_quality : 4
     * broadcast_type : 0
     * is_tv : 0
     */

    private List<LivesBean> lives;
    /**
     * owner : {"face":"http://i2.hdslb.com/bfs/face/bc3668a056a9d75aab745f88d5dd6d4ae995671f.jpg","mid":30698151,"name":"喵熊°"}
     * cover : {"src":"http://i0.hdslb.com/bfs/live/95d6193da552008d5283a6f7c2bbcdc170a38233.jpg","height":180,"width":320}
     * room_id : 234024
     * check_version : 0
     * online : 213
     * area : 手机直播
     * area_id : 11
     * title : 去不了cp得我默默噜猫
     * playurl : http://live-play.acgvideo.com/live/597/live_30698151_1605408.flv?wsSecret=2afc22a91a486c4d2387f11e3e97b7de&wsTime=581aef37
     * accept_quality : 4
     * broadcast_type : 1
     * is_tv : 0
     */

    private List<BannerDataBean> banner_data;

    public PartitionBean getPartition() {
        return partition;
    }

    public void setPartition(PartitionBean partition) {
        this.partition = partition;
    }

    public List<LivesBean> getLives() {
        return lives;
    }

    public void setLives(List<LivesBean> lives) {
        this.lives = lives;
    }

    public List<BannerDataBean> getBanner_data() {
        return banner_data;
    }

    public void setBanner_data(List<BannerDataBean> banner_data) {
        this.banner_data = banner_data;
    }

    public static class PartitionBean {
        private int id;
        private String name;
        private String area;
        /**
         * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/-1.png?2016120101
         * height : 63
         * width : 63
         */

        private SubIconBean sub_icon;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public SubIconBean getSub_icon() {
            return sub_icon;
        }

        public void setSub_icon(SubIconBean sub_icon) {
            this.sub_icon = sub_icon;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public static class SubIconBean {
            private String src;
            private String height;
            private String width;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }
    }

    public static class LivesBean extends LiveBean {
//        /**
//         * face : http://i1.hdslb.com/bfs/face/f6d3536c3004254174d8da34a1c261115f90e3c0.jpg
//         * mid : 67141
//         * name : C酱です
//         */
//
//        private OwnerBean owner;
//        /**
//         * src : http://i0.hdslb.com/bfs/live/5a38818e2b592b3ce95682e9d08716d3d2ac6d79.jpg
//         * height : 180
//         * width : 320
//         */
//
//        private CoverBean cover;
//        private int room_id;
//        private int check_version;
//        private int online;
//        private String area;
//        private int area_id;
//        private String title;
//        private String playurl;
//        private String accept_quality;
//        private int broadcast_type;
//        private int is_tv;
//
//        public OwnerBean getOwner() {
//            return owner;
//        }
//
//        public void setOwner(OwnerBean owner) {
//            this.owner = owner;
//        }
//
//        public CoverBean getCover() {
//            return cover;
//        }
//
//        public void setCover(CoverBean cover) {
//            this.cover = cover;
//        }
//
//        public int getRoom_id() {
//            return room_id;
//        }
//
//        public void setRoom_id(int room_id) {
//            this.room_id = room_id;
//        }
//
//        public int getCheck_version() {
//            return check_version;
//        }
//
//        public void setCheck_version(int check_version) {
//            this.check_version = check_version;
//        }
//
//        public int getOnline() {
//            return online;
//        }
//
//        public void setOnline(int online) {
//            this.online = online;
//        }
//
//        public String getArea() {
//            return area;
//        }
//
//        public void setArea(String area) {
//            this.area = area;
//        }
//
//        public int getArea_id() {
//            return area_id;
//        }
//
//        public void setArea_id(int area_id) {
//            this.area_id = area_id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getPlayurl() {
//            return playurl;
//        }
//
//        public void setPlayurl(String playurl) {
//            this.playurl = playurl;
//        }
//
//        public String getAccept_quality() {
//            return accept_quality;
//        }
//
//        public void setAccept_quality(String accept_quality) {
//            this.accept_quality = accept_quality;
//        }
//
//        public int getBroadcast_type() {
//            return broadcast_type;
//        }
//
//        public void setBroadcast_type(int broadcast_type) {
//            this.broadcast_type = broadcast_type;
//        }
//
//        public int getIs_tv() {
//            return is_tv;
//        }
//
//        public void setIs_tv(int is_tv) {
//            this.is_tv = is_tv;
//        }
//
//        public static class OwnerBean {
//            private String face;
//            private int mid;
//            private String name;
//
//            public String getFace() {
//                return face;
//            }
//
//            public void setFace(String face) {
//                this.face = face;
//            }
//
//            public int getMid() {
//                return mid;
//            }
//
//            public void setMid(int mid) {
//                this.mid = mid;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//        }
//
//        public static class CoverBean {
//            private String src;
//            private int height;
//            private int width;
//
//            public String getSrc() {
//                return src;
//            }
//
//            public void setSrc(String src) {
//                this.src = src;
//            }
//
//            public int getHeight() {
//                return height;
//            }
//
//            public void setHeight(int height) {
//                this.height = height;
//            }
//
//            public int getWidth() {
//                return width;
//            }
//
//            public void setWidth(int width) {
//                this.width = width;
//            }
//        }
//    }
    }


    public static class BannerDataBean {
        /**
         * face : http://i2.hdslb.com/bfs/face/bc3668a056a9d75aab745f88d5dd6d4ae995671f.jpg
         * mid : 30698151
         * name : 喵熊°
         */

        private OwnerBean owner;
        /**
         * src : http://i0.hdslb.com/bfs/live/95d6193da552008d5283a6f7c2bbcdc170a38233.jpg
         * height : 180
         * width : 320
         */

        private CoverBean cover;
        private int room_id;
        private int check_version;
        private int online;
        private String area;
        private int area_id;
        private String title;
        private String playurl;
        private String accept_quality;
        private int broadcast_type;
        private int is_tv;

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public CoverBean getCover() {
            return cover;
        }

        public void setCover(CoverBean cover) {
            this.cover = cover;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getCheck_version() {
            return check_version;
        }

        public void setCheck_version(int check_version) {
            this.check_version = check_version;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public String getAccept_quality() {
            return accept_quality;
        }

        public void setAccept_quality(String accept_quality) {
            this.accept_quality = accept_quality;
        }

        public int getBroadcast_type() {
            return broadcast_type;
        }

        public void setBroadcast_type(int broadcast_type) {
            this.broadcast_type = broadcast_type;
        }

        public int getIs_tv() {
            return is_tv;
        }

        public void setIs_tv(int is_tv) {
            this.is_tv = is_tv;
        }

        public static class OwnerBean {
            private String face;
            private int mid;
            private String name;

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CoverBean {
            private String src;
            private int height;
            private int width;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }
    }
}
