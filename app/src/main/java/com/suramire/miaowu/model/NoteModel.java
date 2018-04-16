package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.NoteContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/1/27.
 */

public class NoteModel implements NoteContract.Model {
    @Override
    public Observable<List<Note>> getNotesByUser(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getNoteByUser(user)
                .map(new ResponseFunc<List<Note>>());
    }

    @Override
    public Observable<List<Note>> getAllNotesByUser(int uid) {
        User user = new User();
        user.setId(uid);
        return ApiLoader.getAllNoteByUser(user)
                .map(new ResponseFunc<List<Note>>());
    }

    @Override
    public Observable<List<Note>> getUnverifyNotes() {
        return ApiLoader.listUnverifyNotes()
                .map(new ResponseFunc<List<Note>>());
    }
}
