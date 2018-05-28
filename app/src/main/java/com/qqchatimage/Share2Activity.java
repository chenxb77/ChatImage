package com.qqchatimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenxb on 2017/10/19.
 */

public class Share2Activity extends Activity {
    FrameLayout frameQuan, frameZone;
    TextView tvQuanSysTime, tvQuanSendTime, tvQuanTag;
    TextView tvZoneList, tvZoneSysTime, tvZoneSendTime, tvZoneTag;
    ImageView ivPraise1, ivPraise2, ivPraise3, ivShade1, ivShade2, ivShade3, ivShade4;
    EditText etTag, etClock;

    String sysTime, sendTime;
    SpannableString tag, tag2;
    File folder;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat tagFormat = new SimpleDateFormat("MM月dd日");

    String folder_tag = "1";
    ImageView ivQuan, ivZone;
    RadioButton rbZone1, rbZone2;
    RadioGroup rbGroup;

    int[] praiseIds = {R.drawable.praise0, R.drawable.praise1, R.drawable.praise2, R.drawable.praise3,
            R.drawable.praise4, R.drawable.praise5, R.drawable.praise6, R.drawable.praise7,
            R.drawable.praise8, R.drawable.praise9, R.drawable.praise10, R.drawable.praise11};

    String[] converts = {"シ俊", "虚.夜月", "不屈", "小豆豆", "Kevin晏", "皮卡丘",
            "Y_Lam", "明", "向左’永垂不朽的偏爱", "胡炳昆", "回亿是曾经", "控制つ爱你", "她说", "一念永恒", "朝槿夕凉", "梧桐相待", "A0-隔壁老王",
            "LKF", "爱拼才会赢", "紫樱菲飞", "云~涧~水", "☆star&", "心&梦君", "小小舒", "丹里个丹", "蓝天", "LzHس钻戒", "心の始迹",
            "不要叫醒", "简单&爱", "涅槃", "暮光之城", "·风雨淋·", "～脚印～", "∮  瀛~", "ャ爵洳σ", "安安", "亾.生缒.莍", "断线の风筝",
            "︷Ｋī︷Ｋǐ", "葉子葉子.花..", "み开心", "落/ǖā", "丛林深处", "小田鼠~", "隱形人○", "Rebelion", "海Ge", "小旧", "珊瑚虫", "Jennifer", "维维豆奶"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share2);

        frameQuan = (FrameLayout) findViewById(R.id.fl_praise);
        frameZone = (FrameLayout) findViewById(R.id.fl_convert);

        tvQuanTag = (TextView) findViewById(R.id.tv_quan_tag);
        tvQuanSysTime = (TextView) findViewById(R.id.tv_praise_sys_time);
        tvQuanSendTime = (TextView) findViewById(R.id.tv_praise_send_time);
        ivPraise1 = (ImageView) findViewById(R.id.iv_praise1);
        ivPraise2 = (ImageView) findViewById(R.id.iv_praise2);
        ivPraise3 = (ImageView) findViewById(R.id.iv_praise3);
        ivShade1 = (ImageView) findViewById(R.id.iv_shade1);
        ivShade2 = (ImageView) findViewById(R.id.iv_shade2);
        ivShade3 = (ImageView) findViewById(R.id.iv_shade3);
        ivShade4 = (ImageView) findViewById(R.id.iv_shade4);

        tvZoneList = (TextView) findViewById(R.id.tv_convert_list);
        tvZoneSysTime = (TextView) findViewById(R.id.tv_convert_sys_time);
        tvZoneSendTime = (TextView) findViewById(R.id.tv_convert_send_time);
        tvZoneTag = (TextView) findViewById(R.id.tv_zone_tag);

        ivQuan = (ImageView) findViewById(R.id.iv_quan);
        ivZone = (ImageView) findViewById(R.id.iv_zone);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        rbZone1 = (RadioButton) findViewById(R.id.rb_share1);
        rbZone2 = (RadioButton) findViewById(R.id.rb_share2);

