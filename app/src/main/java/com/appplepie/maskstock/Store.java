package com.appplepie.maskstock;

class Store {

    String code; // 식별코드
    String name; // 이름
    String addr; // 주소
    String type; // "판매처 유형[약국: '01', 우체국: '02', 농협: '03']"
    double lat;
    double lng;
    String stock_at;
    String remain_stat; //"재고 상태[100개 이상(녹색): 'plenty' / 30개 이상 100개미만(노랑색): 'some' / 2개 이상 30개 미만(빨강색): 'few' / 1개 이하(회색): 'empty']"
    String created_at;
}
