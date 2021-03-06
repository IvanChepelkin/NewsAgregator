package com.example.newsagregator.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
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
import android.widget.Toast;

import com.example.newsagregator.R;
import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.presenter.NewsView;
import com.example.newsagregator.view.dialogs.AddChannelDialog;
import com.example.newsagregator.view.dialogs.DeleteChannelDialog;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsView,
        SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.ItemListener,
        AddChannelDialog.ClickAddChannelDialog,
        DeleteChannelDialog.ClickOkDeleteChannelDialog {
    private static final int MY_REQUEST_CODE = 111;
    private static final String TAG_ADD_CHANNEL_DIALOG = "AddChannelDialog";
    private static final String TAG_DELETE_CHANNEL_DIALOG = "DeleteChannelDialog";
    public static final String KEY_channelsArray = "channelsArray";
    private SwipeRefreshLayout refreshLayout;
    private NewsPresenter newsPresenter;
    private RecyclerView recViewNews;
    private NewsAdapter newsAdapter;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ApplicationContextSingleton.setContext(this);
        initProviders();
        attachPresenter();
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
        newsAdapter = new NewsAdapter(this);
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
        else  if (id == R.id.get_out_profile){
            newsPresenter.setClickAuthenticationSignOut();
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
    public boolean isAuthUser() {

        boolean isAuth = false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            isAuth = true;
        }
        return isAuth;
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

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(guide));
    }

    @Override
    public void sendGuide(String guide) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");//задаем тип передаваемых данных
        intent.putExtra(Intent.EXTRA_TEXT, guide);
        String chooserTitle = getString(R.string.shareText);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        try {
            startActivity(chosenIntent);
        } catch (ActivityNotFoundException e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.noAppText, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void showErrorToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorRequest, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showErrorInvalidAddress() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorInvalidAdress, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showErrorNotCahnnelToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorNotFoundChannels, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showErrorIsChannelToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.you_have_are_channel_text, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showErrorChannelTodelete() {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.errorToDeleteChannel, Toast.LENGTH_LONG);
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
    public void onItemClick(int position) {
        newsPresenter.setClickItemNews(position);
    }

    @Override
    public void onSendButtonCClick(int position) {
        newsPresenter.setClickSendGuideButton(position);
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

    private void initProviders() {
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
    }

    @Override
    public void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(), MY_REQUEST_CODE
        );
    }

    @Override
    public void showErrorAuthentication(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AuthenticationSignOut() {
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showSignInOptions();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public void initActivityLayoutAndViews() {
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                newsPresenter.authenticationIssuccessful();
            } else {
                newsPresenter.authenticationIsFailure(response.getError().getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        newsPresenter.detachView();
        ApplicationContextSingleton.setContext(null);
        super.onDestroy();
    }
}
