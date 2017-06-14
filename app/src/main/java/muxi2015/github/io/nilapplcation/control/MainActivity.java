package muxi2015.github.io.nilapplcation.control;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import muxi2015.github.io.nilapplcation.R;
import muxi2015.github.io.nilapplcation.dao.DataBaseMH;
import muxi2015.github.io.nilapplcation.entity.Question;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //数据库的名称
    private String[] DB_NAME = {"questionA.db", "questionB.db", "questionC.db", "questionD.db"};
    //数据库的地址
    private String DB_PATH = "/data/data/muxi2015.github.io.nilapplcation/databases/";
    //数据库具体位置
    String dbname = null;
    //总的题目数据
    private int count;
    //当前显示的题目
    private int corrent;
    //问题
    private TextView tv_title;
    //选项
    RadioButton[] mRadioButton = new RadioButton[4];
    //上一题
    private Button btn_up;
    //下一题
    private Button btn_down;
    //详情
    private TextView tv_result;
    //容器
    private RadioGroup mRadioGroup;
    //是否进入错题模式
    private boolean wrongMode;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFile();
        initView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //判断数据库是否拷贝到相应的目录下
    private void initFile() {
        for (String dbnames : DB_NAME
                ) {
            if (new File(DB_PATH + dbnames).exists() == false) {
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                System.out.println("开始拷贝");
                //复制文件
                try {
                    InputStream is = getBaseContext().getAssets().open(dbnames);
                    OutputStream os = new FileOutputStream(DB_PATH + dbnames);

                    //用来复制文件
                    byte[] buffer = new byte[1024];
                    //保存已经复制的长度
                    int length;

                    //开始复制
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    System.out.println("复制完成");
                    //刷新
                    os.flush();
                    //关闭
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     * 初始化View
     */
    private void initView() {

        wrongMode = false;

        tv_title = (TextView) findViewById(R.id.answer_title);

        mRadioButton[0] = (RadioButton) findViewById(R.id.RadioA);
        mRadioButton[1] = (RadioButton) findViewById(R.id.RadioB);
        mRadioButton[2] = (RadioButton) findViewById(R.id.RadioC);
        mRadioButton[3] = (RadioButton) findViewById(R.id.RadioD);

        btn_down = (Button) findViewById(R.id.next_btn);
        btn_up = (Button) findViewById(R.id.last_btn);

        tv_result = (TextView) findViewById(R.id.tx_ruslt);

        mRadioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_JavaSenior) {
            page_method();
            dbname = "questionA";
            initDB(dbname);
        } else if (id == R.id.nav_HTML) {
            dbname = "questionB.db";
        } else if (id == R.id.nav_JavaBase) {
            dbname = "questionC.db";
        } else if (id == R.id.nav_SqlServer) {
            dbname = "questionD.db";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void page_method() {
        View question_page = findViewById(R.id.question_page);
        View index_layout = findViewById(R.id.index_layout);
        question_page.setVisibility(View.VISIBLE);
        index_layout.setVisibility(View.INVISIBLE);
    }

    /**
     * 初始化数据库服务
     */
    private void initDB(String dbname) {
        DataBaseMH dbService = new DataBaseMH(dbname);

        final List<Question> list = dbService.getQuestion();

        count = list.size();
        corrent = 0;

        Question q = list.get(0);
        tv_title.setText(q.getQuestionDetail());

        mRadioButton[0].setText(q.getOptionA());
        mRadioButton[1].setText(q.getOptionB());
        mRadioButton[2].setText(q.getOptionC());
        mRadioButton[3].setText(q.getOptionD());

        //上一题
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corrent > 0) {
                    corrent--;

                    Question q = list.get(corrent);

                    tv_title.setText(q.getQuestionDetail());

                    mRadioButton[0].setText(q.getOptionA());
                    mRadioButton[1].setText(q.getOptionB());
                    mRadioButton[2].setText(q.getOptionC());
                    mRadioButton[3].setText(q.getOptionD());

                    tv_result.setText(q.getRuslt());

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (q.getSelectAnswer() != -1) {
                        mRadioButton[q.getSelectAnswer()].setChecked(true);
                    }
                }

            }
        });

        //下一题
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为最后一题
                if (corrent < count - 1) {
                    corrent++;
                    Question q = list.get(corrent);

                    tv_title.setText(q.getQuestionDetail());

                    mRadioButton[0].setText(q.getOptionA());
                    mRadioButton[1].setText(q.getOptionB());
                    mRadioButton[2].setText(q.getOptionC());
                    mRadioButton[3].setText(q.getOptionD());

                    tv_result.setText(q.getRuslt());

                    mRadioGroup.clearCheck();

                    //设置选中
                    if (q.getSelectAnswer() != -1) {
                        mRadioButton[q.getSelectAnswer()].setChecked(true);
                    }
                } else if (corrent == count - 1 && wrongMode == true) {

                    new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("已经到达最后一道题，是否退出？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("取消", null).show();

                } else {
                    //没有题目了，开始检测正确性
                    final List<Integer> wrongList = checkAnswer(list);

                    if (wrongList.size() == 0) {
                        new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("你好厉害，答对了所有题！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).setNegativeButton("取消", null).show();
                    }

                    //窗口提示
                    new AlertDialog.Builder(MainActivity.this).setTitle("恭喜，答题完成！")
                            .setMessage("答对了" + (list.size() - wrongList.size()) + "道题" + "\n"
                                    + "答错了" + wrongList.size() + "道题" + "\n" + "是否查看错题？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            wrongMode = true;
                            List<Question> newList = new ArrayList<Question>();
                            for (int i = 0; i < wrongList.size(); i++) {
                                newList.add(list.get(wrongList.get(i)));
                            }
                            list.clear();
                            for (int i = 0; i < newList.size(); i++) {
                                list.add(newList.get(i));
                            }
                            corrent = 0;
                            count = list.size();

                            //更新当前显示的内容
                            Question q = list.get(corrent);

                            tv_title.setText(q.getQuestionDetail());

                            mRadioButton[0].setText(q.getOptionA());
                            mRadioButton[1].setText(q.getOptionB());
                            mRadioButton[2].setText(q.getOptionC());
                            mRadioButton[3].setText(q.getOptionD());

                            tv_result.setText(q.getRuslt());
                            //显示结果
                            tv_result.setVisibility(View.VISIBLE);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
                }
            }
        });

        //答案选中
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (mRadioButton[i].isChecked() == true) {
                        list.get(corrent).setSelectAnswer(i);
                        break;
                    }
                }
            }
        });
    }

    /**
     * 判断是否答题正确
     *
     * @param list
     * @return
     */
    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //判断对错
            if (list.get(i).getAnswer() != list.get(i).getSelectAnswer()) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }

}
