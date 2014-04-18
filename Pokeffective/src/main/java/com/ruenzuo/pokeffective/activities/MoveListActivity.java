package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;
import com.ruenzuo.pokeffective.definitions.OnMoveFilterChangedListener;
import com.ruenzuo.pokeffective.definitions.OnMoveListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnMoveSelectedListener;
import com.ruenzuo.pokeffective.fragments.FilterDialogFragment;
import com.ruenzuo.pokeffective.fragments.MoveListFragment;
import com.ruenzuo.pokeffective.models.FilterOption;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveListActivity extends Activity implements OnMoveSelectedListener, MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener, OnFilterOptionChangedListener {

    private OnMoveListSearchListener listSearchListener;
    private OnMoveFilterChangedListener moveFilterListener;
    private MenuItem filterItem;
    private MenuItem clearItem;
    private boolean filterActive;
    private FilterOption learnMethodFilterOption;
    private FilterOption moveTypeFilterOption;
    private FilterOption categoryFilterOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_list_activity);filterActive = false;
        learnMethodFilterOption = FilterOption.defaultMoveLearnMethodFilterOption();
        moveTypeFilterOption = FilterOption.defaultMoveTypeFilterOption();
        categoryFilterOption = FilterOption.defaultMoveCategoryFilterOption();
        getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_action_back));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Pokemon pokemon = (Pokemon) extras.getSerializable("Pokemon");
            getActionBar().setTitle(pokemon.getName());
            MoveListFragment fragment = MoveListFragment.newInstance(pokemon);
            setListSearchListener(fragment);
            setMoveFilterListener(fragment);
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

    private void setMoveFilterListener(Fragment fragment) {
        try {
            moveFilterListener = (OnMoveFilterChangedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement OnMoveFilterChangedListener");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.move_list_menu, menu);
        if (filterActive) {
            menu.add(Menu.NONE, R.id.action_move_filter_clear, Menu.NONE, "Clear")
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_trash))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            clearItem = menu.findItem(R.id.action_move_filter_clear);
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
        }
        else if (id == R.id.action_move_search) {
            return true;
        }
        else if (id == R.id.action_move_filter_category) {
            FilterOption option = categoryFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
            return true;
        }
        else if (id == R.id.action_move_filter_learn_method) {
            FilterOption option = learnMethodFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
        }
        else if (id == R.id.action_move_filter_type) {
            FilterOption option = moveTypeFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
        }
        else if (id == R.id.action_move_filter_clear) {
            categoryFilterOption = FilterOption.defaultMoveCategoryFilterOption();
            learnMethodFilterOption = FilterOption.defaultMoveLearnMethodFilterOption();
            moveTypeFilterOption = FilterOption.defaultMoveTypeFilterOption();
            moveFilterListener.onMoveFilterChanged((MoveCategory) categoryFilterOption.getValue(),
                    (MoveLearnMethod) learnMethodFilterOption.getValue(), (PokemonType) moveTypeFilterOption.getValue());
            filterActive = false;
            invalidateOptionsMenu();
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
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Move", move);
        setResult(RESULT_OK, returnIntent);
        finish();
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

    @Override
    public void onFilterOptionChanged(FilterOption filterOption) {
        filterActive = true;
        invalidateOptionsMenu();
        switch (filterOption.getFilterType()) {
            case MOVE_CATEGORY:
                categoryFilterOption = filterOption;
                break;
            case MOVE_LEARN_METHOD:
                learnMethodFilterOption = filterOption;
                break;
            case MOVE_TYPE:
                moveTypeFilterOption = filterOption;
                break;
            default:
                break;
        }
        moveFilterListener.onMoveFilterChanged((MoveCategory) categoryFilterOption.getValue(),
                (MoveLearnMethod) learnMethodFilterOption.getValue(), (PokemonType) moveTypeFilterOption.getValue());
    }
}
