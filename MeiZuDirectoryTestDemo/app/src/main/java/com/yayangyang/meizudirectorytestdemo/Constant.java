package com.yayangyang.meizudirectorytestdemo;

public class Constant {

    public static final String IMG_BASE_URL = "http://images.dmzj.com";

    public static final String API_BASE_URL = "http://v2.api.dmzj.com";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_RESPONSES = FileUtils.createRootPath(AppUtils.getAppContext());

    public static String PATH_PICTURES = FileUtils.createRootPath(AppUtils.getAppContext());

    public static String PATH_COLLECT = FileUtils.createRootPath(AppUtils.getAppContext()) + "/collect";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";

    public static String BASE_PATH = AppUtils.getAppContext().getCacheDir().getPath();


}
