package yang.hong3.com.mymessage.bean.live;

import java.util.List;

/**
 * 分区
 * Created by hong3 on 2016/12/3.
 */

public class Partitions {


    /**
     * id : 9
     * name : 绘画专区
     * area : draw
     * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?2016120101","height":"63","width":"63"}
     * count : 133
     */

    private PartitionBean partition;
    /**
     * owner : {"face":"http://i1.hdslb.com/bfs/face/9c6cda4e0d0fcbe54817a4f0b455b15e1895b537.jpg","mid":28835613,"name":"蠢棉"}
     * cover : {"src":"http://i0.hdslb.com/bfs/live/e6bc1c8f08692d38838bf3667d6bf54276dcfcaa.jpg","height":180,"width":320}
     * title : 温柔up画渣一枚，陪你聊天~
     * room_id : 191313
     * check_version : 0
     * online : 230
     * area : 绘画专区
     * area_id : 9
     * playurl : http://live-play.acgvideo.com/live/893/live_28835613_9051980.flv?wsSecret=7fc3f9829be95c50f590e62f4972b736&wsTime=581aef35
     * accept_quality : 4
     * broadcast_type : 0
     * is_tv : 0
     */

    private List<LivesBean> lives;

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

    public static class PartitionBean {
        private int id;
        private String name;
        private String area;
        /**
         * src : http://static.hdslb.com/live-static/images/mobile/android/small/xxhdpi/9.png?2016120101
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

    public static class LivesBean extends LiveBean{

    }
}
