package com.example.myapplication.edit;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.data.db.AppDatabase;
import com.example.myapplication.data.db.entity.Patients;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.Util;

import java.util.Date;


public class EditActivity extends AppCompatActivity implements EditContract.View, EditContract.DateListener {

    private EditContract.Presenter mPresenter;

    private EditText mNameEditText;
    private EditText mAddressEditText;
    private EditText mEmailEditText;
    private EditText mBirthdayEditText;
    private EditText mPhoneEditText;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mAddressInputLayout;
    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mBirthdayInputLayout;
    private TextInputLayout mPhoneTextInputLayout;
    private TextInputLayout mHeightLayout;
    private TextInputLayout mWeightLayout;

    private FloatingActionButton mFab;

    private Patients patients;
    private boolean mEditMode = false;
    private EditText mHeightEditText,mWeightEditText, mBloodgroupEditText;
    private TextInputLayout mBloodgroupTextInputLayout;
    private TextInputLayout mHeightTextInputLayout;
    private TextInputLayout mWeightTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        patients = new Patients();
        checkMode();

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new EditPresenter(this, db.patientModel());

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mEditMode) {
            mPresenter.getPatientsAndPopulate(patients.id);
        }
    }

    private void checkMode() {
        if (getIntent().getExtras() != null) {
            patients.id = getIntent().getLongExtra(Constants.PERSON_ID, 0);
            mEditMode = true;
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mAddressEditText = (EditText) findViewById(R.id.addressEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mBirthdayEditText = (EditText) findViewById(R.id.birthdayEditText);
        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mHeightEditText = (EditText) findViewById(R.id.heightEditText);
        mWeightEditText = (EditText) findViewById(R.id.weightEditText);
        mBloodgroupEditText = (EditText) findViewById(R.id.bloodGroupEditText);


        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        mAddressInputLayout = (TextInputLayout) findViewById(R.id.addressTextInputLayout);
        mEmailInputLayout = (TextInputLayout) findViewById(R.id.emailTextInputLayout);
        mBirthdayInputLayout = (TextInputLayout) findViewById(R.id.birthdayTextInputLayout);
        mBloodgroupTextInputLayout = (TextInputLayout) findViewById(R.id.bloodGroupTextInputLayout);
        mHeightTextInputLayout = (TextInputLayout) findViewById(R.id.heightTextInputLayout);
        mWeightTextInputLayout = (TextInputLayout) findViewById(R.id.weightInputLayout);

        mBirthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showDateDialog();
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setImageResource(mEditMode ? R.drawable.ic_refresh : R.drawable.ic_done);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                patients.name = mNameEditText.getText().toString();
                patients.address = mAddressEditText.getText().toString();
                patients.email = mEmailEditText.getText().toString();
                patients.phone = mPhoneEditText.getText().toString();
                patients.bloodGroup=mBloodgroupEditText.getText().toString();
                patients.height=mHeightEditText.getText().toString();
                patients.weight=mWeightEditText.getText().toString();

                boolean valid = mPresenter.validate(patients);

                if (!valid) return;

                if (mEditMode) {
                    mPresenter.update(patients);
                } else {
                    mPresenter.save(patients);
                    mPresenter.startWorkManager();
                }
            }
        });
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorMessage(int field) {
        if (field == Constants.FIELD_NAME) {
            mNameTextInputLayout.setError(getString(R.string.invalid_name));
        } else if (field == Constants.FIELD_EMAIL) {
            mEmailInputLayout.setError(getString(R.string.invalid_email));
        } else if (field == Constants.FIELD_PHONE) {
            mPhoneTextInputLayout.setError(getString(R.string.invalid_phone));
        } else if (field == Constants.FIELD_ADDRESS) {
            mAddressInputLayout.setError(getString(R.string.invalid_address));
        } else if (field == Constants.FIELD_BIRTHDAY) {
            mBirthdayInputLayout.setError(getString(R.string.invalid_birthday));
        }else if (field == Constants.FIELD_BLOODGROUP) {
            mBirthdayInputLayout.setError(getString(R.string.invalid_blood));
        }else if (field == Constants.FIELD_WEIGHT) {
            mWeightTextInputLayout.setError(getString(R.string.invalid_weight));
        }else if (field == Constants.FIELD_HEIGHT) {
            mHeightTextInputLayout.setError(getString(R.string.invalid_height));
        }
    }


    @Override
    public void openDateDialog() {
        DateDialogFragment fragment = new DateDialogFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void close() {
        finish();
    }




    @Override
    public void populate(Patients patients) {
        this.patients = patients;
        mNameEditText.setText(patients.name);
        mAddressEditText.setText(patients.address);
        mEmailEditText.setText(patients.email);
        mBirthdayEditText.setText(Util.format(patients.birthday));
        mPhoneEditText.setText(patients.phone);
        mHeightEditText.setText(patients.height);
        mWeightEditText.setText(patients.weight);
        mBloodgroupEditText.setText(patients.bloodGroup);
    }

    @Override
    public void setSelectedDate(Date date) {
        patients.birthday = date;
        mBirthdayEditText.setText(Util.format(date));
    }
}
