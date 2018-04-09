package com.suramire.miaowu.model;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.User;
import com.suramire.miaowu.contract.SearchContract;
import com.suramire.miaowu.http.ApiLoader;
import com.suramire.miaowu.http.base.ResponseFunc;

import java.util.List;

import rx.Observable;

/**
 * Created by Suramire on 2018/3/26.
 */

public class SearchModel implements SearchContract.Model {
    @Override
    public Observable<List<User>> searchUser(String query) {
        User user = new User();
        user.setNickname(query);
        return ApiLoader.searchUser(user)
                .map(new ResponseFunc<List<User>>());
    }

    @Override
    public Observable<List<Note>> searchNote(String query) {
        Note note = new Note();
        note.setContent(query);
        return ApiLoader.searchNote(note)
                .map(new ResponseFunc<List<Note>>());
    }
}
