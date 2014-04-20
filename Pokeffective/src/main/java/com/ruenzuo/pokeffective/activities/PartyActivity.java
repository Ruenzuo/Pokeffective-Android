package com.ruenzuo.pokeffective.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.base.BaseActivity;
import com.ruenzuo.pokeffective.definitions.OnPartyMemberSelectedListener;
import com.ruenzuo.pokeffective.definitions.OnPartySelectionClearedListener;
import com.ruenzuo.pokeffective.fragments.PartyListFragment;
import com.ruenzuo.pokeffective.models.Pokemon;

import java.util.Hashtable;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class PartyActivity extends BaseActivity implements OnPartyMemberSelectedListener{

    private Hashtable selected;
    private RefreshActionItem progressItem;
    private OnPartySelectionClearedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        selected = new Hashtable();
        PartyListFragment fragment = (PartyListFragment) getFragmentManager().findFragmentById(R.id.partyListFragment);
        setSelectionClearedListener(fragment);
    }

    private void clearSelection() {
        Object[] selectedObjects = selected.values().toArray();
        int count = selectedObjects.length;
        for (int i = 0; i < count; i++) {
            Pokemon pokemon = (Pokemon) selectedObjects[i];
            pokemon.setSelected(false);
        }
    }

    @Override
    public void onBackPressed() {
        clearSelection();
        super.onBackPressed();
    }

    private void setSelectionClearedListener(Fragment fragment) {
        try {
            listener = (OnPartySelectionClearedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement OnPartySelectionClearedListener");
        }
    }

    @Override
    public void onPartyMemberSelected(Pokemon member) {
        if (member.isSelected()) {
            selected.put(member.getName(), member);
        }
        else {
            selected.remove(member.getName());
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.party_menu, menu);
        MenuItem progress = menu.findItem(R.id.action_progress);
        progressItem = (RefreshActionItem) progress.getActionView();
        progressItem.showProgress(true);
        progressItem.setProgress(selected.size());
        progressItem.setMax(3);
        progressItem.setProgressIndicatorType(ProgressIndicatorType.PIE);
        if (selected.size() >= 1) {
            menu.add(Menu.NONE, R.id.action_party_selection_clear, Menu.NONE, "Clear")
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_trash))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        if (selected.size() >= 3) {
            menu.add(Menu.NONE, R.id.action_party_selection_accept, Menu.NONE, "Accept")
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_navigation_accept))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            progress.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            clearSelection();
            finishAnimated();
            return true;
        }
        else if (id == R.id.action_party_selection_clear) {
            selected.clear();
            listener.onPartySelectionCleared();
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean shouldAllowSelection(Pokemon pokemon) {
        if (selected.size() >= 3) {
            if (selected.contains(pokemon)) {
                return true;
            }
            return false;
        }
        return true;
    }

}
