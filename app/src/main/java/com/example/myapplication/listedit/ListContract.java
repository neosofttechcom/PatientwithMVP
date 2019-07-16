package com.example.myapplication.listedit;

import com.example.myapplication.BasePresenter;
import com.example.myapplication.BaseView;
import com.example.myapplication.data.db.entity.Patients;

import java.util.List;





public interface ListContract {

    interface Presenter extends BasePresenter {

        void addNewPerson();

        void result(int requestCode, int resultCode);

        void populatePeople();

        void openEditScreen(Patients person);

        void openConfirmDeleteDialog(Patients person);

        void delete(long personId);
    }

    interface View extends BaseView<Presenter> {

        void showAddPerson();

        void setPersons(List<Patients> persons);

        void showEditScreen(long id);

        void showDeleteConfirmDialog(Patients person);

        void showEmptyMessage();
    }

    interface OnItemClickListener {

        void clickItem(Patients person);

        void clickLongItem(Patients person);
    }

    interface DeleteListener {

        void setConfirm(boolean confirm, long personId);

    }
}
