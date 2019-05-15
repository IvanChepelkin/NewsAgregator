package com.example.newsagregator.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.InputType;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsagregator.R;
import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.News.NewsItem;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.presenter.NewsView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsView, SwipeRefreshLayout.OnRefreshListener, NewsAdapter.ItemListener {
    private SwipeRefreshLayout refreshLayout;
    private NewsPresenter newsPresenter;
    private RecyclerView recViewNews;
    private WebView webViewContent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ApplicationContextSingleton.setContext(this);
        newsPresenter = Factory.createObjectNewsPresenter();
        newsPresenter.onAttach(this);
    }

    private void initViews() {
        recViewNews = findViewById(R.id.newsRecyclerView);
        recViewNews.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.news_list_channels) {

        } else if (id == R.id.news_add_channel) {
            newsPresenter.setClickAddChannel();

        } else if (id == R.id.news_delete_channel) {
            newsPresenter.setClickDeleteChannel();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showNews(List<NewsItem> listNewsItem) {
        recViewNews.setHasFixedSize(false);
        NewsAdapter newsAdapter = new NewsAdapter(this,listNewsItem);
        recViewNews.setAdapter(newsAdapter);
    }

    @Override
    public void showAlertDialogAddChannel() {
        AlertDialog.Builder addChannelDialog = new AlertDialog.Builder(this);
        addChannelDialog.setTitle("Введите адрес канала"); //literals

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText("http://www.free-lance.ru/rss/projects.xml");
        addChannelDialog.setView(input);
        addChannelDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newsPresenter.setClickOkAddChannels(input.getText().toString());
            }
        });
        addChannelDialog.show();
    }

    @Override
    public void showAlertDialogDeleteChannel(String[] channelsArray) {

        final boolean[] positionCheckboxArray = new boolean[channelsArray.length];
        for (int i = 0; i < positionCheckboxArray.length; i++) {
            positionCheckboxArray[i] = false;
        }

        AlertDialog.Builder deleteChannelsDialog = new AlertDialog.Builder(this);
        deleteChannelsDialog.setTitle("Выберите канал");

        final EditText inputs = new EditText(this);
        inputs.setInputType(InputType.TYPE_CLASS_TEXT);
        deleteChannelsDialog.setView(inputs);
        deleteChannelsDialog.setMultiChoiceItems(channelsArray, positionCheckboxArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                positionCheckboxArray[which] = true;
            }
        });
        deleteChannelsDialog.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newsPresenter.setClickOkDeleteChannels(positionCheckboxArray);
            }
        });
        deleteChannelsDialog.show();
    }

    @Override
    public void showError(String error) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ошибка запроса", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void showMainConent(String guid) {
        webViewContent.getSettings().setJavaScriptEnabled(true);
        webViewContent.loadUrl(guid);
    }

    @Override
    public void showAlertDialogFiltrChannels() {

//        final boolean[] positionCheckboxArray = new boolean[channelsArray.length];
//        for (int i = 0; i < positionCheckboxArray.length; i++) {
//            positionCheckboxArray[i] = false;
//        }
//
//        AlertDialog.Builder deleteChannelsDialog = new AlertDialog.Builder(this);
//        deleteChannelsDialog.setTitle("Выберите канал");
//
//        final EditText inputs = new EditText(this);
//        inputs.setInputType(InputType.TYPE_CLASS_TEXT);
//        deleteChannelsDialog.setView(inputs);
//        deleteChannelsDialog.setMultiChoiceItems(channelsArray, positionCheckboxArray, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                positionCheckboxArray[which] = true;
//            }
//        });
//        deleteChannelsDialog.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                newsPresenter.setClickOkDeleteChannels(positionCheckboxArray);
//            }
//        });
//        deleteChannelsDialog.show();
    }

    @Override
    public void onRefresh() {
        newsPresenter.updateNews();
    }

    @Override
    public void onItemClick(int position, WebView webViewContent) {
        this.webViewContent = webViewContent;
        newsPresenter.setClickItemNews(position);
    }
}
