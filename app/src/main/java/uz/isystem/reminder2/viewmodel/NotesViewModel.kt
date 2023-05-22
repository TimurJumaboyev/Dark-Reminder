package uz.isystem.reminder2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uz.isystem.reminder2.db.NotesDatabase
import uz.isystem.reminder2.model.Notes
import uz.isystem.reminder2.repo.NotesRepository


class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository:NotesRepository

    init {
        val dao=NotesDatabase.getDataBaseInstance(application).myNotesDao()
        repository= NotesRepository(dao)
    }

    fun addNotes(notes: Notes){
        repository.insertNotes(notes)
    }
    fun getNotes():LiveData<List<Notes>> = repository.getAllNotes()


    fun getCompleted():LiveData<List<Notes>>{
        return repository.getCompletedNotes()
    }

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }
    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }

    /*
    fun getHighNotes():LiveData<List<Notes>>{
        return repository.getHighNotes()
    }
    fun getMediumNotes():LiveData<List<Notes>> = repository.getMediumNotes()

    fun getLowNotes():LiveData<List<Notes>>{
        return repository.getLowNotes()
    }

     */

}