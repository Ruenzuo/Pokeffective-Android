package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.BoxAdapter;
import com.ruenzuo.pokeffective.definitions.OnChoiceSelectedListener;
import com.ruenzuo.pokeffective.fragments.ChoiceDialogFragment;
import com.ruenzuo.pokeffective.fragments.InfoDialogFragment;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.BoxTask;
import com.ruenzuo.pokeffective.utils.PreferencesUtils;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.robotmedia.billing.BillingRequest;
import net.robotmedia.billing.helper.AbstractBillingActivity;
import net.robotmedia.billing.model.Transaction;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class BoxActivity extends AbstractBillingActivity implements OnChoiceSelectedListener {

    private GridView gridView;
    private static final int POKEMON_REQUEST_CODE = 1;
    private static final int INVALID_POSITION = -1;
    private int lastHoldPosition;
    private boolean billingSupported;

    private void setBillingSupported(boolean billingSupported) {
        this.billingSupported = billingSupported;
    }

    private boolean isBillingSupported() {
        return billingSupported;
    }

    @Override
    public void onBillingChecked(boolean supported) {
        setBillingSupported(supported);
    }

    @Override
    public void onSubscriptionChecked(boolean supported) {
        if (supported) {
            Log.i("Pokeffective", "Subscription is supported, although is not used at this time.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBillingSupported(false);
        lastHoldPosition = INVALID_POSITION;
        setContentView(R.layout.box_activity);
        getActionBar().setTitle("Box");
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setEmptyView(findViewById(R.id.txtViewNoBox));
        BoxAdapter adapter = new BoxAdapter(this, R.layout.member_item, new ArrayList<Pokemon>());
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(gridView);
        gridView.setAdapter(swingRightInAnimationAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lastHoldPosition = position;
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter) listAdapter.getDecoratedBaseAdapter();
                Pokemon pokemon = adapter.get(position);
                String[] choices = {"OK", "Cancel"};
                ChoiceDialogFragment dialog = ChoiceDialogFragment.newInstance("Confirm", "Remove " + pokemon.getName() + " from box?", choices);
                dialog.show(getFragmentManager(), "ChoiceDialogFragment");
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter) listAdapter.getDecoratedBaseAdapter();
                Pokemon pokemon = adapter.get(position);
                Intent intent = new Intent(getApplicationContext(), MovesetListActivity.class);
                intent.putExtra("Pokemon", pokemon);
                startActivity(intent);
            }
        });
        Groundy.create(BoxTask.class)
                .callback(this)
                .queueUsing(this);
        checkForUpdates();
    }

    @Override
    public void onPurchaseStateChanged(String itemId, Transaction.PurchaseState state) {
        switch (state) {
            case PURCHASED: {
                if (!PreferencesUtils.isUnlimitedBoxStorageEnabled(this)) {
                    PreferencesUtils.enableUnlimitedBoxStorage(true, this);
                    Toast toast = Toast.makeText(this, "Purchase restored.", Toast.LENGTH_SHORT);
                    toast.show();
                    invalidateOptionsMenu();
                }
                break;
            }
            default: {
                PreferencesUtils.enableUnlimitedBoxStorage(false, this);
            }
        }
    }

    @Override
    public void onRequestPurchaseResponse(String itemId, BillingRequest.ResponseCode response) {
        if (response == BillingRequest.ResponseCode.RESULT_OK) {
            if (!PreferencesUtils.isUnlimitedBoxStorageEnabled(this)) {
                PreferencesUtils.enableUnlimitedBoxStorage(true, this);
                Toast toast = Toast.makeText(this, "Purchase successful.", Toast.LENGTH_SHORT);
                toast.show();
                invalidateOptionsMenu();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    private void checkForCrashes() {
        CrashManager.register(this, "63f2eda0ed13b522abc43ff58f28d179");
    }

    private void checkForUpdates() {
        UpdateManager.register(this, "63f2eda0ed13b522abc43ff58f28d179");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.box_menu, menu);
        MenuItem restore = menu.findItem(R.id.action_box_restore);
        if (PreferencesUtils.isUnlimitedBoxStorageEnabled(this)) {
            restore.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_box_add) {
            Intent intent = new Intent(getApplicationContext(), PokemonListActivity.class);
            startActivityForResult(intent, POKEMON_REQUEST_CODE);
            return true;
        }
        else if (id == R.id.action_balance) {
            if (isBoxValid()) {
                Intent intent = new Intent(getApplicationContext(), PartyActivity.class);
                startActivity(intent);
            }
            else {
                Toast toast = Toast.makeText(this, "You need at least three pókemon with four moves each one in your box to analyze a party.", Toast.LENGTH_LONG);
                toast.show();
            }
            return true;
        }
        else if (id == R.id.action_info) {
            DialogFragment dialog = new InfoDialogFragment();
            dialog.show(getFragmentManager(), "InfoDialogFragment");
            return true;
        }
        else if (id == R.id.action_box_restore) {
            if (isBillingSupported()) {
                requestPurchase("com.ruenzuo.pokeffective.storage");
            }
            else {
                Toast toast = Toast.makeText(this, "Billing is not supported on this device.", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isBoxValid() {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
        BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
        int validPKMN = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            Pokemon pokemon = adapter.get(i);
            if (pokemon.moves().size() >= 4) {
                validPKMN++;
            }
        }
        return validPKMN >= 3;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POKEMON_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Pokemon pokemon = (Pokemon)data.getExtras().get("Pokemon");
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
                int count = adapter.getCount();
                if (!PreferencesUtils.isUnlimitedBoxStorageEnabled(this)) {
                    if (count >= 6) {
                        Toast toast = Toast.makeText(this, "You can't save more than six pokémon in your box. Remove one first in order to add another or buy unlimited space.", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                }
                for(int i = 0; i < count; i++) {
                    Pokemon stored = adapter.get(i);
                    if (stored.getIdentifier() == pokemon.getIdentifier()) {
                        Toast toast = Toast.makeText(this, "You can't save the same pokémon twice in your box.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }
                pokemon.save();
                ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
                pokemons.add(pokemon);
                addToAdapter(pokemons, false);
            }
        }
    }

    @OnSuccess(BoxTask.class)
    public void onSuccess(@Param("Box") ArrayList<Pokemon> box) {
        addToAdapter(box, true);
    }

    private void addToAdapter(ArrayList<Pokemon> pokemons, boolean shouldClear) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
        BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
        if (shouldClear) {
            adapter.clear();
            listAdapter.setShouldAnimateFromPosition(0);
        }
        else {
            listAdapter.setShouldAnimateFromPosition(adapter.getCount());
        }
        adapter.addAll(pokemons);
        adapter.notifyDataSetChanged();
        if (shouldClear) {
            gridView.setSelection(0);
        }
        else {
            gridView.smoothScrollToPosition(adapter.getCount());
        }
    }

    @Override
    public void onChoiceSelected(boolean selected) {
        if (selected && lastHoldPosition != INVALID_POSITION) {
            SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
            BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
            Pokemon pokemon = adapter.get(lastHoldPosition);
            pokemon.delete();
            adapter.remove(lastHoldPosition);
            listAdapter.setShouldAnimateFromPosition(lastHoldPosition);
            adapter.notifyDataSetChanged();
            gridView.smoothScrollToPosition(lastHoldPosition);
            lastHoldPosition = INVALID_POSITION;
        }
    }

    @Override
    public byte[] getObfuscationSalt() {
        byte[] salt = {-84, 21, -86, 11, -31, 96, -49, -24, 100, -30, -3, -37, 68, 58, -74, -73, -58, 83, -92, -75};
        return salt;
    }

    @Override
    public String getPublicKey() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtTMdyX1e/6wM0wlk73ZXzvb7WEP7BmCcaAUEAgpK7euGr+3L61RZdVVTuHe1oP9r3wykDXLalIIqxLuVJ9y6XM2OLMpEGFiozGw80gGgFXezDXAs84Ie6rxYu3WwzrFYxnaDueKysK6PSox44llLYBtuAgbvcrWaLDttiuZeNnPo4ZY+kfdM5JUGsij9eHbiGFmr6aU/uMC0nNGop3MVueu8qitt1J9U0FuppXjS7l6avuC4ouZRc4axuzLHOPcrSwhkyPmq2l64E4ylijwtlwiY9j3lKOiPi3V9cctF567grPdnsMQlGDWsFZt4OLXUALNnesjR5mKUdzfjGSfNlwIDAQAB";
    }
}