        etTag = (EditText) findViewById(R.id.et_tag);
        etClock = (EditText) findViewById(R.id.et_clock);

        folder = new File(Environment.getExternalStorageDirectory().toString() + "/naruto/爱吾"
                + dateFormat.format(new Date(System.currentTimeMillis())) + "/");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        setContent();

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    getImage(frameZone, false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getImage(frameQuan, true);
                        }
                    }, 3000);
                }
            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    setContent();
                }
            }
        });

        etTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setText();
            }
        });

        etClock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendTime = getSendTimeStr();
                tvQuanSendTime.setText(sendTime);
                tvZoneSendTime.setText("今天" + sendTime);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_share1:
                        folder_tag = "1";
                        ivQuan.setImageResource(R.drawable.share_quan1);
                        ivZone.setImageResource(R.drawable.share_zone1);
                        break;
                    case R.id.rb_share2:
                        folder_tag = "2";
                        ivQuan.setImageResource(R.drawable.share_quan2);
                        ivZone.setImageResource(R.drawable.share_zone2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private String getSendTimeStr() {
        if (etClock.getText().toString().contains(":")) {
            return etClock.getText().toString();
        } else {
            return etClock.getText() + ":" + (30 + (int) (Math.random() * 10));
        }
    }

    private void setText(){
        String str = etTag.getText() + getString(R.string.content_share2);
        tag = new SpannableString(str);
        tag2 = new SpannableString(str);
        try {
            int start = str.indexOf("http");
            int end = start + 26;
            tag.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlueFuli)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tag2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColorBlueFuli2)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        tvQuanTag.setText(tag);
        tvZoneTag.setText(tag2);
    }

    private void setContent() {
        sysTime = timeFormat.format(new Date(System.currentTimeMillis()));
        sendTime = getSendTimeStr();
        setText();
        setCommonZone();
        setCommonQuan();
    }

    private void setCommonZone() {
        tvZoneSysTime.setText(sysTime);
        tvZoneSendTime.setText("今天" + sendTime);

        int count = (int) (10 + 3 * Math.random());
        StringBuffer sb = new StringBuffer();
        int index = converts.length - 1;
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                sb.append("     ");
            } else {
                sb.append("、");
            }
            sb.append(converts[index]);
            index -= ((int) (Math.random() * 4) + 1);
        }
        tvZoneList.setText(sb.toString());
    }

    private void setCommonQuan() {
        tvQuanSysTime.setText(sysTime);
        tvQuanSendTime.setText(sendTime);

        int i1 = (int) (Math.random() * 4), i2 = i1 + 1 + (int) (Math.random() * 4), i3 = i2 + 1 + (int) (Math.random() * 3);
        ivPraise1.setImageResource(praiseIds[i1]);
        ivPraise2.setImageResource(praiseIds[i2]);
        ivPraise3.setImageResource(praiseIds[i3]);

        double r = Math.random();
        if (r > 0.2f) {
            ivShade1.setVisibility(View.GONE);
        } else {
            ivShade1.setVisibility(View.VISIBLE);
        }
        if (r > 0.4f) {
            ivShade2.setVisibility(View.GONE);
        } else {
            ivShade2.setVisibility(View.VISIBLE);
        }
        if (r > 0.6f) {
            ivShade3.setVisibility(View.GONE);
        } else {
            ivShade3.setVisibility(View.VISIBLE);
        }
        if (r > 0.8f) {
            ivShade4.setVisibility(View.GONE);
        } else {
            ivShade4.setVisibility(View.VISIBLE);
        }
    }

    private void getImage(View view, boolean over) {
        Bitmap bitmap = loadBitmapFromView(view);
        try {
            saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
            if (over) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.draw(c);
        return screenshot;
    }

    public void saveBitmap(Bitmap bm, String name) throws IOException {
        File f = new File(folder + "/" + folder_tag, name);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }
}