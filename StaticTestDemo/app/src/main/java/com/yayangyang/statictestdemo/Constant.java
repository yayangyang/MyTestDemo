package com.yayangyang.statictestdemo;

import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {

    public static final String IMG_BASE_URL = "http://images.dmzj.com";

    public static final String API_BASE_URL = "http://v2.api.dmzj.com";


    public static final String ISNIGHT = "isNight";

    public static final String ISBYUPDATESORT = "isByUpdateSort";
    public static final String FLIP_STYLE = "flipStyle";

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

    @StringDef({
            CateType.HOT,
            CateType.NEW,
            CateType.REPUTATION,
            CateType.OVER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CateType {
        String HOT = "hot";

        String NEW = "new";

        String REPUTATION = "reputation";

        String OVER = "over";
    }

    @StringDef({
            Distillate.ALL,
            Distillate.DISTILLATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Distillate {
        String ALL = "";

        String DISTILLATE = "true";
    }

    @StringDef({
            SortType.DEFAULT,
            SortType.COMMENT_COUNT,
            SortType.CREATED,
            SortType.HELPFUL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
        String DEFAULT = "updated";

        String CREATED = "created";

        String HELPFUL = "helpful";

        String COMMENT_COUNT = "Comment-count";
    }

    public static List<String> sortTypeList = new ArrayList<String>() {{
        add(SortType.DEFAULT);
        add(SortType.CREATED);
        add(SortType.COMMENT_COUNT);
        add(SortType.HELPFUL);
    }};

    //------------------------------------------------------------------

    public static final int FINISH_ACTIVITY=1;
    public static final int RETURN_DATA=2;

    public static int ALL_COMIC=100;
    public static int ORIGINAL_COMIC=1;
    public static int DUBBING_COMIC=0;
    public static int LINEARLAYOUT_MANAGER=1;
    public static int GRIDLAYOUT_MANAGER=2;

    public static int POPULAR_RANK=0;
    public static int REVIEW_RANK=1;
    public static int SUSCRIBE_RANK=2;

    public static String CHANNEL="Android";
    public static String VERSION="2.7.001";

    public static String MY_INDEX="my_index";
    public static String CURRENT_COMIC_TYPE="currentComicType";;
    public static String CURRENT_DATE="currentdate";;
    public static String CURRENT_RANK_TYPE="currentRankType";;
    public static String TAG_ID="tag_id";;
    public static String OBJECT_ID="object_id";;


    @IntDef({
            //定义限定值
            ComicType.ALL,
            ComicType.JOYFUL,
            ComicType.HAREM,
            ComicType.WARM_BLOOD,
            ComicType.DRAG_QUEEN,
            ComicType.LILY,
            ComicType.SEX_CONVERSION,
            ComicType.SCIENCE_FICTION,
            ComicType.LOVE,
            ComicType.MAGIC,
            ComicType.TERROR,
            ComicType.AMERICA,
            ComicType.THE_END,
            ComicType.JUVENILE_COMIC,
            ComicType.GRIL_COMIC,
            ComicType.YOUTH_COMIC
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ComicType {
        //定义变量
        int ALL=0;
        int JOYFUL=5;
        int HAREM=3249;;
        int WARM_BLOOD=3248;
        int DRAG_QUEEN=3244;
        int LILY=3243;
        int SEX_CONVERSION=4518;
        int SCIENCE_FICTION=7;
        int LOVE=8;
        int MAGIC=11;
        int TERROR=14;
        int AMERICA=2306;
        int THE_END=2310;
        int JUVENILE_COMIC=3262;
        int GRIL_COMIC=3263;
        int YOUTH_COMIC=3264;
    }

    public static List<Integer> comicTypeList = new ArrayList<Integer>() {{
        add(ComicType.ALL);
        add(ComicType.JOYFUL);
        add(ComicType.HAREM);
        add(ComicType.WARM_BLOOD);
        add(ComicType.DRAG_QUEEN);
        add(ComicType.LILY);
        add(ComicType.SEX_CONVERSION);
        add(ComicType.SCIENCE_FICTION);
        add(ComicType.LOVE);
        add(ComicType.MAGIC);
        add(ComicType.TERROR);
        add(ComicType.AMERICA);
        add(ComicType.THE_END);
        add(ComicType.JUVENILE_COMIC);
        add(ComicType.GRIL_COMIC);
        add(ComicType.YOUTH_COMIC);
    }};

    public static Map<Integer, String> comicTypeMap = new HashMap<Integer, String>() {{
        put(ComicType.ALL, "全部");
        put(ComicType.JOYFUL, "欢乐向");
        put(ComicType.HAREM, "后宫");
        put(ComicType.WARM_BLOOD, "热血");
        put(ComicType.DRAG_QUEEN, "伪娘");
        put(ComicType.LILY, "百合");
        put(ComicType.SEX_CONVERSION, "性转换");
        put(ComicType.SCIENCE_FICTION, "科幻");
        put(ComicType.LOVE, "爱情");
        put(ComicType.MAGIC, "魔法");
        put(ComicType.TERROR, "恐怖");
        put(ComicType.AMERICA, "欧美");
        put(ComicType.THE_END, "已完结");
        put(ComicType.JUVENILE_COMIC, "少年漫画");
        put(ComicType.GRIL_COMIC, "少女漫画");
        put(ComicType.YOUTH_COMIC, "青年漫画");
    }};

    @IntDef({
            DateType.DAY,
            DateType.WEEK,
            DateType.MONTH,
            DateType.TOTAL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateType {
        int DAY=0;
        int WEEK=1;
        int MONTH=2;
        int TOTAL=3;
    }

    public static List<Integer> dateTypeList = new ArrayList<Integer>() {{
        add(DateType.DAY);
        add(DateType.WEEK);
        add(DateType.MONTH);
        add(DateType.TOTAL);
    }};

    public static Map<Integer, String> dateTypeMap = new HashMap<Integer, String>() {{
        put(DateType.DAY, "日");
        put(DateType.WEEK, "周");
        put(DateType.MONTH, "月");
        put(DateType.TOTAL, "总");
    }};

    public @interface NewsType {
        int RECOMMEND=0;
        int ANIMATED_INFORMATION=1;
        int COMIC_INFORMATION=2;
        int LIGHT_NOVEL_INFORMATION=3;
        int APPRECIATE_PICTURE=8;
        int GAMNE_INFORMATION=7;
        int COMIC_PERIPHERY=4;
        int AKIRA_INFORMATION=5;
        int COMIC_DISPLAY=9;
        int MUSIC_INFORMATION=6;
        int HODGEPODGE=10;
    }

    @StringDef({
            ThemeType.ALL,
            ThemeType.ADVENTURE,
            ThemeType.LILY,
            ThemeType.LIFE,
            ThemeType.FOUR_LATTICE,
            ThemeType.DRAG_QUEEN,
            ThemeType.SUSPENSE,
            ThemeType.HISTORY,
            ThemeType.HAREM,
            ThemeType.WARM_BLOOD,
            ThemeType.TANBI,
            ThemeType.OTHER,
            ThemeType.TERROR,
            ThemeType.SCIENCE_FICTION,
            ThemeType.COMBAT,
            ThemeType.JOYFUL,
            ThemeType.LOVE,
            ThemeType.DETECTIVE,
            ThemeType.SCHOOL,
            ThemeType.SUPERNATURAL,
            ThemeType.MAGIC,
            ThemeType.ATHLETICS,
            ThemeType.WARFARE,
            ThemeType.ADORABLE,
            ThemeType.FANTASY,
            ThemeType.MAGIC_HALLUCINATION,
            ThemeType.TO_HELP_HER,
            ThemeType.PRINCIPLES,
            ThemeType.LIGHT_NOVEL,
            ThemeType.KANTAI_COLLECTION,
            ThemeType.XIAN_XIA,
            ThemeType.AMUSE,
            ThemeType.YAN_YI,
            ThemeType.EAST,
            ThemeType.SEX_CONVERSION,
            ThemeType.WU_XIA,
            ThemeType.ENCOURAGEMENT,
            ThemeType.CURE,
            ThemeType.HOME_DEPARTMENT,
            ThemeType.SUPER_ROBOT_WARS,
            ThemeType.MUSIC_AND_DANCE,
            ThemeType.HIGH_DEFINITION_SINGLE_LINE,
            ThemeType.WESTERN_MAGIC,
            ThemeType.JOB_MARKET,
            ThemeType.DELICIOUS_FOOD
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThemeType {
        String ALL="0";
        String ADVENTURE="4";
        String LILY="3243";
        String LIFE="3242";
        String FOUR_LATTICE="17";
        String DRAG_QUEEN="3244";
        String SUSPENSE="3245";
        String HISTORY="3250";
        String HAREM="3249";
        String WARM_BLOOD="3248";
        String TANBI="3246";
        String OTHER="16";
        String TERROR="14";
        String SCIENCE_FICTION="7";
        String COMBAT="6";
        String JOYFUL="5";
        String LOVE="8";
        String DETECTIVE="9";
        String SCHOOL="13";
        String SUPERNATURAL="12";
        String MAGIC="11";
        String ATHLETICS="1";
        String WARFARE="3251";
        String ADORABLE="3252";
        String FANTASY="5848";
        String MAGIC_HALLUCINATION="5806";
        String TO_HELP_HER="5345";
        String PRINCIPLES="6219";
        String LIGHT_NOVEL="6316";
        String KANTAI_COLLECTION="13627";
        String XIAN_XIA="7900";
        String AMUSE="7568";
        String YAN_YI="5077";
        String EAST="5077";
        String SEX_CONVERSION="4518";
        String WU_XIA="3324";
        String ENCOURAGEMENT="3255";
        String CURE="3254";
        String HOME_DEPARTMENT="3253";
        String SUPER_ROBOT_WARS="3325";
        String MUSIC_AND_DANCE="3326";
        String HIGH_DEFINITION_SINGLE_LINE="4459";
        String WESTERN_MAGIC="3365";
        String JOB_MARKET="3328";
        String DELICIOUS_FOOD="3327";

    }

    public static List<String> themeTypeList = new ArrayList<String>() {{
        add(ThemeType.ALL);
        add(ThemeType.ADVENTURE);
        add(ThemeType.LILY);
        add(ThemeType.LIFE);
        add(ThemeType.FOUR_LATTICE);
        add(ThemeType.DRAG_QUEEN);
        add(ThemeType.SUSPENSE);
        add(ThemeType.HISTORY);
        add(ThemeType.HAREM);
        add(ThemeType.WARM_BLOOD);
        add(ThemeType.TANBI);
        add(ThemeType.OTHER);
        add(ThemeType.TERROR);
        add(ThemeType.SCIENCE_FICTION);
        add(ThemeType.COMBAT);
        add(ThemeType.JOYFUL);
        add(ThemeType.LOVE);
        add(ThemeType.DETECTIVE);
        add(ThemeType.SCHOOL);
        add(ThemeType.SUPERNATURAL);
        add(ThemeType.MAGIC);
        add(ThemeType.ATHLETICS);
        add(ThemeType.WARFARE);
        add(ThemeType.ADORABLE);
        add(ThemeType.FANTASY);
        add(ThemeType.MAGIC_HALLUCINATION);
        add(ThemeType.TO_HELP_HER);
        add(ThemeType.PRINCIPLES);
        add(ThemeType.LIGHT_NOVEL);
        add(ThemeType.KANTAI_COLLECTION);
        add(ThemeType.XIAN_XIA);
        add(ThemeType.AMUSE);
        add(ThemeType.YAN_YI);
        add(ThemeType.EAST);
        add(ThemeType.SEX_CONVERSION);
        add(ThemeType.WU_XIA);
        add(ThemeType.ENCOURAGEMENT);
        add(ThemeType.CURE);
        add(ThemeType.HOME_DEPARTMENT);
        add(ThemeType.SUPER_ROBOT_WARS);
        add(ThemeType.MUSIC_AND_DANCE);
        add(ThemeType.HIGH_DEFINITION_SINGLE_LINE);
        add(ThemeType.WESTERN_MAGIC);
        add(ThemeType.JOB_MARKET);
        add(ThemeType.DELICIOUS_FOOD);
    }};

    @StringDef({
            AgeType.ALL,
            AgeType.JUVENILE_COMIC,
            AgeType.GRIL_COMIC,
            AgeType.YOUTH_COMIC,
            AgeType.FEMALE_YOUTH_COMIC
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AgeType {
        String ALL="0";
        String JUVENILE_COMIC="3262";
        String GRIL_COMIC="3263";
        String YOUTH_COMIC="3264";
        String FEMALE_YOUTH_COMIC="13626";
    }

    public static List<String> ageTypeList = new ArrayList<String>() {{
        add(AgeType.ALL);
        add(AgeType.JUVENILE_COMIC);
        add(AgeType.GRIL_COMIC);
        add(AgeType.YOUTH_COMIC);
        add(AgeType.FEMALE_YOUTH_COMIC);
    }};

    @StringDef({
            ScheduleType.ALL,
            ScheduleType.SERIALIZING,
            ScheduleType.THE_END
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScheduleType {
        String ALL="0";
        String SERIALIZING ="2309";
        String THE_END="2310";
    }

    public static List<String> scheduleTypeList = new ArrayList<String>() {{
        add(ScheduleType.ALL);
        add(ScheduleType.SERIALIZING);
        add(ScheduleType.THE_END);
    }};

    @StringDef({
            RegionType.ALL,
            RegionType.JAPAN,
            RegionType.KOREA,
            RegionType.EUROPE_AND_AMERICA,
            RegionType.HONGKONG_AND_TAIWAN,
            RegionType.OTHER,
            RegionType.INLAND
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RegionType {
        String ALL="0";
        String JAPAN ="2304";
        String KOREA="2305";
        String EUROPE_AND_AMERICA="2306";
        String HONGKONG_AND_TAIWAN="2307";
        String OTHER="8435";
        String INLAND="2308";
    }

    public static List<String> regionTypeList = new ArrayList<String>() {{
        add(RegionType.ALL);
        add(RegionType.JAPAN);
        add(RegionType.KOREA);
        add(RegionType.EUROPE_AND_AMERICA);
        add(RegionType.HONGKONG_AND_TAIWAN);
        add(RegionType.OTHER);
        add(RegionType.INLAND);
    }};



    @StringDef({
            NovelCommonType.ALL,
            NovelCommonType.TERROR,
            NovelCommonType.SCIENCE_FICTION,
            NovelCommonType.DETECTIVE,
            NovelCommonType.LOVE,
            NovelCommonType.SCHOOL,
            NovelCommonType.SUPERNATURAL,
            NovelCommonType.MAGIC,
            NovelCommonType.ADVENTURE,
            NovelCommonType.OTHER,
            NovelCommonType.JOYFUL,
            NovelCommonType.COMBAT,
            NovelCommonType.SUPER_ROBOT_WARS,
            NovelCommonType.XIAN_XIA,
            NovelCommonType.CITY,
            NovelCommonType.HISTORY,
            NovelCommonType.WARFARE,
            NovelCommonType.ENCOURAGEMENT,
            NovelCommonType.HAREM,
            NovelCommonType.LILY,
            NovelCommonType.TANBI,
            NovelCommonType.ANOTHER_WORLD,
            NovelCommonType.ABILITY,
            NovelCommonType.TIME_TRAVEL,
            NovelCommonType.FANTASY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface NovelCommonType {
        String ALL="0";
        String TERROR ="2";
        String SCIENCE_FICTION="4";
        String DETECTIVE="6";
        String LOVE="8";
        String SCHOOL="12";
        String SUPERNATURAL="14";
        String MAGIC="16";
        String ADVENTURE="20";
        String OTHER="25";
        String JOYFUL="40";
        String COMBAT="47";
        String SUPER_ROBOT_WARS="1264";
        String XIAN_XIA="1265";
        String CITY="1266";
        String HISTORY="1267";
        String WARFARE="1268";
        String ENCOURAGEMENT="1269";
        String HAREM="1271";
        String LILY="1272";
        String TANBI="1273";
        String ANOTHER_WORLD="1274";
        String ABILITY="1275";
        String TIME_TRAVEL="1276";
        String FANTASY="1321";
    }

    public static List<String> novelCommonTypeList = new ArrayList<String>() {{
        add(NovelCommonType.ALL);
        add(NovelCommonType.TERROR);
        add(NovelCommonType.SCIENCE_FICTION);
        add(NovelCommonType.DETECTIVE);
        add(NovelCommonType.LOVE);
        add(NovelCommonType.SCHOOL);
        add(NovelCommonType.SUPERNATURAL);
        add(NovelCommonType.MAGIC);
        add(NovelCommonType.ADVENTURE);
        add(NovelCommonType.OTHER);
        add(NovelCommonType.JOYFUL);
        add(NovelCommonType.COMBAT);
        add(NovelCommonType.SUPER_ROBOT_WARS);
        add(NovelCommonType.XIAN_XIA);
        add(NovelCommonType.CITY);
        add(NovelCommonType.HISTORY);
        add(NovelCommonType.WARFARE);
        add(NovelCommonType.ENCOURAGEMENT);
        add(NovelCommonType.HAREM);
        add(NovelCommonType.LILY);
        add(NovelCommonType.HAREM);
        add(NovelCommonType.TANBI);
        add(NovelCommonType.ANOTHER_WORLD);
        add(NovelCommonType.ABILITY);
        add(NovelCommonType.TIME_TRAVEL);
        add(NovelCommonType.FANTASY);
    }};

    @StringDef({
            novelScheduleType.ALL,
            novelScheduleType.SERIALIZING,
            novelScheduleType.THE_END
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface novelScheduleType {
        String ALL="0";
        String SERIALIZING ="1";
        String THE_END="2";
    }

    public static List<String> novelScheduleTypeList = new ArrayList<String>() {{
        add(ScheduleType.ALL);
        add(ScheduleType.SERIALIZING);
        add(ScheduleType.THE_END);
    }};

}
