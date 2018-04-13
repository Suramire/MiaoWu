package com.suramire.miaowu.model;

import com.suramire.miaowu.base.App;
import com.suramire.miaowu.bean.Apply;
import com.suramire.miaowu.bean.Catinfo;
import com.suramire.miaowu.bean.Multi0;
import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Reply;
import com.suramire.miaowu.bean.User;
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
    public Observable<Note> getNoteDetail(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.getDetailNote(note)
                .map(new ResponseFunc<Note>());
    }

    @Override
    public Observable<List<String>> getPicture(final int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.getAllPictureNote(note)
                .map(new ResponseFunc<List<String>>());

    }

    @Override
    public Observable<List<Multi0>> getNoteReply(final int noteId) {
        Reply reply = new Reply();
        reply.setNid(noteId);
        return ApiLoader.listReplys(reply)
                .map(new ResponseFunc<List<Multi0>>());

    }

    @Override
    public Observable<Object> thumb(final int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return  ApiLoader.thumbNote(note)
                .map(new ResponseFunc<Object>());

    }

//    @Override
//    public Observable<Catinfo> getCatInfo(int noteId) {
//        Note note = new Note();
//        note.setId(noteId);
//        return ApiLoader.getCat(note)
//                .map(new ResponseFunc<Catinfo>());
//    }

    @Override
    public Observable<User> getUserInfo(int userId) {
        User user = new User();
        user.setId(userId);
        return ApiLoader.getUser(user).
                map(new ResponseFunc<User>());
    }

    @Override
    public Observable<Void> passNote(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.passNote(note)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<Void> lockNote(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.lockNote(note)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<Void> unlockNote(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.unlockNote(note)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<Void> deleteNote(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.deleteNote(note)
                .map(new ResponseFunc<Void>());
    }

    @Override
    public Observable<Void> increaseNoteCount(int noteId) {
        Note note = new Note();
        note.setId(noteId);
        return ApiLoader.increaseCount(note)
                .map(new ResponseFunc<Void>());
    }

//    @Override
//    public Observable<Void> reviewApply(Apply reviewApply) {
//        return ApiLoader.addApply(reviewApply)
//                .map(new ResponseFunc<Void>());
//    }
//
//    @Override
//    public Observable<Void> makeChoice(Apply reviewApply) {
//        return ApiLoader.verifyApply(reviewApply)
//                .map(new ResponseFunc<Void>());
//    }
//
//    @Override
//    public Observable<Apply> getApply(int applyId) {
//        Apply reviewApply = new Apply();
//        reviewApply.setId(applyId);
//        return ApiLoader.getOneApply(reviewApply)
//                .map(new ResponseFunc<Apply>());
//    }

}
