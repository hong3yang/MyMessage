package yang.hong3.com.mymessage.bean.near;

/**
 * Created by hong3 on 2017-1-22.
 */

public class NearBean {

    /**
     * id : 4875
     * name : 中石油青浦第二加油站
     * area : 201700
     * areaname : 上海市 青浦区
     * address : 上海市青浦区脸膛镇双菱村菱浜313号
     * brandname : 中石油
     * type : 直营店
     * discount : 打折加油站
     * exhaust : 沪Ⅴ
     * position : 121.016727,30.971745
     * lon : 121.02332973198
     * lat : 30.977390128707
     * price : {"E90":"5.33","E93":"5.71","E97":"6.08","E0":"5.3"}
     * fwlsmc : 银联卡,信用卡支付,加油卡
     * distance : 3360
     */

    private String id;
    private String name;
    private String area;
    private String areaname;
    private String address;
    private String brandname;
    private String type;
    private String discount;
    private String exhaust;
    private String position;
    private String lon;
    private String lat;
    /**
     * E90 : 5.33
     * E93 : 5.71
     * E97 : 6.08
     * E0 : 5.3
     */

    private PriceBean price;
    private String fwlsmc;
    private int distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getExhaust() {
        return exhaust;
    }

    public void setExhaust(String exhaust) {
        this.exhaust = exhaust;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public PriceBean getPrice() {
        return price;
    }

    public void setPrice(PriceBean price) {
        this.price = price;
    }

    public String getFwlsmc() {
        return fwlsmc;
    }

    public void setFwlsmc(String fwlsmc) {
        this.fwlsmc = fwlsmc;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public static class PriceBean {
        private String E90;
        private String E93;
        private String E97;
        private String E0;

        public String getE90() {
            return E90;
        }

        public void setE90(String E90) {
            this.E90 = E90;
        }

        public String getE93() {
            return E93;
        }

        public void setE93(String E93) {
            this.E93 = E93;
        }

        public String getE97() {
            return E97;
        }

        public void setE97(String E97) {
            this.E97 = E97;
        }

        public String getE0() {
            return E0;
        }

        public void setE0(String E0) {
            this.E0 = E0;
        }
    }
}
