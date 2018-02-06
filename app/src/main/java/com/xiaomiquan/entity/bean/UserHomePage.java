package com.xiaomiquan.entity.bean;

import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/6 0006.
 */

public class UserHomePage {


    /**
     * groupVos : [{"articleTopicVos":[],"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/ef82fb59060eb568b8f6f1e01c7a1234.jpg","brief":"没有什么","chargeMoney":0,"createTime":1517840236000,"email":"","groupMaster":false,"groupNum":"20180205221715","id":2,"isBanned":false,"isFree":true,"memberCount":2,"name":"币圈神探","nickName":"王念龙1","ownerAvatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/54268191c44a54b9cb39f80020eb6fba.jpg","phone":"17621364304","status":1,"type":"科技","updateTime":null,"userId":4},{"articleTopicVos":[],"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/c46e4e6bebf57656160247f9ce9ff644.jpg","brief":"Quanzi","chargeMoney":0,"createTime":1517837515000,"email":"","groupMaster":false,"groupNum":"20180205213155","id":1,"isBanned":false,"isFree":true,"memberCount":2,"name":"wangkedequanzi","nickName":"btc868isB","ownerAvatar":"36.png","phone":"15139464819","status":1,"type":"科技","updateTime":null,"userId":2}]
     * attentionCount : 0
     * fansCount : 0
     * articleTopicVos : [{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"莫毅","createTime":1517884274000,"createTimeStr":"5min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":49,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/2f854470716a7943f0c2959c875daa38.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/5e14c06fd1ab0298e5a6e00ced584884.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/e24500c9e237616cd6ea05a1a43babec.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/7ed49199b5fdf4fc512ae069614403e4.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/881c108c8b934857e43f4ed65561fd07.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/aa33c4632f604831776d4d66982e3034.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/876b7f155db907fa49a7e213b562e024.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/762a040047669e18c293b0c68a101f72.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/d639bbf1a71c851d7c70019b7bba1a77.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/2f854470716a7943f0c2959c875daa38.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/5e14c06fd1ab0298e5a6e00ced584884.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/e24500c9e237616cd6ea05a1a43babec.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/7ed49199b5fdf4fc512ae069614403e4.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/881c108c8b934857e43f4ed65561fd07.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/aa33c4632f604831776d4d66982e3034.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/876b7f155db907fa49a7e213b562e024.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/762a040047669e18c293b0c68a101f72.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/d639bbf1a71c851d7c70019b7bba1a77.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"懂","createTime":1517884215000,"createTimeStr":"6min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":48,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/69fe4127e0e571b79268fe15216b4f36.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/36fabd02e0339ec4cb878ce4423d5ba0.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/4fb221489bde681207ecb25c4e1b8f2b.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/69fe4127e0e571b79268fe15216b4f36.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/36fabd02e0339ec4cb878ce4423d5ba0.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/4fb221489bde681207ecb25c4e1b8f2b.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883952000,"createTimeStr":"10min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":47,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/08d5a5c65e5ab14d518e0aa0a80f4f07.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/08d5a5c65e5ab14d518e0aa0a80f4f07.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"啦啦啦","createTime":1517883904000,"createTimeStr":"11min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":46,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/04ec47df93965387c41e96a16fcd55e1.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/04ec47df93965387c41e96a16fcd55e1.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883861000,"createTimeStr":"12min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":45,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/eb42ea3dcd60c9d947be8fcfae2e0f7a.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/84efc5d986887cb723b7023064f2bb12.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/eb42ea3dcd60c9d947be8fcfae2e0f7a.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/84efc5d986887cb723b7023064f2bb12.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883674000,"createTimeStr":"15min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":44,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/36cc6a321e949f2e5eee65796f31fd17.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/36cc6a321e949f2e5eee65796f31fd17.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883665000,"createTimeStr":"15min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":43,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/41207cdb32d75093d9e7648e0877355d.JPEG","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/41207cdb32d75093d9e7648e0877355d.JPEG"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883660000,"createTimeStr":"15min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":42,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/33f7bf82e63bc8fa1942a240f5483001.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/634cc6b291ee37f607010d5eb049fb79.JPEG","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/33f7bf82e63bc8fa1942a240f5483001.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/634cc6b291ee37f607010d5eb049fb79.JPEG"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883655000,"createTimeStr":"15min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":41,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/4fbd829153560cf354f6f0e0f2c9340b.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/82cde0ecdd2cdf2cb81d8a9a3b41159d.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/f0b1f88601750b689cfcd92d869de2ec.JPEG","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/4fbd829153560cf354f6f0e0f2c9340b.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/82cde0ecdd2cdf2cb81d8a9a3b41159d.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/f0b1f88601750b689cfcd92d869de2ec.JPEG"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883643000,"createTimeStr":"15min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":40,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/b0d130926b1c6d863c0e601f6da5a3fb.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/ca1ee1feabc75af7267439e608d21b25.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/6efa629fcf24ca0f5e569707d611637b.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/3e1152d30e1f6f2d7fbae9942961f7bd.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/b0d130926b1c6d863c0e601f6da5a3fb.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/ca1ee1feabc75af7267439e608d21b25.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/6efa629fcf24ca0f5e569707d611637b.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/3e1152d30e1f6f2d7fbae9942961f7bd.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883563000,"createTimeStr":"17min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":39,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/8c0f7f090e4c15d192138d68ac49da35.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/8c0f7f090e4c15d192138d68ac49da35.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿里郎","createTime":1517883528000,"createTimeStr":"17min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":38,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/61ee13a03b25d0788d8e1dab2c152ff4.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/701cf4a9ae222e122235d12af3629b40.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/2f5899a9b49f746e7cb5097c483abd52.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/feecd718faf5462c4e326df3c7b5ee14.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/ab6da7f44253292d6f82e97d2874d241.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/bc662bdf9cf95b05c4c5ad83dd85f063.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/8004edc276a9186dee1bed02e3a2ecbd.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/6a521ccb2099e4b9c1649ca8d18ee2c2.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/ad239ecbaf88980f21be43934788694f.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/61ee13a03b25d0788d8e1dab2c152ff4.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/701cf4a9ae222e122235d12af3629b40.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/2f5899a9b49f746e7cb5097c483abd52.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/feecd718faf5462c4e326df3c7b5ee14.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/ab6da7f44253292d6f82e97d2874d241.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/bc662bdf9cf95b05c4c5ad83dd85f063.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/8004edc276a9186dee1bed02e3a2ecbd.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/6a521ccb2099e4b9c1649ca8d18ee2c2.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/ad239ecbaf88980f21be43934788694f.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呢","createTime":1517883199000,"createTimeStr":"23min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":37,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/a5eabe86f5f4a7145cd74371a6c67a50.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/20004147f655d0508d3122a7760dcd80.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/eedaed0887c1790dd9b868c8a3b493ee.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/a5eabe86f5f4a7145cd74371a6c67a50.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/20004147f655d0508d3122a7760dcd80.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/eedaed0887c1790dd9b868c8a3b493ee.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"阿狸","createTime":1517883114000,"createTimeStr":"24min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":36,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/8fa5ed0e6d9c6b33f23e279629e95394.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/83ecb1e698c0101a22fee51612862d8f.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/8fa5ed0e6d9c6b33f23e279629e95394.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/83ecb1e698c0101a22fee51612862d8f.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呵呵","createTime":1517882913000,"createTimeStr":"28min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":35,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/0b07de0b13ce0f1559a4b05d3948bc27.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/e15d96a79fd99a1cb0744eaf052e1447.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/d1fe29f6d73db69e5c4302ced85774d8.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/7921f65d40eaabc0e5e4eae72287a8d8.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/0b07de0b13ce0f1559a4b05d3948bc27.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/e15d96a79fd99a1cb0744eaf052e1447.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/d1fe29f6d73db69e5c4302ced85774d8.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/7921f65d40eaabc0e5e4eae72287a8d8.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呵呵","createTime":1517882900000,"createTimeStr":"28min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":34,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d25b1decc7970d0c48acf8183dcc0311.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/729791fde1cfcac16c7a9bfc83a548fa.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/29e1e922c0e2f829875282c7f64c213a.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/7e81e0aae13c26d42d6fd1353cba84c6.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/d25b1decc7970d0c48acf8183dcc0311.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/729791fde1cfcac16c7a9bfc83a548fa.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/29e1e922c0e2f829875282c7f64c213a.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/7e81e0aae13c26d42d6fd1353cba84c6.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呵呵","createTime":1517882853000,"createTimeStr":"29min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":33,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/88c14cc963a29ebdd154944addac4f2b.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/9488c62835aeafa2d9e9a0911e77f41f.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd70ae6c5180507f3051f737c4486371.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/a1cb22a9858fa681d7bf28f18307a002.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/88c14cc963a29ebdd154944addac4f2b.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/9488c62835aeafa2d9e9a0911e77f41f.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd70ae6c5180507f3051f737c4486371.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/a1cb22a9858fa681d7bf28f18307a002.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呵呵","createTime":1517882834000,"createTimeStr":"29min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":32,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/e9ae48f35e53bce31cadb29c5c389349.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/524a830bef1c4bbc1e8895bcbc2e51f5.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/ad209732bfc7814c2bd423d2a32c4708.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/a76d6b4524098771bdc8e1649aa01d6e.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/e9ae48f35e53bce31cadb29c5c389349.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/524a830bef1c4bbc1e8895bcbc2e51f5.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/ad209732bfc7814c2bd423d2a32c4708.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/a76d6b4524098771bdc8e1649aa01d6e.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"呵呵","createTime":1517882811000,"createTimeStr":"29min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":31,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/fe6745a44c7a35003eb053da531b560d.JPEG,http://topcoin.oss-cn-hangzhou.aliyuncs.com/2a17aa7614dc53a25219314ee45914a1.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd74564994a40665abf38e6a7f085c6f.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/931048fce2e1e965ce62cdb269940f6c.jpg","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/fe6745a44c7a35003eb053da531b560d.JPEG","http://topcoin.oss-cn-hangzhou.aliyuncs.com/2a17aa7614dc53a25219314ee45914a1.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd74564994a40665abf38e6a7f085c6f.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/931048fce2e1e965ce62cdb269940f6c.jpg"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false},{"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","badCount":0,"commentCount":0,"commentVos":[],"content":"是","createTime":1517882472000,"createTimeStr":"35min ago","goodCount":0,"groupId":0,"groupMaster":false,"groupName":"","id":30,"img":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/e2e0ae5f2be5e841cc3fa0668c705fb1.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/f1eb6219b615b93be033683e900ad1a5.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/7cb3caee597ba24fee240e3483489a02.jpg,http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd6266a54b4af8fa75cc29506d023ce8.JPEG","imgList":["http://topcoin.oss-cn-hangzhou.aliyuncs.com/e2e0ae5f2be5e841cc3fa0668c705fb1.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/f1eb6219b615b93be033683e900ad1a5.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/7cb3caee597ba24fee240e3483489a02.jpg","http://topcoin.oss-cn-hangzhou.aliyuncs.com/cd6266a54b4af8fa75cc29506d023ce8.JPEG"],"nickName":"无敌","platform":0,"praiseStr":"","showImg":true,"status":0,"title":"","top":0,"type":2,"updateTime":null,"userDemoDealVos":[],"userId":1,"userPraise":false}]
     * user : {"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg","bigv":0,"createTime":1517835022000,"email":"","id":1,"imToken":"H85C+3AJGsOsHyTwIoZqtVVxHIYGnXpkHuJ3K63i4Y5Z4wMdVwVksMpT8J0C9EyxIAmgKiNVSvs=","nickName":"无敌","password":"c4ca4238a0b923820dcc509a6f75849b","phone":"17633905867","subscribeCharge":0,"updateTime":1517835021000}
     */

