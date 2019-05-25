package com.roshan.dev;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.roshan.dev.beans.EventModel;
import com.roshan.dev.databinding.ActivityCreateEditEventBinding;
import com.roshan.dev.eventlisitng.EventsListingActivity;
import com.roshan.dev.viewmodel.EventViewModel;

public class EventCreateUpdateActivity extends AppCompatActivity {
    public static final String TAG = EventsListingActivity.class.getSimpleName();
    public static final String KEY_DATA = "key_args_data";
    private ActivityCreateEditEventBinding binding;
    private EventModel model;
    private String screenTitle;
    private EventViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_event);
        binding.setActivity(this);
        viewModel = ViewModelProvider.AndroidViewModelFactory.
                getInstance(getApplication()).create(EventViewModel.class);
        handleArgs();
        setupToolbar();
        setUpView();
    }


    private void setUpView() {
        binding.btnCreateEditEvent.setText(screenTitle);
    }

    private void handleArgs() {
        if (null != getIntent()) {
            model = getIntent().getParcelableExtra(KEY_DATA);
            screenTitle = getString(R.string.updateEventText);
        }
        if (null == model) {
            model = new EventModel();
            model.setIsCreateEvent(true);
            screenTitle = getString(R.string.createEventText);
        }
        binding.setModel(model);
    }

    public void createUpdateEvent(EventModel eventModel) {
        if (isValidated(eventModel)) {
            String toastMessage;
            if (eventModel.isCreateEvent()) {
                viewModel.insertEvent(this, eventModel);
                toastMessage = getResources().getString(R.string.event_created_success);
            } else {
                viewModel.updateEvent(this, eventModel);
                toastMessage = getResources().getString(R.string.event_updated_success);
            }
            Intent intent = new Intent(this, EventsListingActivity.class);
            intent.putExtra(KEY_DATA, eventModel);
            setResult(Activity.RESULT_OK, intent);
            finish();
            AppUtils.showToastMessage(this, toastMessage);
        }
    }

    private boolean isValidated(EventModel eventModel) {
        boolean isStatus = false;
        if (TextUtils.isEmpty(eventModel.getEventName())) {
            binding.textInputEventName.setError("Please enter event name.");
        } else if (TextUtils.isEmpty(eventModel.getEventDescription())) {
            binding.textInputEventName.setError(null);
            binding.textInputEventDesc.setError("Please enter event description.");
        } else {
            binding.textInputEventDesc.setError(null);
            isStatus = true;
        }
        return isStatus;
    }


    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(screenTitle);

        }
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


}
