package com.example.myapplication.edit;


import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.data.db.dao.PersonDao;
import com.example.myapplication.data.db.entity.Patients;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.MyWorker;
import com.example.myapplication.utils.Util;

import java.util.concurrent.TimeUnit;

public class EditPresenter implements EditContract.Presenter {

    private final EditContract.View mView;
    private final PersonDao personDao;

    public EditPresenter(EditContract.View mMainView, PersonDao personDao) {
        this.mView = mMainView;
        this.mView.setPresenter(this);
        this.personDao = personDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void save(Patients person) {
        long ids = this.personDao.insertPerson(person);
        mView.close();
    }

    @Override
    public boolean validate(Patients person) {
        if (person.name.isEmpty() || !Util.isValidName(person.name)) {
            mView.showErrorMessage(Constants.FIELD_NAME);
            return false;
        }
        if (person.address.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_ADDRESS);
            return false;
        }
        if (person.phone.isEmpty() || !Util.isValidPhone(person.phone)) {
            mView.showErrorMessage(Constants.FIELD_PHONE);
            return false;
        }
        if (person.email.isEmpty() || !Util.isValidEmail(person.email)) {
            mView.showErrorMessage(Constants.FIELD_EMAIL);
            return false;
        }   if (person.bloodGroup.isEmpty() ) {
            mView.showErrorMessage(Constants.FIELD_BLOODGROUP);
            return false;
        }if (person.weight.isEmpty() ) {
            mView.showErrorMessage(Constants.FIELD_WEIGHT);
            return false;
        }if (person.height.isEmpty() ) {
            mView.showErrorMessage(Constants.FIELD_HEIGHT);
            return false;
        }


        if (person.birthday == null) {
            mView.showErrorMessage(Constants.FIELD_BIRTHDAY);
            return false;
        }


        return true;
    }

    @Override
    public void showDateDialog() {
        mView.openDateDialog();
    }

    @Override
    public void getPatientsAndPopulate(long id) {
        Patients person = personDao.findPerson(id);
        if (person != null) {
            mView.populate(person);
        }
    }

    @Override
    public void update(Patients person) {
        int ids = this.personDao.updatePerson(person);
        mView.close();
    }

    @Override
    public void startWorkManager() {
        // This wil uplaod data to the server periodically
        PeriodicWorkRequest compressionWork =
                new PeriodicWorkRequest.Builder(MyWorker.class, 20, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance().enqueue(compressionWork);
    }
}