    private int attentionCount;
    private int fansCount;
    private UserBean user;
    private boolean isAttention;


    List<SquareLive> articleTopicVos;
    List<UserCircle> groupVos;

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<SquareLive> getArticleTopicVos() {
        return articleTopicVos;
    }

    public void setArticleTopicVos(List<SquareLive> articleTopicVos) {
        this.articleTopicVos = articleTopicVos;
    }

    public List<UserCircle> getGroupVos() {
        return groupVos;
    }

    public void setGroupVos(List<UserCircle> groupVos) {
        this.groupVos = groupVos;
    }

    public static class UserBean {
        /**
         * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg
         * bigv : 0
         * createTime : 1517835022000
         * email :
         * id : 1
         * imToken : H85C+3AJGsOsHyTwIoZqtVVxHIYGnXpkHuJ3K63i4Y5Z4wMdVwVksMpT8J0C9EyxIAmgKiNVSvs=
         * nickName : 无敌
         * password : c4ca4238a0b923820dcc509a6f75849b
         * phone : 17633905867
         * subscribeCharge : 0
         * updateTime : 1517835021000
         */

        private String avatar;
        private int bigv;
        private long createTime;
        private String email;
        private int id;
        private String imToken;
        private String nickName;
        private String password;
        private String phone;
        private int subscribeCharge;
        private long updateTime;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getBigv() {
            return bigv;
        }

        public void setBigv(int bigv) {
            this.bigv = bigv;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImToken() {
            return imToken;
        }

        public void setImToken(String imToken) {
            this.imToken = imToken;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSubscribeCharge() {
            return subscribeCharge;
        }

        public void setSubscribeCharge(int subscribeCharge) {
            this.subscribeCharge = subscribeCharge;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
