package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnMoveListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnMoveSelectedListener;
import com.ruenzuo.pokeffective.fragments.MoveListFragment;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveListActivity extends Activity implements OnMoveSelectedListener, MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {

    private OnMoveListSearchListener listSearchListener;
    private MenuItem filterItem;
    private MenuItem clearItem;
    private boolean filterActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_list_activity);
        Bundle extras = getIntent().getExtras();
        getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_action_back));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (extras != null) {
            Pokemon pokemon = (Pokemon) extras.getSerializable("Pokemon");
            getActionBar().setTitle(pokemon.getName());
            MoveListFragment fragment = MoveListFragment.newInstance(pokemon);
            setListSearchListener(fragment);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.moveListFragmentPlaceholder, fragment);
            transaction.commit();
        }
    }

    private void setListSearchListener(Fragment fragment) {
        try {
            listSearchListener = (OnMoveListSearchListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement OnMoveListSearchListener");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.move_list_menu, menu);
        if (filterActive) {
            menu.add(Menu.NONE, R.id.action_move_filter_clear, Menu.NONE, "Clear")
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_trash))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            clearItem = menu.findItem(R.id.action_pokemon_filter_clear);
        }
        MenuItem searchItem = menu.findItem(R.id.action_move_search);
        searchItem.setOnActionExpandListener(this);
        SearchView searchView = (SearchView)searchItem.getActionView();
        setupSearchView(searchView);
        filterItem = menu.findItem(R.id.action_move_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(RESULT_CANCELED, null);
            finish();
            return true;
        } else if (id == R.id.action_move_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.search_query_hint));
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setHintTextColor(Color.WHITE);
    }

    @Override
    public void onMoveSelected(Move move) {

    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        filterItem.setVisible(false);
        if (filterActive) {
            clearItem.setVisible(false);
        }
        listSearchListener.onSearchStart();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        filterItem.setVisible(true);
        if (filterActive) {
            clearItem.setVisible(true);
        }
        listSearchListener.onSearchCancel();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listSearchListener.onSearchQueryChange(newText);
        return false;
    }
}
