package com.xiaomiquan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.PathEntity;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Created by 郭青枫 on 2017/8/5.
 *
 * ui 处理统一工具
 */

public class UiHeplUtils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static float getPXfromDP(float value, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 根据设计稿计算实际宽高
     *
     * @param context
     * @param paddingRes 总间距 px
     * @param viewNum    每行数量
     * @param relWith    设计稿宽度px
     * @param relHei     设计稿高度px
     * @return
     */
    public static int[] cacularWidAndHei(Context context, @DimenRes int paddingRes, int viewNum, int relWith, int relHei) {
        int[] size = new int[2];
        int paddingValue = context.getResources().getDimensionPixelSize(paddingRes);
        int screenWidth = getScreenW(context, false);
        int viewWidth = (screenWidth - paddingValue) / viewNum;
        int viewHeight = viewWidth * relHei / relWith;
        size[0] = viewWidth;
        size[1] = viewHeight;
        return size;
    }

    public static int[] cacularWidAndHei(Context context, @DimenRes int widthRes, @DimenRes int paddingRes, int viewNum, int relWith, int relHei) {
        int[] size = new int[2];
        int paddingValue = context.getResources().getDimensionPixelSize(paddingRes);
        int screenWidth = getScreenW(context, false) - context.getResources().getDimensionPixelSize(widthRes);
        int viewWidth = (screenWidth - paddingValue) / viewNum;
        int viewHeight = viewWidth * relHei / relWith;
        size[0] = viewWidth;
        size[1] = viewHeight;
        return size;
    }

    public static void setCacularWidAndHei(int[] ints, View view) {
        if (ints != null) {
            if (view != null) {
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();
                layoutParams.width = ints[0];
                layoutParams.height = ints[1];
                view.setLayoutParams(layoutParams);
            }
        }
    }

    /**
     * 获取屏幕像素尺寸
     *
     * @return : true:高 false:宽
     * @author yff 2013-10-31下午1:08:22
     */
    public static int getScreenW(Context context, boolean isHeight) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        return isHeight ? metrics.heightPixels : metrics.widthPixels;
    }

    /**
     * 根据文字长度计算占多少中文字符宽度
     */
    public static float stringToLenght(String content) {
        float lenght = 0;
        if (!TextUtils.isEmpty(content)) {
            for (int i = 0; i < content.length(); i++) {

                String txt = String.valueOf(content.charAt(i));

                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(txt);
                if (m.matches()) {
                    lenght = lenght + 0.5f;
                }
                p = Pattern.compile("[a-zA-Z]");
                m = p.matcher(txt);
                if (m.matches()) {
                    lenght = lenght + 0.8f;
                }
                p = Pattern.compile("[\u4e00-\u9fa5]");
                m = p.matcher(txt);
                if (m.matches()) {
                    lenght = lenght + 1;
                }

            }
        }
        return lenght;
    }

    /**
     * 修改手机号中间四个为*
     */
    public static String hidePhone(String phone) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(phone.substring(0, 3));
        stringBuffer.append(" **** ");
        stringBuffer.append(phone.substring(phone.length() - 4, phone.length()));
        return stringBuffer.toString();
    }

    /**
     * 性别转换
     *
     * @param num
     * @return
     */
    public static String sex(String num) {
        if ("1".equals(num)) {
            return "男";
        }
        if ("2".equals(num)) {
            return "女";
        }
        return "";
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        Log.d("HomePageActivity", "version1Array==" + version1Array.length);
        Log.d("HomePageActivity", "version2Array==" + version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        Log.d("HomePageActivity", "verTag2=2222=" + version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3456789]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 获取RecyclerView 滑动高度
     */
    public int getScollYDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    /**
     * 一键设置webview
     *
     * @param webView
     */
    public static void webviewRegister(WebView webView) {
        //允许访问文件
        WebSettings webSettings = webView.getSettings();
        //
        webSettings.setDomStorageEnabled(true);
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        ////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //获取触摸焦点
        webView.requestFocusFromTouch();
        //http/https混合加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置跨域cookie读取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }
        });
    }

    /**
     * 倒计时
     *
     * @param textView
     * @param count
     * @return
     */
    public static Disposable getCode(final TextView textView, final long count) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong; // 由于是倒计时，需要将倒计时的数字反过来
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        textView.setEnabled(false);
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        textView.setText(aLong + CommonUtils.getString(R.string.str_seconds_resend));
                        textView.setGravity(Gravity.CENTER);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        textView.setEnabled(true);
                        textView.setText(CommonUtils.getString(R.string.str_resend));
                        textView.setGravity(Gravity.CENTER);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        textView.setEnabled(true);
                        textView.setText(CommonUtils.getString(R.string.str_resend));
                        textView.setGravity(Gravity.CENTER);
                    }
                });
    }

    /**
     * 获取验证码
     */
    public static void getCode(final CompositeDisposable compositeDisposable, final TextView textView, final long count) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong; // 由于是倒计时，需要将倒计时的数字反过来
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        textView.setEnabled(false);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        textView.setText(aLong + "秒后重发");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        textView.setEnabled(true);
                        textView.setText("获取验证码");
                    }
                });
    }


    //设置图库样式
    public static Widget getDefaultAlbumWight(Context context, String title) {
        return Widget.newDarkBuilder(context)
                .title(title) // Title.
                .statusBarColor(context.getResources().getColor(R.color.color_blue)) // StatusBar color.
                .toolBarColor(context.getResources().getColor(R.color.color_blue)) // Toolbar color.
                .navigationBarColor(Color.WHITE) // Virtual NavigationBar color of Android5.0+.
                .mediaItemCheckSelector(context.getResources().getColor(R.color.color_blue),
                        context.getResources().getColor(R.color.color_blue_dark)) // Image or video selection box.
                .bucketItemCheckSelector(context.getResources().getColor(R.color.color_blue),
                        context.getResources().getColor(R.color.color_blue_dark)) // Select the folder selection box.
                .buttonStyle( // Used to configure the style of button when the image/video is not found.
                        Widget.ButtonStyle.newDarkBuilder(context) // With Widget's Builder model.
                                .setButtonSelector(Color.WHITE, Color.WHITE) // Button selector.
                                .build()
                ).build();
    }

    /**
     * 牌照path转 AlbumFile
     */
    public static AlbumFile stringToAlbumFile(String result) {
        File file = new File(result);
        String name = file.getName();
        AlbumFile albumFile = new AlbumFile();
        albumFile.setPath(result);
        albumFile.setName(name);
        String title = name;
        if (name.contains("."))
            title = name.split("\\.")[0];
        albumFile.setTitle(title);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(result));
        albumFile.setMimeType(mimeType);
        long nowTime = System.currentTimeMillis();
        albumFile.setAddDate(nowTime);
        albumFile.setModifyDate(nowTime);
        albumFile.setSize(file.length());
        albumFile.setThumbPath(result);
        albumFile.setMediaType(AlbumFile.TYPE_IMAGE);
        albumFile.setChecked(true);

        return albumFile;
    }

    public static List<AlbumFile> stringsToAlbumFiles(List<String> results) {
        List<AlbumFile> albumFileList = new ArrayList<>();
        for (String result : results) {
            File file = new File(result);
            String name = file.getName();
            AlbumFile albumFile = new AlbumFile();
            albumFile.setPath(result);
            albumFile.setName(name);
            String title = name;
            if (name.contains("."))
                title = name.split("\\.")[0];
            albumFile.setTitle(title);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(result));
            albumFile.setMimeType(mimeType);
            long nowTime = System.currentTimeMillis();
            albumFile.setAddDate(nowTime);
            albumFile.setModifyDate(nowTime);
            albumFile.setSize(file.length());
            albumFile.setThumbPath(result);
            albumFile.setMediaType(AlbumFile.TYPE_IMAGE);
            albumFile.setChecked(true);
            albumFileList.add(albumFile);
        }
        return albumFileList;
    }

    /**
     * html 编码
     *
     * @param str
     * @return
     */
    public static String fmtString(String str) {
        String notice = "";
        try {
            notice = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {

        }
        return notice;
    }

    /**
     * 把 pathentity集合转成 path集合
     *
     * @param pathEntityList
     * @return
     */
    public static List<String> pathEntityToSmallPathList(List<PathEntity> pathEntityList) {
        List<String> strList = new ArrayList<>();
        if (!isListNull(pathEntityList)) {
            for (PathEntity pathEntity : pathEntityList) {
                strList.add(pathEntity.getSmall_path());
            }
        }
        return strList;
    }

    /**
     * 把 pathentity集合转成 path集合
     *
     * @param pathEntityList
     * @return
     */
    public static List<String> pathEntityToPathList(List<PathEntity> pathEntityList) {
        List<String> strList = new ArrayList<>();
        if (!isListNull(pathEntityList)) {
            for (PathEntity pathEntity : pathEntityList) {
                strList.add(pathEntity.getPath());
            }
        }
        return strList;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @return
     */
    public static boolean isListNull(Collection collection) {
        if (collection == null || collection.size() == 0)
            return true;
        else
            return false;
    }

    public static Map<String, List> getValueListMap(List list, String... paramsName) {
        Map<String, List> map = new HashMap<>();
        if (list == null || paramsName == null)
            return map;
        for (String item : paramsName) {
            map.put(item, new ArrayList<>());
        }
        for (Object t : list) {
            // 得到类对象
            Class userCla = (Class) t.getClass();
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); // 设置属性是可以访问的
                try {
                    for (String item : paramsName) {
                        if (f.getName().equals(item)) {
                            map.get(item).add(f.get(t));
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    /**
     * 吧String 集合转成用逗号隔开
     *
     * @param list
     */
    public static String pinjieString(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0, size = list.size(); i < size; i++) {
            if (i == size - 1) {
                buffer.append(list.get(i));
            } else {
                buffer.append(list.get(i) + ",");
            }
        }
        return buffer.toString();
    }

    /**
     * 将集合转换成map
     *
     * @param list
     * @param key
     * @return
     */
    public static Map<String, Object> listToFileMap(List list, String key) {
        if (list != null && list.size() > 0) {
            Map<String, Object> fileMap = new HashMap<>();
            List<String> strList = new ArrayList<>();
            for (Object item : list) {
                if (new File(item.toString()).exists()) {
                    strList.add(item.toString());
                }
            }
            for (int i = 0, size = strList.size(); i < size; i++) {
                fileMap.put(key + "[" + i + "]", new File(strList.get(i)));
            }
            return fileMap;
        }
        return null;
    }

    /**
     * 获取删除服务器的id
     *
     * @param list 全部的图片
     * @param path 服务器的图片
     * @return
     */
    public static String getDeletePathId(List list, List<PathEntity> path) {
        if (path != null && path.size() > 0) {

            List<PathEntity> deletePath = new ArrayList<>();
            for (PathEntity item : path) {
                if (!list.contains(item)) {
                    deletePath.add(item);
                }
            }
            if (deletePath.size() > 0) {
                StringBuffer pathid = new StringBuffer();
                for (int i = 0, size = deletePath.size(); i < size; i++) {
                    if (i < size - 1) {
                        pathid.append(deletePath.get(i).getId() + ",");
                    } else {
                        pathid.append(deletePath.get(i).getId());
                    }
                }
                return pathid.toString();
            }
        }
        return null;
    }

    /**
     * 判断提交必填参数是否为空
     *
     * @param content
     * @return
     */
    public static boolean judgeRequestContentIsNull(String content, String toastString) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show(toastString);
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
        if (str == null || !str.matches(regex)) {
            return false;
        }
        return true;
    }

    /**
     * 打开网页
     */
    public static void startWeb(Context context, String url) {
        String new_url = url.replace("\\", "");
        Uri uri = Uri.parse(new_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("url", new_url);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveList = pm.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        Log.i("MainActivity", new_url + "resolveList size:" + resolveList.size());
        if (resolveList.size() > 0) {
            String title = "选择浏览器打开";
            Intent intentChooser = Intent.createChooser(intent, title);
            context.startActivity(intentChooser);
        } else {
            ToastUtils.showShort("没有浏览器可以打开此网页!");
        }
    }

    /**
     * 数字 前面加0
     */
    public static String numIntToString(int num, int digits) {
        String str = String.format("%0" + digits + "d", num);
        return str;
    }

    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + " B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + " KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + " MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + " GB";
        }
    }

    /**
     * gly 添加
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 获取手机的密度*/
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
}
