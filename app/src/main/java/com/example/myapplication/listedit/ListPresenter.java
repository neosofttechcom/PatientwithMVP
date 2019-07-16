package com.example.myapplication.listedit;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

import com.example.myapplication.data.db.dao.PersonDao;
import com.example.myapplication.data.db.entity.Patients;

import java.util.List;




public class ListPresenter implements ListContract.Presenter {

    private final ListContract.View mView;
    private final PersonDao personDao;

    public ListPresenter(ListContract.View view, PersonDao personDao) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.personDao = personDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void addNewPerson() {
        mView.showAddPerson();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void populatePeople() {
        personDao.findAllPersons().observeForever(new Observer<List<Patients>>() {
            @Override
            public void onChanged(@Nullable List<Patients> persons) {
                mView.setPersons(persons);
                if (persons == null || persons.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void openEditScreen(Patients person) {
        mView.showEditScreen(person.id);
    }

    @Override
    public void openConfirmDeleteDialog(Patients person) {
        mView.showDeleteConfirmDialog(person);
    }

    @Override
    public void delete(long personId) {
        Patients person = personDao.findPerson(personId);
        personDao.deletePerson(person);
    }
}
