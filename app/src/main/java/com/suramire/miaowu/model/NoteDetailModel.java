package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Multi;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.contract.NoteDetailContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2017/10/31.
 */

public class NoteDetailModel implements NoteDetailContract.Model {

    @Override
    public Observable getNoteDetail(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.getDetailNote(note);
    }

    @Override
    public Observable<List<String>> getPicture(final int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.getAllPictureNote(note)
                .map(new ResponseFunc<List<String>>());

    }

    @Override
    public Observable<List<Multi>> getNoteReply(final int noteId) {
        Reply reply = new Reply();
        reply.setNid(noteId);
        return ApiLoader.listReplys(reply)
                .map(new ResponseFunc<List<Multi>>());

    }

    @Override
    public Observable<Object> thumb(final int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return  ApiLoader.thumbNote(note)
                .map(new ResponseFunc<Object>());

    }
}
