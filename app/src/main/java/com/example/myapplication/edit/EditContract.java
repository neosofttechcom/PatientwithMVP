package com.example.myapplication.edit;

import com.example.myapplication.BasePresenter;
import com.example.myapplication.BaseView;
import com.example.myapplication.data.db.entity.Patients;

import java.util.Date;



public interface EditContract {

    interface Presenter extends BasePresenter {
        void save(Patients person);

        boolean validate(Patients person);

        void showDateDialog();

        void getPatientsAndPopulate(long id);


        void update(Patients person);
void startWorkManager();

    }

    interface View extends BaseView<Presenter> {

        void showErrorMessage(int field);

        void clearPreErrors();

        void openDateDialog();

        void close();

        void populate(Patients person);
    }

    interface DateListener {

        void setSelectedDate(Date date);

    }
}
