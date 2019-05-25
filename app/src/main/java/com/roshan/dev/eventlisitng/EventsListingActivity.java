package com.roshan.dev.eventlisitng;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.roshan.dev.EventCreateUpdateActivity;
import com.roshan.dev.R;
import com.roshan.dev.beans.EventModel;
import com.roshan.dev.callback.OnItemClickListener;
import com.roshan.dev.databinding.ActivityEventListingBinding;
import com.roshan.dev.eventlisitng.adapter.EventListAdapter;
import com.roshan.dev.viewmodel.EventViewModel;

import java.util.List;
import java.util.Objects;

public class EventsListingActivity extends AppCompatActivity {
    public static final String TAG = EventsListingActivity.class.getSimpleName();
    private final int REQUEST_CODE = 101;
    private ActivityEventListingBinding binding;
    private EventListAdapter adapter;
    private EventViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_listing);
        binding.setActivity(this);
        viewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getApplication()).create(EventViewModel.class);
        setupToolbar();
        setUpList();
    }

    /**
     * Preparing List
     */
    private void setUpList() {
        adapter = new EventListAdapter(viewModel.getEventsList(this));
        adapter.setOnItemClickListener(new OnItemClickListener<EventModel>() {
            @Override
            public void onItemClick(View v, EventModel model) {
                if (v.getId() == R.id.btnDeleteEvent) {
                    removeEvent(model);
                } else if (v.getId() == R.id.btnEditEvent) {
                    navigateToCreateUpdateEvent(model);
                }
            }
        });
        binding.rvTodo.setAdapter(adapter);
        showNoDataFound();
    }

    private void showNoDataFound() {
        binding.tvNoDataFound.setVisibility(adapter.getItemList().isEmpty() ? View.VISIBLE : View.GONE);
    }


    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    public void navigateToCreateUpdateEvent(EventModel model) {
        Intent intent = new Intent(this, EventCreateUpdateActivity.class);
        if (null != model) {
            intent.putExtra(EventCreateUpdateActivity.KEY_DATA, model);
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void removeEvent(EventModel model) {
        viewModel.deleteEvent(this, model.getEventId());
        adapter.getItemList().remove(model);
        adapter.notifyDataSetChanged();
        showNoDataFound();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            EventModel eventModel = Objects.requireNonNull(data).getParcelableExtra(EventCreateUpdateActivity.KEY_DATA);
            if (eventModel.isCreateEvent()) {
                eventModel.setIsCreateEvent(false);
                adapter.getItemList().add(eventModel);
            } else {
                //update List
                List<EventModel> modelList = adapter.getItemList();
                for (int i = 0; i < modelList.size(); i++) {
                    EventModel model = modelList.get(i);
                    if (model.getEventId() == eventModel.getEventId()) {
                        adapter.getItemList().set(i, eventModel);
                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
        showNoDataFound();
    }
}
