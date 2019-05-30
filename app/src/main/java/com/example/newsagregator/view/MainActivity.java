package com.example.newsagregator.view;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.newsagregator.R;
import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.presenter.NewsView;
import com.example.newsagregator.view.dialogs.AddChannelDialog;
import com.example.newsagregator.view.dialogs.DeleteChannelDialog;
import com.example.newsagregator.view.dialogs.MainContentDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsView,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.ItemListener,
        AddChannelDialog.ClickAddChannelDialog,
        DeleteChannelDialog.ClickOkDeleteChannelDialog {

    private static final String TAG_ADD_CHANNEL_DIALOG = "AddChannelDialog";
    private static final String TAG_DELETE_CHANNEL_DIALOG = "DeleteChannelDialog";
    private static final String TAG_MAIN_CONTENT_DIALOG = "MainContentDialog";
    public static final String KEY_channelsArray = "channelsArray";
    public static final String KEY_News_guide = "newsGuide";
    private SwipeRefreshLayout refreshLayout;
    private NewsPresenter newsPresenter;
    private RecyclerView recViewNews;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ApplicationContextSingleton.setContext(this);
        attachPresenter();
        newsAdapter = new NewsAdapter(this);
        loadDataDeeplink();

    }

    private void loadDataDeeplink() {
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            newsPresenter.setClickOkAddChannels(intent.getData().toString());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getData() != null) {
            newsPresenter.setClickOkAddChannels(intent.getData().toString());
        }
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

    private void attachPresenter() {
        newsPresenter = (NewsPresenter) getLastCustomNonConfigurationInstance();
        if (newsPresenter == null) {
            newsPresenter = Factory.createObjectNewsPresenter();
        }
        newsPresenter.onAttachView(this);
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
        if (id == R.id.news_add_channel) {
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
        newsAdapter.setListNewsItem(listNewsItem);
        recViewNews.setAdapter(newsAdapter);
    }

    @Override
    public void clearList() {
        List<NewsItem> listNewsItem = new ArrayList<>();
        listNewsItem.clear();
        newsAdapter.setListNewsItem(listNewsItem);
        newsAdapter.notifyDataSetChanged();
        recViewNews.setAdapter(newsAdapter);
    }

    @Override
    public void showAlertDialogAddChannel() {
        AddChannelDialog addChannelDialog = new AddChannelDialog();
        addChannelDialog.show(getSupportFragmentManager(), TAG_ADD_CHANNEL_DIALOG);
    }

    @Override
    public void showAlertDialogDeleteChannel(String[] channelsArray) {
        DeleteChannelDialog deleteChannelDialog = new DeleteChannelDialog();
        Bundle data = new Bundle();
        data.putStringArray(KEY_channelsArray, channelsArray);
        deleteChannelDialog.setArguments(data);
        deleteChannelDialog.show(getSupportFragmentManager(), TAG_DELETE_CHANNEL_DIALOG);
    }
    @Override
    public void showMainConent(String guide) {
        MainContentDialog mainContentDialog = new MainContentDialog();
        Bundle data = new Bundle();
        data.putString(KEY_News_guide, guide);
        mainContentDialog.setArguments(data);
        mainContentDialog.show(getSupportFragmentManager(), TAG_MAIN_CONTENT_DIALOG);

    }

    @Override
    public void showErrorToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorInvalidAdress, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showNotCahnnelToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorNotFoundChannels, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showIsChannelToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.you_have_are_channel_text, Toast.LENGTH_LONG);
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


    @Override
    public void onRefresh() {
        newsPresenter.updateNews();
    }

    @Override
    public void onItemClick(int position, WebView webViewContent) {
        newsPresenter.setClickItemNews(position);
    }

    @Override
    public void setClickOkAddChannel(String saveUrlChannel) {
        newsPresenter.setClickOkAddChannels(saveUrlChannel);
    }

    @Override
    public void setClickOkAddChannel(boolean[] positionChannelToDelete) {
        newsPresenter.setClickOkDeleteChannels(positionChannelToDelete);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return newsPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.detachView();
        ApplicationContextSingleton.setContext(null);

    }
}
