package com.appplepie.maskstock;

class Store {
    public Store(String code, String name, float lat, float lng, int stock_t, int stock_cnt, int sold_cnt, int remain_cnt, boolean sold_out, String created_at) {
        this.code = code;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.stock_t = stock_t;
        this.stock_cnt = stock_cnt;
        this.sold_cnt = sold_cnt;
        this.remain_cnt = remain_cnt;
        this.sold_out = sold_out;
        this.created_at = created_at;
    }

    private final String code;
    private final String name;
    private final float lat;
    private final float lng;
    private final int stock_t;
    private final int stock_cnt;
    private final int sold_cnt;
    private final int remain_cnt;
    private final boolean sold_out;
    private final String created_at;
}
