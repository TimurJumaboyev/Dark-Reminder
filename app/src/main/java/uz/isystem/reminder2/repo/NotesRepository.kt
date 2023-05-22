package uz.isystem.reminder2.repo

import androidx.lifecycle.LiveData
import uz.isystem.reminder2.dao.NotesDao
import uz.isystem.reminder2.model.Notes

class NotesRepository(val dao:NotesDao) {

    fun getAllNotes():LiveData<List<Notes>>{
        return dao.getNotes()
    }

    fun getCompletedNotes():LiveData<List<Notes>>{
        return dao.getCompleted()
    }

    fun insertNotes(notes: Notes){
        dao.insertNotes(notes)
    }
    fun deleteNotes(id:Int){
        dao.deleteNotes(id)
    }
    fun updateNotes(notes: Notes){
        dao.updateNotes(notes)
    }

    /*  fun getHighNotes():LiveData<List<Notes>>{
        return dao.getHighNotes()
    }
    fun getMediumNotes():LiveData<List<Notes>>{
        return dao.getMediumNotes()
    }
    fun getLowNotes():LiveData<List<Notes>>{
        return dao.getLowNotes()
    }

    * */
}